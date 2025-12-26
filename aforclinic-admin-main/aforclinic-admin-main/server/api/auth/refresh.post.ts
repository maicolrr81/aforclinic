export default defineEventHandler(async (event) => {
  const runtimeConfig = useRuntimeConfig();

  const refreshTokenId = getCookie(event, runtimeConfig.app.cookie.session);
  if (!refreshTokenId) {
    throw createError({
      statusCode: 401,
      statusMessage: `Unauthorized`,
    });
  }

  try {
    const { data } = await $fetch<ApiResponse<Pick<AuthenticationSuccessResponse, 'accessToken'>>>(`/auth/refresh`, {
      baseURL: runtimeConfig.public.apiBase,
      method: `post`,
      headers: {
        'Content-Type': `application/json`,
      },
      body: {
        refreshTokenId,
      },
    });

    const { accessToken } = data;

    return {
      accessToken,
    };
  }
  catch {
    // 쿠키에 저장된 리프레시 토큰을 삭제한다.
    setCookie(event, runtimeConfig.app.cookie.session, ``, {
      httpOnly: true,
      secure: !import.meta.dev,
      path: `/`,
      expires: new Date(0),
    });

    deleteCookie(event, runtimeConfig.app.cookie.session);

    throw createError({
      statusCode: 401,
      statusMessage: `Unauthorized`,
    });
  }
});
