import { test, expect } from '@playwright/test';

const getContactFields = (page) => {
  const form = page.locator('form');
  const inputs = form.locator('input');
  const nameInput = inputs.nth(0);
  const emailInput = inputs.nth(1);
  const subjectInput = inputs.nth(2);
  const messageTextarea = form.locator('textarea');
  return {
    form,
    nameInput,
    emailInput,
    subjectInput,
    messageTextarea,
  };
};

test.describe('Contact Form Submission', () => {
  test.beforeEach(async ({ page }) => {
    // Navigate to contact page before each test
    await page.goto('/contact', { waitUntil: 'domcontentloaded' });
    await page.waitForLoadState('domcontentloaded');
  });

  test('should display contact form with all required fields', async ({
    page,
  }) => {
    // Verify contact page heading
    const pageHeading = page.getByRole('heading', { name: /contact/i });
    await expect(pageHeading).toBeVisible();

    // Verify form exists
    const { form, nameInput, emailInput, subjectInput, messageTextarea } =
      getContactFields(page);
    await expect(form).toBeVisible();

    // Verify sender name field
    await expect(nameInput).toBeVisible();

    // Verify sender email field
    await expect(emailInput).toBeVisible();

    // Verify subject field
    await expect(subjectInput).toBeVisible();

    // Verify message textarea
    await expect(messageTextarea).toBeVisible();

    // Verify submit button
    const submitButton = page.locator('button[type="submit"]');
    await expect(submitButton).toBeVisible();
  });

  test('should validate form fields and show error messages for empty submission', async ({
    page,
  }) => {
    // Attempt to submit with empty fields
    const submitButton = page.locator('button[type="submit"]');
    await submitButton.click();

    // Check HTML5 validation on required fields
    const { nameInput, emailInput, messageTextarea } = getContactFields(page);

    // Verify HTML5 validation
    const nameValidity = await nameInput.evaluate((el) => el.validity.valid);
    const emailValidity = await emailInput.evaluate((el) => el.validity.valid);
    const messageValidity = await messageTextarea.evaluate(
      (el) => el.validity.valid
    );

    expect(nameValidity).toBe(false);
    expect(emailValidity).toBe(false);
    expect(messageValidity).toBe(false);

    // Should still be on contact page
    await expect(page).toHaveURL('/contact');
  });

  test('should validate email format and show error', async ({ page }) => {
    // Fill form with invalid email
    const { nameInput, emailInput, subjectInput, messageTextarea } =
      getContactFields(page);

    await nameInput.fill('John Doe');
    await emailInput.fill('invalid-email-format');
    await subjectInput.fill('Test Subject');
    await messageTextarea.fill('This is a test message');

    // Try to submit
    const submitButton = page.locator('button[type="submit"]');
    await submitButton.click();

    // Verify email input shows validation error
    const emailValidity = await emailInput.evaluate((el) => el.validity.valid);
    expect(emailValidity).toBe(false);

    // Should stay on contact page
    await expect(page).toHaveURL('/contact');
  });

  test('should successfully submit form with valid data and show success message', async ({
    page,
  }) => {
    // Fill form with valid data
    const { nameInput, emailInput, subjectInput, messageTextarea } =
      getContactFields(page);

    const testData = {
      name: 'John Doe',
      email: 'john@example.com',
      subject: 'Test Portfolio Inquiry',
      message:
        'This is a test message to verify the contact form is working correctly.',
    };

    await nameInput.fill(testData.name);
    await emailInput.fill(testData.email);
    await subjectInput.fill(testData.subject);
    await messageTextarea.fill(testData.message);

    // Intercept the submission request
    const submitPromise = page.waitForResponse(
      (response) =>
        response.url().includes('/messages') &&
        (response.status() === 200 || response.status() === 201)
    );

    // Submit form
    const submitButton = page.locator('button[type="submit"]');
    await submitButton.click();

    // Wait for successful submission response
    const response = await submitPromise;
    expect(response.status()).toBeLessThan(300);

    // Verify success message appears
    const successMessage = page.locator(
      'text=/message.*sent|success|thank you|received/i'
    );
    await expect(successMessage).toBeVisible({ timeout: 5000 });

    // Verify form is cleared
    await expect(nameInput).toHaveValue('');
    await expect(emailInput).toHaveValue('');
    await expect(messageTextarea).toHaveValue('');
  });

  test('should disable submit button while submitting', async ({ page }) => {
    // Fill form with valid data
    const { nameInput, emailInput, subjectInput, messageTextarea } =
      getContactFields(page);

    await nameInput.fill('Jane Doe');
    await emailInput.fill('jane@example.com');
    await subjectInput.fill('Portfolio Feedback');
    await messageTextarea.fill('Great portfolio, love the design!');

    // Intercept request with delay to observe disabled state
    await page.route('**/api/messages', async (route) => {
      await new Promise((resolve) => setTimeout(resolve, 1000)); // 1 second delay
      await route.continue();
    });

    const submitButton = page.locator('button[type="submit"]');

    // Verify button is not disabled before click
    const isDisabledBefore = await submitButton.evaluate((el) =>
      el.hasAttribute('disabled')
    );
    expect(isDisabledBefore).toBe(false);

    // Click submit
    await submitButton.click();

    // Verify button shows loading state (disabled or contains loading text)
    await expect.poll(async () => {
      const isDisabledAfter = await submitButton.evaluate((el) =>
        el.hasAttribute('disabled')
      );
      const loadingText = submitButton.locator('text=/loading|sending/i');
      const hasLoadingText = (await loadingText.count()) > 0;
      return isDisabledAfter || hasLoadingText;
    }).toBe(true);
  });

  test('should handle network errors gracefully', async ({ page }) => {
    // Simulate network error
    await page.route('**/api/messages', (route) => {
      route.abort('failed');
    });

    // Fill form with valid data
    const { nameInput, emailInput, subjectInput, messageTextarea } =
      getContactFields(page);

    await nameInput.fill('Error Test User');
    await emailInput.fill('error@test.com');
    await subjectInput.fill('Network Error Test');
    await messageTextarea.fill('Testing error handling');

    // Submit form
    const submitButton = page.locator('button[type="submit"]');
    await submitButton.click();

    // Verify error message appears
    const errorMessage = page
      .getByText(/error|failed|try again|something went wrong/i)
      .first();
    await expect(errorMessage).toBeVisible({ timeout: 5000 });

    // Verify form data is preserved so user can retry
    await expect(nameInput).toHaveValue('Error Test User');
    await expect(emailInput).toHaveValue('error@test.com');
  });
});
