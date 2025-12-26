<script setup lang="ts">
import type { HeaderProps } from '#shared/types/ComponentProps';

const {
  wide = false,
} = defineProps<HeaderProps>();

const { i18n } = useAppConfig();

const route = useRoute();
const auth = useAuthStore();

const windowScroll = useWindowScroll();
const windowSize = useWindowSize();

const isLocaleOpen = ref(false);

const isEnabledI18n = computed(() => i18n?.enabled ?? false);

const isWhite = computed(() => {
  const headThemeColor = route.meta.headThemeColor;
  if (headThemeColor === `transparent`) {
    return windowScroll.y.value > (windowSize.height.value - 65);
  }
  else if (headThemeColor === `sensitive`) {
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
      <NuxtLink
        to="/home"
        class="w-24 lg:w-28"
        @click="onClick"
      >
        <img
          src="/images/5c67cdd825250e2c34048004e765fb508a0454e46a44584c3f2c812cd93c8d07.webp"
          alt="에이포의원"
          width="100%"
        >
      </NuxtLink>
      <div class="flex items-center gap-3">
        <NuxtLink
          class="h-6 w-6"
          to="https://youtube.com/@의사광수"
          target="_blank"
        >
          <NuxtIcon
            name="mdi:youtube"
            class="h-full w-full"
          />
        </NuxtLink>
        <NuxtLink
          class="h-6 w-6"
          to="https://www.instagram.com/afor_kim/"
          target="_blank"
        >
          <NuxtIcon
            name="mdi:instagram"
            class="h-full w-full"
          />
        </NuxtLink>
        <NuxtLink
          v-if="auth.isAuthenticated"
          class="h-6 w-6"
          to="/mypage"
        >
          <NuxtIcon
            name="mdi:user"
            class="h-full w-full"
          />
        </NuxtLink>
        <button
          v-else
          class="text-sm font-medium md:text-base"
          @click="$modal.state.isLoginModalOpen = true"
        >
          로그인
        </button>
        <div
          v-if="isEnabledI18n"
          class="flex items-center"
        >
          <button
            class="h-6 w-6"
            @click="isLocaleOpen = true"
          >
            <NuxtIcon
              name="lineicons:globe-1"
              class="h-full w-full"
            />
          </button>
          <LocaleListbox
            v-model="isLocaleOpen"
            inset
          />
        </div>
        <button class="h-6 w-6">
          <NuxtIcon
            name="mdi:hamburger-menu"
            class="h-full w-full"
            @click="$drawer.toggle()"
          />
        </button>
      </div>
    </div>
  </header>
</template>
