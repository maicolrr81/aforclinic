<script setup lang="ts">
const { $api } = useNuxtApp();

const route = useRoute();
const router = useRouter();

const text = ref(route.query.text as string || ``);
const page = ref(Number(route.query.page || 1));

// 파라미터
const params = computed(() => ({
  type: `DEFAULT`,
  text: text.value,
  page: page.value,
}));

// 라우트 변경 감지하여 입력값 동기화
watch(() => route.query, (newQuery) => {
  text.value = (newQuery.text) as string || ``;
  page.value = Number(newQuery.page) || 1;
}, {
  immediate: true,
});

// 서버 데이터 불러오기
const { data } = await useAsyncData<ApiPagedResponse<Post>>(async () => {
  const data = await $api<ApiPagedResponse<Post>>(`/public/posts`, {
    params: params.value,
  });
  return data;
}, {
  watch: [() => route.query],
});

// 데이터
const rows = computed(() => (data.value?.data.content ?? []));
const totalPages = computed(() => data.value?.data.page?.totalPages ?? 1);
const totalElements = computed(() => data.value?.data.page?.totalElements ?? 0);

// 페이지 변경
function onChangedPage(newPage: number) {
  page.value = newPage;
  updateQueryParams();
}

// 조회
function updateQueryParams() {
  router.push({
    path: route.path,
    query: { ...params.value },
  });
}

function onRefresh() {
  page.value = 1;
  updateQueryParams();
}
</script>

<template>
  <h1 class="p-4 text-xl font-bold md:p-8">
    게시판
  </h1>
  <div class="px-4 md:px-8">
    <!-- 필터 + 검색 -->
    <div class="mb-4 flex flex-wrap items-center justify-between gap-4">
      <div class="flex space-x-2" />

      <!-- 검색창 -->
      <div class="flex items-center space-x-2">
        <input
          v-model="text"
          type="text"
          placeholder="검색어"
          class="w-64 rounded border px-3 py-2"
          @keyup.enter="onRefresh"
        >
        <button
          class="min-w-16 rounded bg-black px-3 py-2 text-white hover:bg-black/75"
          @click="onRefresh"
        >
          조회
        </button>
      </div>
    </div>

    <!-- 게시글 테이블 -->
    <table class="w-full border-t text-sm">
      <thead class="bg-gray-100 text-left">
        <tr>
          <th class="border-b p-2">
            순번
          </th>
          <th class="border-b p-2">
            제목
          </th>
          <th class="border-b p-2">
            등록일시
          </th>
        </tr>
      </thead>
      <tbody class="overflow-auto">
        <tr
          v-for="post in rows"
          :key="post.id"
          class="w-80 hover:bg-gray-50"
        >
          <td class="w-20 border-b p-2">
            {{ post.sequence }}
          </td>
          <td class="w-60 border-b p-2">
            <NuxtLink
              class="hover:underline"
              :to="`/boards/${post.id}`"
            >
              {{ post.title }}
            </NuxtLink>
          </td>
          <td class="w-40 border-b p-2">
            {{ post.createdAt }}
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 전체 개수 -->
    <div class="mt-2 text-sm">
      전체 개수: {{ totalElements }}
    </div>

    <!-- 페이지네이션 -->
    <div
      v-if="totalElements > 0"
      class="mt-4 flex items-center justify-center gap-x-1"
    >
      <button
        class="flex h-8 w-8 items-center justify-center rounded-full hover:bg-black hover:text-white disabled:text-gray-200"
        :class=" {
          'pointer-events-none': page === 1,
        }"
        :disabled="page === 1"
        @click="onChangedPage(page - 1)"
      >
        <NuxtIcon
          name="mdi:chevron-left"
          size="32"
        />
      </button>

      <button
        v-for="i in totalPages"
        :key="i"
        class="flex h-8 w-8 items-center justify-center rounded-full hover:bg-black hover:text-white"
        :class="{
          'bg-black font-medium text-white': page === i,
        }"
        @click="onChangedPage(i)"
      >
        {{ i }}
      </button>
      <button
        class="flex h-8 w-8 items-center justify-center rounded-full hover:bg-black hover:text-white disabled:text-gray-200"
        :disabled="page === totalPages"
        :class=" {
          'pointer-events-none': page === totalPages,
        }"
        @click="onChangedPage(page + 1)"
      >
        <NuxtIcon
          name="mdi:chevron-right"
          size="32"
        />
      </button>
    </div>
  </div>
</template>
