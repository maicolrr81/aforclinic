export default defineNuxtRouteMiddleware((to) => {
  const { i18n } = useAppConfig();
  const matchedLocale = Object.keys(i18n.locales).find(key => to.path.startsWith(`/${key}`)) || `ko`;

  useHead({
    htmlAttrs: {
      lang: matchedLocale,
    },
  });
});
