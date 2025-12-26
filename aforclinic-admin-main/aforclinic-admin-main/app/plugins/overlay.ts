export default defineNuxtPlugin(() => {
  const { $q } = useNuxtApp();

  function show() {
    $q.loading.show({
      message: `처리 중입니다...`,
    });
  }

  function hide() {
    $q.loading.hide();
  }

  return {
    provide: {
      overlay: {
        show,
        hide,
      },
    },
  };
});
