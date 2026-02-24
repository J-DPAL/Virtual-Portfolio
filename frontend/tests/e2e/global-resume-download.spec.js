import { test, expect } from '@playwright/test';

test.describe('Resume Download', () => {
  test('requests resume download for selected language', async ({ page }) => {
    await page.route('**/api/v1/files/resume/download*', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/pdf',
        body: 'Mock PDF content',
      });
    });

    await page.goto('/', { waitUntil: 'domcontentloaded' });
    await page.waitForLoadState('domcontentloaded');

    const languageButton = page.getByTestId('language-toggle');
    await languageButton.click({ force: true });

    const spanishOption = page.getByTestId('language-option-es');
    await expect(spanishOption).toBeVisible();
    await spanishOption.click({ force: true });

    let downloadButton = page.getByRole('button', { name: /^cv$/i }).first();
    if (!(await downloadButton.isVisible())) {
      downloadButton = page
        .locator('button:visible')
        .filter({
          hasText: /download|resume|cv|descargar|hoja de vida|curriculum|telecharger/i,
        })
        .first();
    }

    if (!(await downloadButton.isVisible())) {
      const mobileMenuButton = page.locator('button.lg\\:hidden').first();
      if (await mobileMenuButton.isVisible()) {
        await mobileMenuButton.click({ force: true });
      }

      downloadButton = page
        .locator('button:visible')
        .filter({
          hasText: /download|resume|cv|descargar|hoja de vida|curriculum|telecharger/i,
        })
        .first();
    }

    await expect(downloadButton).toBeVisible();
    await expect(downloadButton).toBeEnabled();
    await downloadButton.scrollIntoViewIfNeeded();

    const [request] = await Promise.all([
      page.waitForRequest((req) =>
        req.url().includes('/api/v1/files/resume/download')
      ),
      downloadButton.click({ force: true }),
    ]);
    expect(request.url()).toContain('language=es');
  });
});
