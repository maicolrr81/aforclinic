export default defineNuxtRouteMiddleware(async (to) => {
  const { $dialog } = useNuxtApp();

  const auth = useAuthStore();
  const user = useUserStore();

  // 인증되지 않은 사용자
  if (!auth.isAuthenticated) {
    const res = await $fetch(`/api/auth/refresh`, {
      method: `post`,
      headers: import.meta.server ? useRequestHeaders([`cookie`]) : undefined,
      credentials: `include`, // 쿠키 포함 (서버/클라이언트 모두 적용)
    });
    if (res?.accessToken) {
      auth.$patch((state) => {
        state.accessToken = res.accessToken;
      });
    }
  }

  // 인증된 사용자가 사용자 정보가 없다면 사용자 정보를 읽어온다.
  if (auth.isAuthenticated && !user.data) {
    await user.load();
  }

  // 혹시 연락처가 없다면 회원 정보 수정 페이지로 이동.
  if (to.path !== `/mypage/edit`) {
    if (auth.isAuthenticated && user.data && !user.data.phoneNumber) {
      $dialog.alert(`연락처를 입력해주세요.`);
      return navigateTo(`/mypage/edit`);
    }
  }
});
