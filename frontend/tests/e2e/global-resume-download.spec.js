import { test, expect } from '@playwright/test';

test.describe('Resume Download', () => {
  test('requests resume download for selected language', async ({ page }) => {
    await page.route('**/v1/files/resume/download**', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/pdf',
        body: 'Mock PDF content',
      });
    });

    await page.goto('/', { waitUntil: 'networkidle' });
    await page.waitForLoadState('domcontentloaded');

    const languageButton = page
      .getByRole('button', { name: /EN|FR|ES|Langue|Language/i })
      .first();
    await languageButton.click({ force: true });

    const spanishOption = page.getByText('Español', { selector: 'button' }).first();
    await expect(spanishOption).toBeVisible();
    await spanishOption.click({ force: true });

    const mobileMenuButton = page.locator('button.lg\\:hidden').first();
    if (await mobileMenuButton.isVisible()) {
      await mobileMenuButton.click({ force: true });
    }

    const downloadButton = page
      .locator('button:visible')
      .filter({ hasText: /download resume|cv|resume/i })
      .first();
    await expect(downloadButton).toBeVisible();
    await expect(downloadButton).toBeEnabled();
    await downloadButton.scrollIntoViewIfNeeded();

    const [response] = await Promise.all([
      page.waitForResponse((resp) =>
        resp.url().includes('/v1/files/resume/download')
      ),
      downloadButton.click({ force: true }),
    ]);
    expect(response.status()).toBe(200);
    expect(response.url()).toContain('language=es');
  });
});
