export default defineEventHandler((event) => {
  const runtimeConfig = useRuntimeConfig();

  const referer = event.node.req.headers.referer || '/';

  setCookie(event, `redirect`, referer, {
    httpOnly: true,
    secure: !import.meta.dev,
    path: `/`,
    maxAge: 60, // 60초
  });

  return sendRedirect(event, `${runtimeConfig.public.apiBase}/oauth2/authorization/kakao`);
});
