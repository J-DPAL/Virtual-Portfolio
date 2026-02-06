import { test, expect } from '@playwright/test';

test.describe('Header Navigation', () => {
  test('navigates using desktop header links', async ({ page }) => {
    await page.route('**/api/projects', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify([]),
      });
    });

    await page.goto('/', { waitUntil: 'networkidle' });
    await page.waitForLoadState('domcontentloaded');

    const projectsLink = page.getByRole('link', { name: /projects/i }).first();
    await expect(projectsLink).toBeVisible();
    await projectsLink.click();

    await expect(page).toHaveURL('/projects');
  });
});
