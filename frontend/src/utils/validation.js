export function countWords(value) {
  return (value || '')
    .trim()
    .split(/\s+/)
    .filter(Boolean).length;
}

export function validateContactForm(form, t) {
  const senderName = (form.senderName || '').trim();
  const senderEmail = (form.senderEmail || '').trim();
  const subject = (form.subject || '').trim();
  const message = (form.message || '').trim();
  const messageWords = countWords(message);

  if (!senderName) return t('validationContactNameRequired');
  if (senderName.length < 2) return t('validationContactNameMin', { min: 2 });
  if (senderName.length > 100)
    return t('validationContactNameMax', { max: 100 });

  if (!senderEmail) return t('validationContactEmailRequired');
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailPattern.test(senderEmail)) return t('validationContactEmailInvalid');

  if (!subject) return t('validationContactSubjectRequired');
  if (subject.length < 3) return t('validationContactSubjectMin', { min: 3 });
  if (subject.length > 200)
    return t('validationContactSubjectMax', { max: 200 });

  if (!message) return t('validationContactMessageRequired');
  if (message.length < 20)
    return t('validationContactMessageMinChars', { min: 20 });
  if (messageWords < 5)
    return t('validationContactMessageMinWords', { min: 5 });
  if (message.length > 2000)
    return t('validationContactMessageMaxChars', { max: 2000 });

  return null;
}

export function validateTestimonialInput(input, t) {
  const clientName = (input.clientName || '').trim();
  const clientPosition = (input.clientPosition || '').trim();
  const clientCompany = (input.clientCompany || '').trim();
  const testimonialTextEn = (input.testimonialTextEn || '').trim();
  const testimonialTextFr = (input.testimonialTextFr || '').trim();
  const testimonialTextEs = (input.testimonialTextEs || '').trim();
  const rating = Number(input.rating);

  if (!clientName) return t('validationTestimonialNameRequired');
  if (clientName.length < 2)
    return t('validationTestimonialNameMinChars', { min: 2 });
  if (clientName.length > 100)
    return t('validationTestimonialNameMaxChars', { max: 100 });

  if (!clientPosition) return t('validationTestimonialPositionRequired');
  if (clientPosition.length < 2)
    return t('validationTestimonialPositionMinChars', { min: 2 });
  if (clientPosition.length > 120)
    return t('validationTestimonialPositionMaxChars', { max: 120 });

  if (!clientCompany) return t('validationTestimonialCompanyRequired');
  if (clientCompany.length < 2)
    return t('validationTestimonialCompanyMinChars', { min: 2 });
  if (clientCompany.length > 120)
    return t('validationTestimonialCompanyMaxChars', { max: 120 });

  const testimonialTexts = [
    { value: testimonialTextEn, lang: 'English' },
    { value: testimonialTextFr, lang: 'French' },
    { value: testimonialTextEs, lang: 'Spanish' },
  ];

  for (const item of testimonialTexts) {
    if (!item.value) {
      return t('validationTestimonialTextRequired', { language: item.lang });
    }
    if (item.value.length < 30) {
      return t('validationTestimonialTextMinChars', {
        language: item.lang,
        min: 30,
      });
    }
    if (countWords(item.value) < 6) {
      return t('validationTestimonialTextMinWords', {
        language: item.lang,
        min: 6,
      });
    }
    if (item.value.length > 1200) {
      return t('validationTestimonialTextMaxChars', {
        language: item.lang,
        max: 1200,
      });
    }
  }

  if (!Number.isFinite(rating)) return t('validationTestimonialRatingRequired');
  if (rating < 1 || rating > 5) {
    return t('validationTestimonialRatingRange');
  }

  return null;
}
