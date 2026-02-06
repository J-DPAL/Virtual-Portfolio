import { test, expect } from '@playwright/test';
import { loginAsAdmin } from './utils/auth';
import { fillRequiredFields } from './utils/forms';

test.describe('Admin Experience Management', () => {
  test('opens add experience modal and submits form', async ({ page }) => {
    let experiences = [];

    await page.route('**/api/experience', async (route) => {
      const method = route.request().method();
      if (method === 'GET') {
        await route.fulfill({
          status: 200,
          contentType: 'application/json',
          body: JSON.stringify(experiences),
        });
        return;
      }
      if (method === 'POST') {
        const payload = route.request().postDataJSON();
        const created = { id: 1, ...payload };
        experiences = [created];
        await route.fulfill({
          status: 201,
          contentType: 'application/json',
          body: JSON.stringify(created),
        });
        return;
      }
      await route.fulfill({ status: 200, body: '{}' });
    });

    await loginAsAdmin(page);
    await page.goto('/admin/experience', { waitUntil: 'domcontentloaded' });
    const pageHeading = page.getByRole('heading', { name: /experience/i });
    await expect(pageHeading).toBeVisible({ timeout: 10000 });

    const addButton = page
      .getByRole('button', { name: /add new|add/i })
      .first();
    await expect(addButton).toBeVisible({ timeout: 10000 });
    await addButton.click({ force: true });

    const form = page.locator('form');
    await expect(form).toBeVisible();

    await fillRequiredFields(form);

    const submitButton = form.locator('button[type="submit"]');
    await submitButton.click({ force: true });

    const rows = page.locator('table tbody tr');
    await expect(rows.first()).toBeVisible({ timeout: 10000 });
  });
});
