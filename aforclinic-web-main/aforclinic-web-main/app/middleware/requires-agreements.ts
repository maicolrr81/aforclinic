export default defineNuxtRouteMiddleware(() => {
  const agreements = useAgreementsStore();
  if (!agreements.isEssentialAgreed) {
    return navigateTo(`/signup/agreements`);
  }
});
