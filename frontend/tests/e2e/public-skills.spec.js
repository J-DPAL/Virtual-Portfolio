import { test, expect } from '@playwright/test';

test.describe('Public Skills Page', () => {
  test('renders skills from API with logos and badges', async ({ page }) => {
    await page.route('**/api/skills', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify([
          {
            id: 1,
            nameEn: 'Java',
            nameFr: 'Java',
            nameEs: 'Java',
            category: 'Backend',
            proficiencyLevel: 'Advanced',
          },
        ]),
      });
    });

    await page.goto('/skills', { waitUntil: 'networkidle' });
    await page.waitForLoadState('domcontentloaded');

    const heading = page.getByRole('heading', { name: /skills/i });
    await expect(heading).toBeVisible({ timeout: 10000 });

    const cardTitle = page.getByText('Java', { selector: 'h2' }).first();
    await expect(cardTitle).toBeVisible();

    const categoryChip = page.locator('span', { hasText: 'Backend' }).first();
    await expect(categoryChip).toBeVisible();

    const levelChip = page.locator('span', { hasText: /advanced/i }).first();
    await expect(levelChip).toBeVisible();

    const logo = page.locator('img[alt*="Java"]').first();
    await expect(logo).toBeVisible();
  });
});
