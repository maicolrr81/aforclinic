<template>
  <div class="container mx-auto grid min-h-screen w-auto grid-rows-[1fr_auto]">
    <TheHeader wide />
    <TheMain class="w-full !max-w-none !pt-0">
      <slot />
    </TheMain>
    <TheDrawer />
  </div>
</template>
