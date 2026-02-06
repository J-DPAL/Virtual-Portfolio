import { test, expect } from '@playwright/test';

test.describe('Public Hobbies Page', () => {
  test('renders hobbies cards with names and descriptions', async ({
    page,
  }) => {
    await page.route('**/api/hobbies', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify([
          {
            id: 1,
            nameEn: 'Photography',
            nameFr: 'Photographie',
            nameEs: 'Fotografía',
            descriptionEn: 'Capturing moments and landscapes.',
            descriptionFr: 'Capturer des moments et des paysages.',
            descriptionEs: 'Capturando momentos y paisajes.',
          },
        ]),
      });
    });

    await page.goto('/hobbies', { waitUntil: 'networkidle' });
    await page.waitForLoadState('domcontentloaded');

    const heading = page.getByRole('heading', { name: /hobbies/i });
    await expect(heading).toBeVisible({ timeout: 10000 });

    const hobbyName = page.getByText('Photography', { selector: 'h2' }).first();
    await expect(hobbyName).toBeVisible();

    const hobbyDescription = page.getByText('Capturing moments', { selector: 'p' }).first();
    await expect(hobbyDescription).toBeVisible();
  });
});
