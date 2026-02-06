const getAttributeSafe = async (locator, attribute) => {
  try {
    return (await locator.getAttribute(attribute)) || '';
  } catch {
    try {
      return (
        (await locator.evaluate((el, attr) => el.getAttribute(attr), attribute)) ||
        ''
      );
    } catch {
      return '';
    }
  }
};

const fillFieldSafe = async (locator, value) => {
  try {
    await locator.fill(value, { timeout: 5000 });
    return;
  } catch {
    try {
      await locator.evaluate((el, val) => {
        el.value = val;
        el.dispatchEvent(new Event('input', { bubbles: true }));
        el.dispatchEvent(new Event('change', { bubbles: true }));
      }, value);
    } catch {
      // ignore final fallback failures
    }
  }
};

export const fillRequiredFields = async (scope, overrides = {}) => {
  const inputs = scope.locator('input[required]');
  const textareas = scope.locator('textarea[required]');
  const selects = scope.locator('select[required]');

  const inputCount = await inputs.count();
  const textareaCount = await textareas.count();
  const selectCount = await selects.count();

  if (inputCount === 0 && textareaCount === 0 && selectCount === 0) {
    return;
  }
  if (inputCount > 0) {
    try {
      await inputs.first().isVisible();
    } catch {
      // ignore initial visibility checks
    }
  }
  for (let i = 0; i < inputCount; i += 1) {
    const input = inputs.nth(i);
    try {
      if (!(await input.isVisible())) {
        await input.scrollIntoViewIfNeeded();
      }
    } catch {
      // Ignore transient detachments
    }
    let type = await getAttributeSafe(input, 'type');
    const name = await getAttributeSafe(input, 'name');
    const placeholder = await getAttributeSafe(input, 'placeholder');
    if (!type) {
      type = 'text';
    }
    const key = name || placeholder || `input-${i}`;

    if (type === 'file') {
      continue;
    }

    if (overrides[key]) {
      await fillFieldSafe(input, overrides[key]);
      continue;
    }

    if (type === 'email') {
      await fillFieldSafe(input, 'test@example.com');
    } else if (type === 'url') {
      await fillFieldSafe(input, 'https://example.com');
    } else if (type === 'number') {
      await fillFieldSafe(input, '5');
    } else if (type === 'date') {
      await fillFieldSafe(input, '2024-01-15');
    } else {
      await fillFieldSafe(input, `Test ${i + 1}`);
    }
  }

  if (textareaCount > 0) {
    try {
      await textareas.first().isVisible();
    } catch {
      // ignore initial visibility checks
    }
  }
  for (let i = 0; i < textareaCount; i += 1) {
    const textarea = textareas.nth(i);
    try {
      if (!(await textarea.isVisible())) {
        await textarea.scrollIntoViewIfNeeded();
      }
    } catch {
      // Ignore transient detachments
    }
    const name = await getAttributeSafe(textarea, 'name');
    const placeholder = await getAttributeSafe(textarea, 'placeholder');
    const key = name || placeholder || `textarea-${i}`;

    if (overrides[key]) {
      await fillFieldSafe(textarea, overrides[key]);
    } else {
      await fillFieldSafe(textarea, `Test description ${i + 1}`);
    }
  }

  if (selectCount > 0) {
    try {
      await selects.first().isVisible();
    } catch {
      // ignore initial visibility checks
    }
  }
  for (let i = 0; i < selectCount; i += 1) {
    const select = selects.nth(i);
    try {
      if (!(await select.isVisible())) {
        await select.scrollIntoViewIfNeeded();
      }
    } catch {
      // Ignore transient detachments
    }
    try {
      const options = await select.locator('option').all();
      if (options.length > 1) {
        const value = await options[1].getAttribute('value');
        if (value) {
          await select.selectOption(value);
        }
      }
    } catch {
      // ignore select fallback failures
    }
  }

  // Fallback evaluation removed to avoid hanging on detached forms.
};
