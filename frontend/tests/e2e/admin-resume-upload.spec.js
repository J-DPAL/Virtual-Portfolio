import { test, expect } from '@playwright/test';
import { loginAsAdmin } from './utils/auth';

test.describe('Admin Resume Upload', () => {
  test('uploads an English resume PDF', async ({ page }) => {
    await page.route('**/v1/files/resume/upload', async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: '{}',
      });
    });

    await loginAsAdmin(page);
    await page.goto('/admin/files');

    const fileInput = page.locator('#file-upload-en');
    await expect(fileInput).toBeVisible();

    await fileInput.setInputFiles({
      name: 'resume-en.pdf',
      mimeType: 'application/pdf',
      buffer: Buffer.from('PDF content'),
    });

    const uploadButton = page
      .getByRole('button', { name: /Upload.*English/i })
      .first();
    await expect(uploadButton).toBeEnabled();
    await uploadButton.scrollIntoViewIfNeeded();
    await uploadButton.click({ force: true });

    const successMessage = page.locator('text=/upload.*success/i');
    await expect(successMessage).toBeVisible({ timeout: 5000 });
  });
});
