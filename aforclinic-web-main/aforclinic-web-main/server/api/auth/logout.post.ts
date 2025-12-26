export default defineEventHandler(async (event) => {
  const runtimeConfig = useRuntimeConfig();

  // 현재 쿠키에 저장된 리프레시 토큰을 얻는다.
  const refreshTokenId = getCookie(event, runtimeConfig.cookieConfig.sessionName);
  if (!refreshTokenId) {
    return;
  }

  try {
    // 토큰 무효화 요청
    await $fetch<ApiResponse<string>>(`/auth/revoke`, {
      baseURL: runtimeConfig.public.apiBase,
      method: 'post',
      body: {
        refreshTokenId,
      },
    });
  }
  catch (error) {
    console.error(`로그아웃 요청 실패:`, error);
  }
  finally {
    // 쿠키에 저장된 리프레시 토큰을 삭제한다.
    setCookie(event, runtimeConfig.cookieConfig.sessionName, ``, {
      httpOnly: true,
      secure: !import.meta.dev,
      path: `/`,
      maxAge: 0,
    });

    deleteCookie(event, runtimeConfig.cookieConfig.sessionName);
  }
});
