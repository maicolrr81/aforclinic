export const useUserStore = defineStore(`user`, () => {
  const { $api } = useNuxtApp();

  const auth = useAuthStore();

  const data = ref<User | null>(null);

  const isAdmin = computed(() => data.value?.role === `ADMIN`);
  const isManager = computed(() => data.value?.role === `MANAGER`);

  async function load(): Promise<void> {
    try {
      const res = await $api<ApiResponse<User>>(`/users/me`);
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
    if (!newToken) { $reset(); }
  });

  return {
    data,
    isAdmin,
    isManager,
    load,
    $reset,
  };
});
