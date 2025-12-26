export default defineNuxtPlugin(() => {
  const runtimeConfig = useRuntimeConfig();

  const api = $fetch.create({
    baseURL: runtimeConfig.public.apiBase,
    headers: {
      'Content-Type': `application/json`,
    },
    // 401(Unauthorized) 응답 시 토큰 갱신 후 1회 재시도
    retry: 1,
    retryStatusCodes: [401],
    credentials: `include`,
    // 요청 전 헤더에 액세스 토큰 설정
    onRequest: ({ options }) => {
      const auth = useAuthStore();
      if (auth.accessToken) {
        options.headers.set(`Authorization`, `Bearer ${auth.accessToken}`);
      }
    },
    onResponseError: async ({ response, options }) => {
      // 응답 오류가 401(Unauthorized)인 경우 토큰 갱신 시도
      if (response.status === 401) {
        const auth = useAuthStore();
        try {
          const res = await $fetch(`/api/auth/renew`, {
            method: `post`,
            credentials: `include`,
          });
          if (res) {
            auth.$patch({ accessToken: res.accessToken });
          }
        }
        catch {
          options.retry = 0;
          await auth.logout();
        }
      }
    },
  });

  return {
    provide: {
      api,
    },
  };
});
