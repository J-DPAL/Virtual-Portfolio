import { test, expect } from '@playwright/test';

test.describe('Public Home Page', () => {
  test('renders hero content and primary navigation links', async ({
    page,
  }) => {
    await page.goto('/', { waitUntil: 'networkidle' });
    await page.waitForLoadState('domcontentloaded');

    const heading = page.locator('h1');
    await expect(heading).toBeVisible();

    await page.route('**/api/projects', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify([
          {
            id: 1,
            titleEn: 'Sample Project',
            descriptionEn: 'Project description',
            technologies: 'React, Java',
          },
        ]),
      });
    });

    const cta = page
      .getByRole('link', { name: /view my work|projects/i })
      .first();
    await expect(cta).toBeVisible();

    await cta.click();
    await expect(page).toHaveURL('/projects');
  });
});
