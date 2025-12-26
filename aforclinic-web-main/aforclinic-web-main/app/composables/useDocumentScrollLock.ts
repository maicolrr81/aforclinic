export function useDocumentScrollLock() {
  const target = ref<HTMLElement | null>(null);
  const isLock = useScrollLock(target);

  onMounted(() => {
    target.value = document.documentElement;
  });

  return {
    isLock,
  };
}
