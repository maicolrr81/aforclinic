<script setup lang="ts">
import type { QTableColumn } from 'quasar';
import { VueDraggableNext } from 'vue-draggable-next';

const { $api, $dialog } = useNuxtApp();

const route = useRoute();
const router = useRouter();

const { data: categories } = await useAsyncData<ApiResponse<ProductCategory[]>>(() => $api(`/categories/names`));
const categoryOptions = computed(() => categories.value?.data.map(category => ({
  label: category.name,
  value: category.id,
})));

const bundleOptions = computed<{
  label: string;
  value: boolean | undefined;
}[]>(() => [
  { label: `전체`, value: undefined },
  { label: `패키지`, value: true },
  { label: `상품`, value: false },
]);

const rowsPerPageOptions = computed<{
  label: string;
  value: number | undefined;
}[]>(() => [
  { label: `전체`, value: undefined },
  { label: `10`, value: 10 },
  { label: `20`, value: 20 },
  { label: `50`, value: 50 },
  { label: `100`, value: 100 },
]);

const categoryId = ref(route.query.categoryId);
const bundle = ref<boolean | undefined>(route.query.bundle === `true` ? true : route.query.bundle === `false` ? false : undefined);
const text = ref(route.query.text as string || ``);
const page = ref(Number(route.query.page || 1));
const size = ref(false);
// const size = ref<number | undefined>(route.query.size ? Number(route.query.size) : undefined);

const pagination = reactive({
  page, // 현재 페이지
  // rowsPerPage: size,
  rowsNumber: 0, // 전체 개수
});

// 파라미터
const params = computed(() => {
  const baseParams = {
    categoryId: categoryId.value,
    bundle: bundle.value,
    text: text.value,
    page: pagination.page,
  };
  return baseParams;
  // return pagination.rowsPerPage === undefined
  //   ? { ...baseParams, unpaged: 'true' }
  //   : { ...baseParams, size: pagination.rowsPerPage };
});

// 컬럼 정보
// const columns = computed<QTableColumn[]>(() => ([
//   { field: `sequence`, name: 'sequence', label: `순번`, align: 'center', style: `width: 60px` },
//   { field: `categoryName`, name: 'categoryName', label: `카테고리`, align: 'center', style: `width: 120px` },
//   { field: `bundle`, name: 'bundle', label: `상품/패키지`, align: 'center', style: `width: 60px` },
//   { field: `name`, name: 'name', label: `이름`, align: 'center', style: `width: 120px` },
//   { field: `description`, name: 'description', label: `설명`, align: 'left' },
//   { field: `discountedPrice`, name: 'discountedPrice', label: `가격`, align: 'center', style: `width: 120px` },
//   { field: `createdBy`, name: `createdBy`, label: `등록자`, align: `center`, style: `width: 60px` },
//   { field: `createdAt`, name: `createdAt`, label: `등록일시`, align: `center`, style: `width: 60px` },
//   { field: `modifiedBy`, name: `modifiedBy`, label: `수정자`, align: `center`, style: `width: 100px` },
//   { field: `modifiedAt`, name: `modifiedAt`, label: `수정일시`, align: `center`, style: `width: 60px` },
// ]));

// 라우트 변경 감지하여 입력값 동기화
watch(() => route.query, (newQuery) => {
  categoryId.value = (newQuery.categoryId) as string || undefined;
  bundle.value = (newQuery.bundle === `true` ? true : newQuery.bundle === `false` ? false : undefined);
  text.value = (newQuery.text) as string || ``;
  page.value = Number(newQuery.page) || 1;
  // size.value = newQuery.size ? Number(newQuery.size) : undefined;
}, {
  immediate: true,
});

// 서버 데이터 불러오기
const { data, refresh } = useAsyncData<ApiPagedResponse<Product>>(async () => {
  const productData = await $api<ApiPagedResponse<Product>>(`/products`, {
    params: params.value,
  });
  pagination.rowsNumber = productData.data.page.totalElements ?? 0;
  return productData;
}, {
  watch: [() => route.query],
});

// 데이터
const rows = ref<Product[]>([]);

watchEffect(() => {
  rows.value = data.value?.data.content ?? [];
});

// const rows = computed(() => (data.value?.data.content ?? []).map((row) => {
//   return {
//     ...row,
//     bundle: (row.bundle === true ? `패키지` : `상품`),
//   };
// }));

const totalPages = computed(() => data.value?.data.page.totalPages ?? 1);
const totalElements = computed(() => data.value?.data.page.totalElements ?? 0);

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

