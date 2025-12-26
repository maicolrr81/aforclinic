<script setup lang="ts">
const { $api } = useNuxtApp();

const { data: settings } = await useAsyncData<ApiResponse<SiteSetting>>(() => $api(`/public/site-settings`));

const text = ref(``);
const recommendedKeywords = computed<string[]>(() => {
  const raw = settings.value?.data.settings.recommendedKeywords;
  return raw ? JSON.parse(raw) : [];
});
</script>

<template>
  <aside class="fixed left-[calc((100%-768px)/2-768px/2)] hidden h-full w-[344px] px-6 2xl:flex 2xl:flex-col 2xl:justify-center 2xl:gap-y-16">
    <div>
      <h3 class="text-2xl font-medium">
        당신의<br>
        아름다움을<br>
        항상<br>
        연구합니다.
      </h3>
      <p class="mt-3 text-sm text-[#3d3d3b]">
        원하는 시술을 검색해보세요.
      </p>
      <label class="mt-4 flex h-10 appearance-none items-center gap-4 bg-white px-4">
        <input
          v-model="text"
          type="text"
          class="h-full grow bg-transparent text-xs font-medium focus:outline-none"
          placeholder="원하는 시술을 검색해 보세요."
          @keyup.enter="navigateTo(`/procedures?text=${text}`)"
        >
        <button
          class="h-6 w-6"
          @click="navigateTo(`/procedures?text=${text}`)"
        >
          <NuxtIcon
            class="text-gray-400"
            name="mdi:search"
            size="24"
          />
        </button>
      </label>
      <div class="mt-4 flex gap-4 text-xs font-medium text-gray-500">
        <NuxtLink
          v-for="(recommendedKeyword, index) of recommendedKeywords"
          :key="index"
          :to="`/procedures?text=${recommendedKeyword}`"
          class="underline"
        >
          {{ recommendedKeyword }}
        </NuxtLink>
      </div>
    </div>
  </aside>
</template>
