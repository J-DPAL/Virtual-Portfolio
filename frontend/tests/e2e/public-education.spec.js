import { test, expect } from '@playwright/test';

test.describe('Public Education Page', () => {
  test('renders education timeline entries', async ({ page }) => {
    await page.route('**/api/education', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify([
          {
            id: 1,
            degreeEn: 'BSc Computer Science',
            institutionNameEn: 'State University',
            fieldOfStudyEn: 'Computer Science',
            startDate: '2018-09-01',
            endDate: '2022-06-01',
            grade: 'A',
            descriptionEn: 'Studied software engineering.',
          },
        ]),
      });
    });

    await page.goto('/education', { waitUntil: 'networkidle' });
    await page.waitForLoadState('domcontentloaded');

    const heading = page.getByRole('heading', { name: /education/i });
    await expect(heading).toBeVisible({ timeout: 10000 });

    const degree = page.getByText('BSc Computer Science', { selector: 'h2' }).first();
    await expect(degree).toBeVisible();

    const institution = page.getByText('State University', { selector: 'h3' }).first();
    await expect(institution).toBeVisible();

    const field = page.getByText('Computer Science', { selector: 'p' }).first();
    await expect(field).toBeVisible();
  });
});
