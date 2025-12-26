import type { Cart, CreateCart, DeleteCart } from '#shared/types/Cart';

export const useCartStore = defineStore(`cart`, () => {
  const { $api } = useNuxtApp();

  const auth = useAuthStore();

  const _items = ref<string[]>([]);
  const storage = useLocalStorage<string[]>(`cart`, []);

  const items = computed<string[]>({
    get: () => {
      if (auth.isAuthenticated) {
        return _items.value ?? [];
      }
      return storage.value;
    },
    set: (newValue) => {
      if (auth.isAuthenticated) {
        _items.value = newValue;
      }
      else {
        storage.value = newValue;
      }
    },
  });
  const count = computed(() => items.value.length);

  async function add(productId: string) {
    if (!productId) { return; }

    try {
      await $api(`/public/cart`, {
        method: `post`,
        body: {
          productId,
        } satisfies CreateCart,
      });
    }
    catch {
      console.error(`장바구니 추가에 실패했습니다.`);
    }
  }

  async function remove(productIds: string | string[]) {
    if (!productIds || productIds.length === 0) { return; }

    try {
      await $api(`/public/cart`, {
        method: `delete`,
        params: {
          productId: productIds,
        } satisfies DeleteCart,
      });
    }
    catch {
      console.error(`장바구니 삭제에 실패했습니다.`);
    }
  }

  function $reset() {
    items.value = [];
  }

  watch(() => auth.isAuthenticated, async (newValue) => {
    if (newValue) {
      try {
        const data = await $api<ApiPagedResponse<Cart>>(`/public/cart`);
        _items.value = data.data.content.map(item => item.productId);
        if (import.meta.client && storage.value) {
          _items.value = [...new Set([..._items.value, ...storage.value])];
          storage.value = [];
        }
      }
      catch (e) {
        console.error(`장바구니 로드에 실패했습니다.`, e);
      }
    }
  }, {
    immediate: true,
  });

  let skip = true;
  watch(_items, async (newValue, oldValue) => {
    if (skip) {
      skip = false;
      return;
    }

    // 추가
    const addedItems = newValue.filter(value => !oldValue.includes(value));
    if (addedItems.length > 0) {
      for (const addedItem of addedItems) {
        await add(addedItem);
      }
    }

    // 삭제
    const deletedItems = oldValue.filter(value => !newValue.includes(value));
    if (deletedItems.length > 0) {
      await remove(deletedItems);
    }
  });

  return {
    items,
    count,
    $reset,
  };
});
