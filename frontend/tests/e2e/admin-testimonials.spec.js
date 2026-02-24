import { test, expect } from '@playwright/test';
import { loginAsAdmin } from './utils/auth';

test.describe('Admin Testimonials Management', () => {
  test('approves a pending testimonial', async ({ page }) => {
    let pending = [
      {
        id: 1,
        clientName: 'Jane Doe',
        clientPosition: 'Manager',
        testimonialTextEn: 'Great work!',
        approved: false,
        rating: 5,
      },
    ];
    let approved = [];

    await page.route('**/api/testimonials/pending', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify(pending),
      });
    });

    await page.route('**/api/testimonials', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify(approved),
      });
    });

    await page.route('**/api/testimonials/1/approve', async (route) => {
      approved = [{ ...pending[0], approved: true }];
      pending = [];
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: '{}',
      });
    });

    await loginAsAdmin(page);
    await page.goto('/admin/testimonials');

    const approveButton = page.getByRole('button', { name: /approve/i }).first();
    await expect(approveButton).toBeVisible();
    await approveButton.click();

    await expect(approveButton).toBeHidden({ timeout: 5000 });
  });

  test('does not expose edit controls for testimonials', async ({ page }) => {
    const pending = [
      {
        id: 2,
        clientName: 'John Client',
        clientPosition: 'Director',
        clientCompany: 'Acme',
        testimonialTextEn: 'Excellent service and delivery quality.',
        approved: false,
        rating: 5,
      },
    ];

    await page.route('**/api/testimonials/pending', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify(pending),
      });
    });

    await page.route('**/api/testimonials', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify([]),
      });
    });

    await loginAsAdmin(page);
    await page.goto('/admin/testimonials');

    await expect(page.getByRole('button', { name: /add new/i })).toHaveCount(0);
    await expect(page.getByRole('button', { name: /edit/i })).toHaveCount(0);
  });
});
