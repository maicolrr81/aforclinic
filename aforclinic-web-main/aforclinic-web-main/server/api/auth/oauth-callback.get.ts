export default defineEventHandler(async (event) => {
  const runtimeConfig = useRuntimeConfig();

  const query = getQuery<{
    refreshTokenId?: string;
  }>(event);

  const { refreshTokenId } = query;
  if (!refreshTokenId) {
    throw createError({
      statusCode: 400,
      message: 'Invalid OAuth response',
    });
  }

  setCookie(event, runtimeConfig.cookieConfig.sessionName, refreshTokenId, {
    httpOnly: true,
    secure: !import.meta.dev,
    path: `/`,
  });

  // API 서버에서 생성된 JSESSIONID 삭제(없을수도있음)
  deleteCookie(event, `JSESSIONID`);

  const redirect = getCookie(event, `redirect`) || `/`;

  deleteCookie(event, `redirect`, { path: `/` });

  return sendRedirect(event, redirect);
});
