<template>
  <main
    class="w-full min-w-0 max-w-screen-md pt-16"
    :class="$route.meta.mainClass"
  >
    <slot />
  </main>
</template>
