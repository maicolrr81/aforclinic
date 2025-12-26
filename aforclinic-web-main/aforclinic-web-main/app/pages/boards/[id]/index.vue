<script setup lang="ts">
const { $api } = useNuxtApp();

const route = useRoute();

// 서버 데이터 불러오기
const { data } = await useAsyncData<Partial<Post>>(async () => {
  const data = await $api<ApiResponse<Post>>(`/public/posts/${route.params.id}`);
  return data.data;
}, {
  default: () => ({}),
});

function linkList() {
  navigateTo('/boards');
}
</script>

<template>
  <div class="border-b p-4 md:p-8">
    <div class="flex justify-between">
      <h1 class="text-xl font-bold">
        {{ data.title }}
      </h1>
      <button
        class="min-w-20 rounded bg-black px-3 py-2 text-sm text-white hover:bg-black/75"
        @click="linkList"
      >
        목록
      </button>
    </div>

    <div class="mt-5 flex items-center gap-4">
      <div>
        {{ data.createdBy }}
      </div>
      <time :datetime="data.createdAt">
        {{ data.createdAt }}
      </time>
    </div>
  </div>
  <div class="p-4 md:p-8">
    <div
      class="prose min-h-80 max-w-none"
      v-html="data.content"
    />
  </div>
</template>
