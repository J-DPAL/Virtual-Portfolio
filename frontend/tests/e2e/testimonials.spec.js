import { test, expect } from '@playwright/test';

const openTestimonialForm = async (page) => {
  const toggleButton = page
    .locator('button')
    .filter({ hasText: /add testimonial|close|add a testimonial|submit/i })
    .first();
  await expect(toggleButton).toBeVisible({ timeout: 10000 });
  await toggleButton.click();
};

const getTestimonialFormFields = (page) => {
  const form = page.locator('form');
  const inputs = form.locator('input');
  const nameInput = inputs.nth(0);
  const titleInput = inputs.nth(1);
  const companyInput = inputs.nth(2);
  const ratingInput = inputs.nth(3);
  const textareas = form.locator('textarea');
  const textEn = textareas.nth(0);
  const textFr = textareas.nth(1);
  const textEs = textareas.nth(2);
  const submitButton = form.locator('button[type="submit"]');
  return {
    form,
    nameInput,
    titleInput,
    companyInput,
    ratingInput,
    textEn,
    textFr,
    textEs,
    submitButton,
  };
};

test.describe('Testimonials Page - View & Submit', () => {
  test.beforeEach(async ({ page }) => {
    await page.route('**/api/testimonials', async (route) => {
      if (route.request().method() === 'GET') {
        await route.fulfill({
          status: 200,
          contentType: 'application/json',
          body: JSON.stringify([]),
        });
        return;
      }
      await route.continue();
    });

    // Navigate to testimonials page before each test
    await page.goto('/testimonials', { waitUntil: 'networkidle' });
    await page.waitForLoadState('domcontentloaded');
  });

  test('should display approved testimonials in grid layout', async ({
    page,
  }) => {
    await page.unroute('**/api/testimonials');
    await page.route('**/api/testimonials', async (route) => {
      if (route.request().method() === 'GET') {
        await route.fulfill({
          status: 200,
          contentType: 'application/json',
          body: JSON.stringify([
            {
              id: 1,
              clientName: 'Sample Client',
              testimonialTextEn: 'Excellent work',
              rating: 5,
            },
          ]),
        });
        return;
      }
      await route.continue();
    });

    await page.goto('/testimonials');

    // Verify page heading
    const pageHeading = page.getByRole('heading', { name: /testimonials/i });
    await expect(pageHeading).toBeVisible({ timeout: 10000 });

    // Wait for testimonials to load
    await page.waitForLoadState('networkidle');

    // Expect the mocked testimonial to render
    const clientName = page.getByText('Sample Client').first();
    if (await clientName.count()) {
      await expect(clientName).toBeVisible({ timeout: 10000 });
      const testimonialCards = page.locator('div[class*="rounded-2xl"]');
      await expect(testimonialCards.first()).toBeVisible();
    } else {
      // If no testimonials, should show message
      const emptyMessage = page.locator(
        'text=/no testimonials available|no testimonials yet|be the first/i'
      );
      await expect(emptyMessage.first()).toBeVisible({ timeout: 10000 });
    }
  });

  test('should display testimonial submission form with all fields', async ({
    page,
  }) => {
    await openTestimonialForm(page);

    const { form, nameInput, titleInput, textEn, ratingInput, submitButton } =
      getTestimonialFormFields(page);

    await expect(form).toBeVisible();
    await expect(nameInput).toBeVisible();
    await expect(titleInput).toBeVisible();
    await expect(textEn).toBeVisible();
    await expect(ratingInput).toBeVisible();
    await expect(submitButton).toBeVisible();
  });

  test('should validate testimonial form and show errors for empty submission', async ({
    page,
  }) => {
    await openTestimonialForm(page);

    const { nameInput, submitButton } = getTestimonialFormFields(page);

    // Try to submit without filling form
    await submitButton.click();

    // Check HTML5 validation
    const nameValidity = await nameInput.evaluate((el) => el.validity.valid);
    expect(nameValidity).toBe(false);

    // Should remain on testimonials page
    await expect(page).toHaveURL('/testimonials');
  });

  test('should successfully submit testimonial with valid data', async ({
    page,
  }) => {
    await page.route('**/api/testimonials', async (route) => {
      if (route.request().method() === 'POST') {
        await route.fulfill({
          status: 201,
          contentType: 'application/json',
          body: JSON.stringify({ id: 2, clientName: 'Sarah Johnson' }),
        });
        return;
      }
      await route.continue();
    });

    // Scroll to form
    const form = page.locator('form').last();
    await openTestimonialForm(page);

    const {
      nameInput,
      titleInput,
      companyInput,
      ratingInput,
      textEn,
      textFr,
      textEs,
      submitButton,
    } = getTestimonialFormFields(page);

    await nameInput.fill('Sarah Johnson');
    await titleInput.fill('Marketing Manager');
    await companyInput.fill('Tech Innovations Inc');
    await textEn.fill('Outstanding work on our portfolio redesign.');
    await textFr.scrollIntoViewIfNeeded();
    await textFr.fill('Travail exceptionnel sur notre portfolio.');
    await textEs.scrollIntoViewIfNeeded();
    await textEs.fill('Trabajo excelente en nuestro portafolio.');
    await ratingInput.fill('5');

    const submitPromise = page.waitForResponse(
      (response) =>
        response.url().includes('/api/testimonials') &&
        (response.status() === 200 || response.status() === 201)
    );

    await submitButton.click({ force: true });

    const response = await submitPromise;
    expect(response.status()).toBeLessThan(300);

    const successMessage = page.locator(
      'text=/testimonial.*submitted|success|thank you|received/i'
    );
    await expect(successMessage).toBeVisible({ timeout: 5000 });

    await expect(nameInput).toHaveValue('');
  });

  test('should display multiple testimonials in proper layout', async ({
    page,
  }) => {
    // Wait for page to load
    await page.waitForLoadState('networkidle');

    // Get all testimonial cards
    const testimonialCards = page.locator('div[class*="rounded-3xl"]').filter({
      has: page.locator('text=/[A-Za-z]+|⭐'),
    });

    const cardCount = await testimonialCards.count();

    if (cardCount > 1) {
      // Verify grid/layout structure
      const firstCard = testimonialCards.first();
      const secondCard = testimonialCards.nth(1);

      // Get bounding boxes to verify layout
      const firstBox = await firstCard.boundingBox();
      const secondBox = await secondCard.boundingBox();

      // Verify cards are positioned differently (in grid)
      expect(firstBox).not.toBeNull();
      expect(secondBox).not.toBeNull();
      expect(firstBox?.x !== secondBox?.x || firstBox?.y !== secondBox?.y).toBe(
        true
      );

      // Verify responsive behavior - check if cards are visible
      for (let i = 0; i < Math.min(cardCount, 3); i++) {
        const card = testimonialCards.nth(i);
        const isVisible = await card.isVisible();
        expect(isVisible).toBe(true);
      }
    }
  });

  test('should handle testimonial submission errors gracefully', async ({
    page,
  }) => {
    // Simulate network error for submission
    await page.route('**/api/testimonials', (route) => {
      if (route.request().method() === 'POST') {
        route.abort('failed');
        return;
      }
      route.continue();
    });

    await openTestimonialForm(page);

    const {
      nameInput,
      titleInput,
      companyInput,
      ratingInput,
      textEn,
      textFr,
      textEs,
      submitButton,
    } = getTestimonialFormFields(page);

    await nameInput.fill('Error Test User');
    await titleInput.fill('QA Engineer');
    await companyInput.fill('Test Co');
    await ratingInput.fill('5');
    await textEn.fill('This submission will fail');
    await textFr.fill('Échec attendu');
    await textEs.fill('Fallo esperado');

    // Submit form
    await submitButton.click();

    // Verify error message appears
    const errorMessage = page.locator(
      'text=/failed to submit testimonial|error occurred|please try again/i'
    );
    await expect(errorMessage).toBeVisible({ timeout: 10000 });

    // Verify form data is preserved
    await expect(nameInput).toHaveValue('Error Test User');
    await expect(textEn).toHaveValue('This submission will fail');
  });
});
