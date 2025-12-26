<script setup lang="ts">
// Use SSR-safe IDs for Headless UI
provideHeadlessUseId(() => useId());

const { $modal, $router } = useNuxtApp();
watch(() => $router.currentRoute.value.path, () => $modal.reset);
</script>

<template>
  <NuxtRouteAnnouncer />
  <NuxtLoadingIndicator />
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
  <LoginModal v-model="$modal.state.isLoginModalOpen" />
  <UiDialog v-model="$dialog.state.open" />
  <UiOverlay />
</template>
