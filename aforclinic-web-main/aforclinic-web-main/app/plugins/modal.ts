export default defineNuxtPlugin(() => {
  const state = reactive({
    // 로그인 모달
    isLoginModalOpen: false,
  });

  function reset() {
    state.isLoginModalOpen = false;
  }

  return {
    provide: {
      modal: {
        state,
        reset,
      },
    },
  };
});
