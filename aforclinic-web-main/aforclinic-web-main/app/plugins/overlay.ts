export default defineNuxtPlugin(() => {
  const visible = ref(false);

  const overlay = {
    get visible() {
      return visible.value;
    },
    set visible(newValue: boolean) {
      visible.value = newValue;
    },
    hidden() {
      visible.value = false;
    },
  };

  return {
    provide: {
      overlay,
    },
  };
});
