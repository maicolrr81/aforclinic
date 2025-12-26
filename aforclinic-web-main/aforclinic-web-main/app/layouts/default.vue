<template>
  <div class="container mx-auto grid min-h-screen w-auto max-w-screen-md grid-rows-[1fr_auto] overflow-x-hidden bg-white pb-[3.5rem]">
    <TheHeader />
    <TheMain>
      <slot />
    </TheMain>
    <TheFooter />
    <TheDock />
    <TheDrawer />
    <TheAside />
  </div>
</template>
