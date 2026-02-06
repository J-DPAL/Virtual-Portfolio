import { test, expect } from '@playwright/test';

test.describe('Global Dark Mode Toggle', () => {
  test('toggles dark mode and persists class on document', async ({ page }) => {
    await page.goto('/');

    const getIsDark = async () =>
      page.evaluate(() => document.documentElement.classList.contains('dark'));

    const initialIsDark = await getIsDark();

    const toggleButton = page.locator(
      'button[title*="dark mode" i], button[title*="light mode" i]'
    );
    await expect(toggleButton).toBeVisible();

    await toggleButton.click();

    const updatedIsDark = await getIsDark();
    expect(updatedIsDark).toBe(!initialIsDark);

    const storedTheme = await page.evaluate(() =>
      localStorage.getItem('theme')
    );
    expect(storedTheme).toBe(updatedIsDark ? 'dark' : 'light');

    await page.reload();

    const persistedTheme = await page.evaluate(() =>
      localStorage.getItem('theme')
    );
    expect(persistedTheme).toBe(updatedIsDark ? 'dark' : 'light');
  });
});
