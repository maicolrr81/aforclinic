export const useAgreementsStore = defineStore(`agreements`, () => {
  const terms = ref(false);
  const privacy = ref(false);
  const marketing = ref(false);

  // 필수 동의 확인 (이용약관 & 개인정보)
  const isEssentialAgreed = computed(() => terms.value && privacy.value);

  function $reset() {
    terms.value = false;
    privacy.value = false;
    marketing.value = false;
  }

  return {
    terms,
    privacy,
    marketing,
    isEssentialAgreed,
    $reset,
  };
});