async function changeDisplayOrder() {
  try {
    await $api(`/products/change-displayorder`, {
      method: `put`,
      body: rows.value.map((row, idx) => ({
        productId: row.id,
        categoryId: categoryId.value,
        displayOrder: idx,
      })),
    });
    refresh();
  }
  catch {
    $dialog.alert(`카테고리별 상품의 순서 변경 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            상품 목록
          </QToolbarTitle>
          <QBtn
            color="blue"
            to="/products/create"
          >
            상품 등록
          </QBtn>
          <QBtn
            color="blue-9"
            to="/products/packages/create"
          >
            패키지 등록
          </QBtn>
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <QForm
          class="flex flex-col justify-between gap-4 md:flex-row md:gap-2"
          @submit.prevent="updateQueryParams"
        >
          <fieldset class="grid flex-1 grid-cols-1 gap-4 md:grid-cols-4 md:gap-2 lg:grid-cols-6">
            <QSelect
              v-model="categoryId"
              :options="categoryOptions"
              label="카테고리"
              map-options
              emit-value
              class="w-full"
              dense
              clearable
              @update:model-value="updateQueryParams()"
            />
            <QSelect
              v-model="bundle"
              :options="bundleOptions"
              label="패키지여부"
              map-options
              emit-value
              class="w-full"
              dense
              clearable
              @update:model-value="updateQueryParams()"
            />
            <QSelect
              v-if="size"
              v-model="size"
              :options="rowsPerPageOptions"
              label="검색개수"
              map-options
              emit-value
              class="w-full"
              dense
              clearable
              @update:model-value="updateQueryParams()"
            />
          </fieldset>
          <fieldset class="flex flex-col gap-4 md:flex-row md:gap-2">
            <QInput
              v-model="text"
              type="text"
              label="검색어"
              clearable
              autofocus
              class="w-full"
              dense
              @keyup.enter="updateQueryParams"
            >
              <template #append>
                <QIcon
                  name="search"
                  class="cursor-pointer"
                  @click="updateQueryParams"
                />
              </template>
            </QInput>
          </fieldset>
        </QForm>
      </QCardSection>

      <QCardSection>
        <QList
          bordered
          class="overflow-x-auto transition"
        >
          <div class="min-w-[1450px]">
            <QItem class="grid grid-cols-[70px_130px_100px_180px_1fr_100px_120px_150px_120px_150px] items-center border-b text-xs font-bold">
              <div class="text-center">
                순서
              </div>
              <div class="text-center">
                카테고리
              </div>
              <div class="text-center">
                상품/패키지
              </div>
              <div class="text-center">
                이름
              </div>
              <div class="text-center">
                설명
              </div>
              <div class="text-center">
                가격
              </div>
              <div class="text-center">
                등록자
              </div>
              <div class="text-center">
                등록일시
              </div>
              <div class="text-center">
                수정자
              </div>
              <div class="text-center">
                수정일시
              </div>
            </QItem>
            <VueDraggableNext
              v-model="rows"
              animation="200"
              handle=".handle"
              @change="changeDisplayOrder()"
            >
              <QItem
                v-for="(row, index) of rows"
                :key="row.id"
                class="grid grid-cols-[70px_130px_100px_180px_1fr_100px_120px_150px_120px_150px] items-center text-sm"
                :class="{
                  'border-t': index > 0,
                }"
              >
                <div
                  v-if="categoryId != null && bundle === undefined && text.trim() === ''"
                  class="handle flex h-5 cursor-grab flex-col px-1 text-center active:cursor-grabbing"
                >
                  <NuxtIcon
                    name="mdi:drag-vertical"
                    size="20"
                  />
                  <span>
                    {{ row.sequence }}
                  </span>
                </div>
                <div
                  v-else
                  class="flex h-5 flex-col px-1 text-center"
                >
                  <span>
                    {{ row.sequence }}
                  </span>
                </div>
                <div class="px-1 text-center">
                  {{ row.categoryName }}
                </div>
                <div class="px-1 text-center">
                  {{ (row.bundle === true ? `패키지` : `상품`) }}
                </div>
                <div class="px-1 text-center">
                  <NuxtLink
                    v-if="row.bundle === true"
                    class="hover:underline"
                    :to="`/products/packages/${row.id}/edit`"
                  >
                    {{ row.name }}
                  </NuxtLink>
                  <NuxtLink
                    v-else
                    class="hover:underline"
                    :to="`/products/${row.id}/edit`"
                  >
                    {{ row.name }}
                  </NuxtLink>
                </div>
                <div class="px-1 text-start">
                  {{ row.description }}
                </div>
                <div class="px-1 text-center">
                  {{ currency(row.discountedPrice) }}
                </div>
                <div class="px-1 text-center">
                  {{ row.createdBy }}
                </div>
                <div class="px-1 text-center">
                  {{ row.createdAt }}
                </div>
                <div class="px-1 text-center">
                  {{ row.modifiedBy }}
                </div>
                <div class="px-1 text-center">
                  {{ row.modifiedAt }}
                </div>
              </QItem>
            </VueDraggableNext>
          </div>
        </QList>
        <div class="mt-4 flex items-center justify-between">
          <span>
            전체 개수: {{ totalElements }}
          </span>
          <QPagination
            v-if="categoryId == null || bundle !== undefined || text.trim() !== ''"
            v-model="pagination.page"
            :max="totalPages"
            color="grey-8"
            @update:model-value="onChangedPage"
          />
          <QPagination
            v-if="categoryId == null || bundle !== undefined || text.trim() !== ''"
            v-model="pagination.page"
            :max="totalPages"
            color="grey-8"
            input
            input-class="text-xs m-0"
            :boundary-links="false"
            :direction-links="false"
            @update:model-value="onChangedPage"
          />
        </div>
      </QCardSection>
    </QCard>
  </QPage>
</template>
