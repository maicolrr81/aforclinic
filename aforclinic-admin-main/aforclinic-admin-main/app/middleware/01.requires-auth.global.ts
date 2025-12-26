export default defineNuxtRouteMiddleware(async (to) => {
  const { $q } = useNuxtApp();

  const auth = useAuthStore();
  const user = useUserStore();

  // 로그인 페이지에서는 인증 체크를 하지 않는다.
  if (to.path === `/login`) { return; }

  // 인증되지 않은 사용자
  if (!auth.isAuthenticated) {
    try {
      // 액세스토큰 갱신
      const res = await $fetch(`/api/auth/refresh`, {
        method: `post`,
        headers: import.meta.server ? useRequestHeaders([`cookie`]) : undefined,
        credentials: `include`, // 쿠키 포함 (서버/클라이언트 모두 적용)
      });
      auth.$patch((state) => {
        state.accessToken = res.accessToken;
      });
    }
    catch {
      return navigateTo(`/login`);
    }
  }

  // 인증된 사용자가 사용자 정보가 없다면 사용자 정보를 읽어온다.
  if (auth.isAuthenticated && !user.data) {
    await user.load();
  }

  // 관리자 또는 매니저가 아니라면 로그인 화면으로 이동
  if (!(user.isAdmin || user.isManager)) {
    $q.dialog({
      title: ``,
      message: `접근불가`,
      ok: `확인`,
    });
    await auth.logout();
    return navigateTo(`/login`);
  }
});
