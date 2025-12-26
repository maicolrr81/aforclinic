export default defineEventHandler(async (event) => {
  const runtimeConfig = useRuntimeConfig();

  const {
    username,
    password,
  } = await readBody<{
    username: string;
    password: string;
  }>(event);

  try {
    const { data } = await $fetch<ApiResponse<AuthenticationSuccessResponse>>(`/auth/issue`, {
      baseURL: runtimeConfig.public.apiBase,
      method: 'post',
      headers: {
        'Content-Type': `application/x-www-form-urlencoded`,
      },
      body: new URLSearchParams({
        username,
        password,
      }),
    });

    const { accessToken, refreshToken } = data;
    setCookie(event, runtimeConfig.cookieConfig.sessionName, refreshToken, {
      httpOnly: true,
      secure: !import.meta.dev,
      path: `/`,
    });

    return { accessToken };
  }
  catch {
    // 쿠키에 저장된 리프레시 토큰을 삭제한다.
    setCookie(event, runtimeConfig.cookieConfig.sessionName, ``, {
      httpOnly: true,
      secure: !import.meta.dev,
      path: `/`,
      expires: new Date(0),
    });

    deleteCookie(event, runtimeConfig.cookieConfig.sessionName);

    throw createError({
      statusCode: 401,
      statusMessage: `Unauthorized`,
    });
  }
});
