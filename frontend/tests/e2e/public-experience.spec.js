import { test, expect } from '@playwright/test';

test.describe('Public Experience Page', () => {
  test('renders experience timeline entries', async ({ page }) => {
    await page.route('**/api/experience', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify([
          {
            id: 1,
            positionEn: 'Software Engineer',
            companyNameEn: 'Tech Corp',
            locationEn: 'Remote',
            startDate: '2022-01-01',
            endDate: null,
            descriptionEn: 'Worked on full-stack features.',
            responsibilities: ['Build APIs', 'Ship UI'],
          },
        ]),
      });
    });

    await page.goto('/experience', { waitUntil: 'networkidle' });
    await page.waitForLoadState('domcontentloaded');

    const heading = page.getByRole('heading', { name: /experience/i });
    await expect(heading).toBeVisible({ timeout: 10000 });

    const position = page.getByText('Software Engineer', { selector: 'h2' }).first();
    await expect(position).toBeVisible();

    const company = page.locator('h3:has-text("Tech Corp")');
    await expect(company).toBeVisible();

    const responsibility = page.getByText('Build APIs').first();
    await expect(responsibility).toBeVisible();
  });
});
