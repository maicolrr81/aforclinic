<script setup lang="ts">
import type { Popup } from '#shared/types/Popup';

import { NuxtLink } from '#components';

const {
  title: alt,
  imageId: image,
  linkUri: link,
} = defineProps<Popup>();

const runtimeConfig = useRuntimeConfig();

const src = ref(`${runtimeConfig.public.apiBase}/files/${image}`);

const isExternal = computed(() => link
  ? /^(?:http|https):\/\//.test(link)
  : false);

const wrapperTag = computed(() => {
  if (!link) { return `div`; }
  return isExternal.value ? `a` : NuxtLink;
});

const wrapperAttrs = computed(() => {
  if (!link) { return {}; }
  return isExternal.value
    ? {
        href: link,
        target: `_blank`,
        rel: `noopener`,
      }
    : {
        to: link,
      };
});

function error() {
  src.value = `/images/ImageError.png`;
}
</script>

<template>
  <component
    :is="wrapperTag"
    v-bind="wrapperAttrs"
    class="inline-block h-full w-full"
  >
    <img
      :alt
      class="w-full object-cover"
      :src
      loading="lazy"
      @error="error"
    >
  </component>
</template>
