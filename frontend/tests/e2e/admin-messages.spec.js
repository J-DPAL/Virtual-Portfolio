import { test, expect } from '@playwright/test';
import { loginAsAdmin } from './utils/auth';

test.describe('Admin Messages View', () => {
  test('renders message list from API', async ({ page }) => {
    await page.route('**/api/messages', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify([
          {
            id: 1,
            name: 'Alice',
            email: 'alice@example.com',
            subject: 'Hello',
            message: 'Test message',
            createdAt: '2024-01-01T00:00:00Z',
          },
        ]),
      });
    });

    await loginAsAdmin(page);
    await page.goto('/admin/messages', { waitUntil: 'networkidle' });
    await page.waitForLoadState('domcontentloaded');

    const heading = page.getByRole('heading', { name: /messages/i });
    await expect(heading).toBeVisible({ timeout: 10000 });

    const row = page.getByText('Alice', { selector: 'td' }).first();
    await expect(row).toBeVisible();
  });
});
