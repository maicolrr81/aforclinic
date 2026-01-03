<script setup lang="ts">
import type { HeaderProps } from "#shared/types/ComponentProps";

const { wide = false } = defineProps<HeaderProps>();

const { i18n } = useAppConfig();

const route = useRoute();

const windowScroll = useWindowScroll();
const windowSize = useWindowSize();

const isLocaleOpen = ref(false);

const isEnabledI18n = computed(() => i18n?.enabled ?? false);

const isWhite = computed(() => {
  const headThemeColor = route.meta.headThemeColor;
  if (headThemeColor === `transparent`) {
    return windowScroll.y.value > windowSize.height.value - 65;
  } else if (headThemeColor === `sensitive`) {
    return windowScroll.y.value > 0;
  }
  return true;
});

function onClick() {
  windowScroll.y.value = 0;
}
</script>

<template>
  <header
    class="container fixed top-0 z-[900] flex h-16 w-full items-center transition"
    :class="{
      'border-b bg-white': isWhite,
      'max-w-screen-md': !wide,
    }"
  >
    <div class="flex flex-1 content-center justify-between px-4 md:px-8">
      <NuxtLink to="/home" class="w-28 md:w-36" @click="onClick">
        <img
          alt="aforclinic"
          class="w-full"
          src="/images/ja/aforclinic_ch.png"
        />
      </NuxtLink>
      <div class="flex items-center gap-3">
        <NuxtLink
          class="h-6 w-6"
          to="https://www.instagram.com/aforclinic_jp/"
          target="_blank"
        >
          <NuxtIcon name="mdi:instagram" class="h-full w-full" />
        </NuxtLink>
        <NuxtLink
          class="h-6 w-6"
          href="https://line.me/R/ti/p/@aforclinic"
          target="_blank"
        >
          <NuxtIcon name="lineicons:line" class="h-full w-full" />
        </NuxtLink>
        <div v-if="isEnabledI18n" class="flex items-center">
          <button class="h-6 w-6" @click="isLocaleOpen = true">
            <NuxtIcon name="lineicons:globe-1" class="h-full w-full" />
          </button>
          <LocaleListbox v-model="isLocaleOpen" />
        </div>
      </div>
    </div>
  </header>
</template>
