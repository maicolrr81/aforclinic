export const useAuthStore = defineStore(`auth`, () => {
  const accessToken = ref<string | null>(null);

  const isAuthenticated = computed(() => !!accessToken.value);

  async function login(credentials: {
    username: string;
    password: string;
  }) {
    const res = await $fetch(`/api/auth/login`, {
      method: `post`,
      body: credentials,
    });
    accessToken.value = res.accessToken;
  }

  async function logout() {
    try {
      await $fetch(`/api/auth/logout`, {
        method: `post`,
        headers: import.meta.server ? useRequestHeaders([`cookie`]) : undefined,
        credentials: `include`,
      });
    }
    catch (error) {
      console.error(`로그아웃 요청 실패`, error);
    }
    finally {
      accessToken.value = null;
    }

    return navigateTo({
      path: `/`,
    }, {
      replace: true,
    });
  }

  function $reset() {
    accessToken.value = null;
  }

  return {
    accessToken,
    isAuthenticated,
    login,
    logout,
    $reset,
  };
});
