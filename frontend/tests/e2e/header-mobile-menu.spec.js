import { test, expect } from '@playwright/test';

test.describe('Header Mobile Menu', () => {
  test('opens mobile menu and navigates to skills', async ({ page }) => {
    await page.setViewportSize({ width: 375, height: 812 });

    await page.route('**/api/skills', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify([]),
      });
    });

    await page.goto('/', { waitUntil: 'networkidle' });
    await page.waitForLoadState('domcontentloaded');

    const menuButton = page.locator('button[class*="lg:hidden"]').first();
    await menuButton.click({ force: true });

    const header = page.locator('header');
    const skillsLink = header.getByRole('link', { name: /skills/i }).first();
    await expect(skillsLink).toBeVisible();
    await skillsLink.click({ force: true });

    await expect(page).toHaveURL('/skills');
  });
});
