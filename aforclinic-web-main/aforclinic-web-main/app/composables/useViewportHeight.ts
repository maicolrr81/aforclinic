export function useViewportHeight() {
  let resizeObserver: number | null = null;

  function setViewportHeight() {
    if (resizeObserver) { return; }
    resizeObserver = requestAnimationFrame(() => {
      document.documentElement.style.setProperty('--vh', `${window.innerHeight * 0.01}px`);
      resizeObserver = null;
    });
  }

  onMounted(() => {
    setViewportHeight();
    window.addEventListener('resize', setViewportHeight);
  });

  onBeforeUnmount(() => {
    window.removeEventListener('resize', setViewportHeight);
    if (resizeObserver) {
      cancelAnimationFrame(resizeObserver);
    }
  });
}
