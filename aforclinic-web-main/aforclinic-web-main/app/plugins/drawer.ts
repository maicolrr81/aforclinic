export default defineNuxtPlugin(() => {
  const open = ref(false);

  const drawer = {
    get open() {
      return open.value;
    },
    set open(newValue: boolean) {
      open.value = newValue;
    },
    toggle() {
      open.value = !open.value;
    },
  };

  return {
    provide: {
      drawer,
    },
  };
});
