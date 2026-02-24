import { test, expect } from '@playwright/test';

test.describe('Global Language Switching', () => {
  test('switches language to French and updates navigation labels', async ({
    page,
  }) => {
    const emptyList = {
      status: 200,
      contentType: 'application/json',
      body: JSON.stringify([]),
    };

    await page.route('**/api/projects', (route) => route.fulfill(emptyList));
    await page.route('**/api/skills', (route) => route.fulfill(emptyList));
    await page.route('**/api/experience', (route) => route.fulfill(emptyList));
    await page.route('**/api/education', (route) => route.fulfill(emptyList));

    await page.goto('/', { waitUntil: 'domcontentloaded' });
    await page.waitForLoadState('domcontentloaded');

    const languageButton = page.getByTestId('language-toggle');
    await expect(languageButton).toBeVisible();

    await languageButton.click({ force: true });

    const frenchOption = page.getByTestId('language-option-fr');
    await expect(frenchOption).toBeVisible();
    await frenchOption.click({ force: true });

    const mobileMenuButton = page.locator('button.lg\\:hidden').first();
    if (await mobileMenuButton.isVisible()) {
      await mobileMenuButton.click({ force: true });
    }

    const projectsNav = page.getByRole('link', { name: /projets/i }).first();
    await expect(projectsNav).toBeVisible();

    const homeNav = page.getByRole('link', { name: /accueil/i }).first();
    await expect(homeNav).toBeVisible();
  });
});
