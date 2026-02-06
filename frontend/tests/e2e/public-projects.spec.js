import { test, expect } from '@playwright/test';

test.describe('Public Projects Page', () => {
  test('renders projects and technology tags', async ({ page }) => {
    await page.route('**/api/projects', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify([
          {
            id: 1,
            titleEn: 'Portfolio Platform',
            descriptionEn: 'Full-stack portfolio project',
            technologies: 'React, Spring Boot',
            projectUrl: 'https://example.com',
            githubUrl: 'https://github.com/example',
          },
        ]),
      });
    });

    await page.goto('/projects', { waitUntil: 'networkidle' });
    await page.waitForLoadState('domcontentloaded');

    const heading = page.getByRole('heading', { name: /projects/i });
    await expect(heading).toBeVisible({ timeout: 10000 });

    const projectTitle = page.getByText('Portfolio Platform', { selector: 'h2' }).first();
    await expect(projectTitle).toBeVisible();

    const techTag = page.getByText('React', { selector: 'span' }).first();
    await expect(techTag).toBeVisible();

    const link = page.locator('a[href="https://example.com"]');
    await expect(link).toBeVisible();
  });
});
