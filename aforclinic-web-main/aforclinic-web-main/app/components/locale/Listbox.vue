<script setup lang="ts">
const {
  inset,
} = defineProps<{
  inset?: boolean;
}>();

const { i18n } = useAppConfig();

const modelValue = defineModel<boolean>();

const route = useRoute();
const listbox = useTemplateRef(`listbox`);

const locales = computed(() =>
  Object.entries(i18n.locales).map(([key, locale]) => ({ key, ...locale })),
);

const currentLocale = computed(() => {
  return (
    locales.value.find(locale => route.path.startsWith(`/${locale.key}`))
    || locales.value.find(locale => locale.key === 'ko')
  );
});

onClickOutside(listbox, () => modelValue.value = false);
</script>

<template>
  <div
    v-if="modelValue"
    ref="listbox"
    class="absolute right-0 top-0 z-10 w-48"
  >
    <div class="flex h-16 items-center justify-center border-b-2 border-b-pink-300 bg-white">
      <b
        :class="{
          'pe-8': inset,
        }"
      >
        {{ currentLocale?.label }}
      </b>
      <button
        class="absolute right-8 h-6 w-6"
        :class="{
          'right-[68px]': inset,
        }"
        @click="modelValue = false"
      >
        <NuxtIcon
          name="lineicons:globe-1"
          class="h-full w-full text-pink-600"
        />
      </button>
    </div>
    <ul class="bg-white/80">
      <li
        v-for="({ key, label, to }) of locales"
        :key
        class="grid h-10 border-b border-pink-200 font-medium text-gray-400"
        :class="{
          'font-medium text-pink-600': currentLocale?.key === key,
        }"
      >
        <button
          class="flex items-center justify-center hover:text-pink-600"
          :to
          @click="navigateTo(to, { external: true })"
        >
          {{ label }}
        </button>
      </li>
    </ul>
  </div>
</template>
