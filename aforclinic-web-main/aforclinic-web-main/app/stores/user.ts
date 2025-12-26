export const useUserStore = defineStore(`user`, () => {
  const auth = useAuthStore();
  const nuxtApp = useNuxtApp();
  const { $api } = nuxtApp;

  const data = ref<User | null>(null);

  async function load(): Promise<void> {
    try {
      const res = await $api<ApiResponse<User>>(`/public/users/me`);
      data.value = res.data;
    }
    catch {
      data.value = null;
      await auth.logout();
    }
  }

  function $reset() {
    data.value = null;
  }

  watch(() => auth.accessToken, (newToken) => {
    if (newToken) {
      if (data.value === null) {
        load();
      }
    }
    else {
      $reset();
    }
  });

  return {
    data,
    load,
    $reset,
  };
});
