<script setup lang="ts">
import type { Event } from '~~/shared/types/Event';
import { VueDraggableNext } from 'vue-draggable-next';

const { $api, $dialog } = useNuxtApp();
const route = useRoute();
const { onChange: onThumbnailChange, open } = useFileUpload();

const { data, error } = await useAsyncData<Partial<Event>>(async () => {
  const data = await $api<ApiResponse<Event>>(`/events/${route.params.id}`);
  return data.data;
}, {
  default: () => ({}),
});

// 데이터가 없는 경우
if (error.value) {
  showError({ statusCode: 404 });
}

const { data: categories } = await useAsyncData(async () => {
  const data = await $api<ApiPagedResponse<ProductCategory>>(`/categories`, {
    method: `get`,
    params: {
      unpaged: true,
    },
  });
  return data.data.content.map(item => ({ label: item.name, value: item.id }));
});

const id = ref(data.value.id!);
const title = ref(data.value.title!);
const description = ref(data.value.description);
const imageId = ref<string | undefined>(data.value.imageId!);
const startDate = ref(data.value.startDate!);
const endDate = ref(data.value.endDate!);
const status = ref(data.value.status!);

const categoryId = ref<string>();
const selectedProduct = ref<string>();
const selectedEventProducts = ref<UpdateEventProduct[]>(
  (!data.value.products
    ? []
    : data.value.products.map(v => ({
        eventId: v.eventId ?? ``,
        productId: v.productId,
        productName: v.productName ?? ``,
        adjustedPrice: v.adjustedPrice ?? 0,
        discountedPrice: v.discountedPrice ?? 0,
        mainVisible: v.mainVisible ?? false,
        displayOrder: v.displayOrder ?? 0,
      }))),
);

const post = computed(() => data.value.post);
const isPost = ref(!!post.value);

const postId = computed(() => post.value?.id);
const content = ref(post.value?.content);

const { data: products } = await useAsyncData<Product[]>(async () => {
  selectedProduct.value = undefined;
  if (!categoryId.value) { return []; }
  const data = await $api<ApiPagedResponse<Product>>(`/products`, {
    params: {
      categoryId: categoryId.value,
    },
  });
  return data.data.content ?? [];
}, {
  watch: [() => categoryId.value],
});

const productOptions = ref<{ label: string; value: Product }[]>();

function updateProductOptions(filter = '') {
  const lower = filter.toLowerCase();
  productOptions.value = products.value
    ?.filter(product =>
      !selectedEventProducts.value.find(v => (product.id === v.productId))
      && product.name.toLowerCase().includes(lower),
    )
    ?.map(product => ({
      label: product.name,
      value: product,
    }));
}

function productFilterFn(val: string, update: (cb: () => void) => void) {
  update(() => {
    updateProductOptions(val || '');
  });
}

watch([products, selectedEventProducts], () => {
  updateProductOptions('');
}, {
  deep: true,
});

function selectProduct(newValue: Product) {
  const duplicatedProduct = selectedEventProducts.value.find(product => product.productId === newValue.id);
  if (duplicatedProduct) {
    return;
  }
  const foundedProduct = products.value?.find((product) => {
    return (product.id === newValue.id);
  });
  if (foundedProduct) {
    selectedEventProducts.value.push({
      productId: foundedProduct.id,
      productName: foundedProduct.name,
      adjustedPrice: foundedProduct.adjustedPrice,
      discountedPrice: foundedProduct.discountedPrice,
      mainVisible: false,
      displayOrder: 0,
    });
    selectedProduct.value = foundedProduct.name;
  }
}

function deleteBundleProduct(productId: string) {
  selectedEventProducts.value = selectedEventProducts.value.filter(v => v.productId !== productId);
}

onThumbnailChange((item) => {
  imageId.value = item.id;
});

function discountRate(adjustedPrice: number, discountedPrice: number) {
  if (adjustedPrice === 0 && discountedPrice === 0) {
    return 0;
  }
  return Math.round((1 - discountedPrice / adjustedPrice) * 100);
}

async function remove() {
  const confirmed = await $dialog.confirm(`이벤트를 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });

  if (!confirmed) { return; }

  try {
    await $api(`/events/${id.value}`, {
      method: `patch`,
      body: {
        status: `DELETED`,
        ...(postId.value && {
          post: {
            id: postId.value,
            status: `DELETED`,
          },
        }),
      }, // satisfies UpdateEventStatus | UpdateEventStatusWithPost,
    });
    navigateTo(`/products/events`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `이벤트 삭제 중 오류가 발생했습니다.`);
  }
}

async function changeDisplayOrder() {
  selectedEventProducts.value.forEach((product, idx) => {
    product.displayOrder = idx;
  });
}

async function submit() {
  try {
    if (!title.value) {
      $dialog.alert(`제목이 입력되지 않았습니다.`);
      return;
    }

    selectedEventProducts.value.forEach((v) => {
      if (!v.adjustedPrice) {
        v.adjustedPrice = 0;
      }
      if (!v.discountedPrice) {
        v.discountedPrice = 0;
      }
    });

    await $api(`/events/${id.value}`, {
      method: `put`,
      body: {
        title: title.value,
        description: description.value,
        imageId: imageId.value,
        startDate: startDate.value,
        endDate: endDate.value,
        status: status.value,
        products: selectedEventProducts.value,
        ...(post.value && {
          post: {
            type: `EVENT`,
            id: postId.value!,
            title: title.value,
            content: content.value!,
            status: post.value?.status,
          },
        }),
      } satisfies UpdateEvent,
    });
    navigateTo(`/products/events`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `이벤트 수정 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="flex justify-between pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            이벤트 수정
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <h3 class="pb-4 pl-3 text-lg">
          기본 정보
        </h3>
        <QForm class="flex flex-col gap-4">
          <div class="space-y-4">
            <QInput
              v-model="title"
              label="제목"
              autofocus
              clearable
            />
            <QInput
              v-model="description"
              type="textarea"
              label="설명"
              autogrow
              clearable
            />
            <div class="flex gap-4">
              <UiDatePicker
                v-model="startDate"
                class="flex-1"
                clearable
                label="시작일"
              />
              <UiDatePicker
                v-model="endDate"
                class="flex-1"
                clearable
                label="종료일"
              />
            </div>
          </div>
        </QForm>
      </QCardSection>

      <template v-if="isPost">
        <QCardSection class="border-t">
          <ClientOnly>
            <TiptapEditor v-model="content" />
          </ClientOnly>
        </QCardSection>
      </template>

      <template v-else>
        <QCardSection class="border-t">
          <div class="flex items-center justify-between gap-x-4">
            <h3 class="pb-4 pl-3 text-lg">
              이미지
            </h3>
            <QSpace />
            <p class="text-gray-500">
              이미지는 16:9 비율의 10MB 미만으로 선택하세요.
            </p>
            <QBtn
              v-if="imageId"
              color="red"
              label="삭제"
              @click="imageId = undefined"
            />
            <QBtn
              v-else
              color="blue"
              label="선택"
              @click="open()"
            />
          </div>
          <div
            v-if="imageId"
            class="mt-4"
          >
            <QImg
              class="max-h-96 w-full"
              :src="`${$config.public.apiBase}/files/${imageId}`"
              fit="contain"
            />
          </div>
        </QCardSection>

        <QCardSection class="border-t">
          <h3 class="pb-4 pl-3 text-lg">
            상품
          </h3>
          <fieldset class="flex-1 space-y-4">
            <QSelect
              v-model="categoryId"
              label="카테고리"
              :options="categories"
              emit-value
              map-options
            />
            <QSelect
              v-model="selectedProduct"
              :options="productOptions"
              emit-value
              map-options
              input-debounce="0"
              label="상품을 검색합니다."
              :readonly="!categoryId"
              use-input
              hide-selected
              class="w-full md:flex-1"
              @filter="productFilterFn"
              @update:model-value="selectProduct"
            />
            <QList
              bordered
              class="rounded"
            >
              <QExpansionItem
                expand-separator
                label="이벤트에 등록할 상품 목록"
                class="text-primary"
                default-opened
              >
                <VueDraggableNext
                  v-model="selectedEventProducts"
                  animation="200"
                  handle=".handle"
                  @change="changeDisplayOrder()"
                >
                  <QItem
                    v-for="product of selectedEventProducts"
                    :key="product.productId"
                    class="flex flex-col items-start gap-x-4 gap-y-2 text-black lg:flex-row lg:items-center"
                    clickable
                  >
                    <div class="handle h-5 cursor-grab active:cursor-grabbing">
                      <NuxtIcon
                        name="mdi:drag-vertical"
                        size="20"
                      />
                    </div>

                    <div
                      class="truncate"
                      :title="product.productName"
                    >
                      {{ product.productName }}
                    </div>
                    <QSpace />
                    <div class="w-full text-lg text-primary lg:w-44">
                      <QCheckbox
                        v-model="product.mainVisible"
                        label="메인화면 표시"
                        class="text-base"
                        color="blue"
                      />
                    </div>
                    <div class="w-full text-lg text-primary lg:w-44">
                      <QInput
                        v-model="product.adjustedPrice"
                        label="정가"
                        type="number"
                        step="100"
                        min="0"
                        dense
                        class="w-full"
                      >
                        <template #append>
                          <span class="text-sm">
                            원
                          </span>
                        </template>
                      </QInput>
                    </div>
                    <div class="w-full text-lg text-primary lg:w-44">
                      <QInput
                        v-model="product.discountedPrice"
                        label="할인가"
                        type="number"
                        step="100"
                        min="0"
                        dense
                        class="w-full"
                      >
                        <template #append>
                          <span class="text-sm">
                            원
                          </span>
                        </template>
                      </QInput>
                    </div>
                    <div class="w-full text-lg text-primary lg:w-44">
                      <QInput
                        :model-value="discountRate(product.adjustedPrice, product.discountedPrice)"
                        label="할인율"
                        type="number"
                        readonly
                        class="w-full"
                        dense
                      >
                        <template #append>
                          %
                        </template>
                      </QInput>
                    </div>
                    <QBtn
                      icon="close"
                      color="red"
                      flat
                      dense
                      class="ml-auto"
                      @click="deleteBundleProduct(product.productId)"
                    />
                  </QItem>
                </VueDraggableNext>
              </QExpansionItem>
            </QList>
          </fieldset>
        </QCardSection>
      </template>

      <QCardActions class="justify-end p-4">
        <QBtn
          class="!px-8 !py-2"
          color="red"
          label="삭제"
          @click="remove()"
        />
        <QSpace />
        <QBtn
          class="!px-8 !py-2"
          color="grey"
          label="목록"
          to="/products/events"
        />
        <QBtn
          type="submit"
          class="!px-8 !py-2"
          color="blue"
          label="저장"
          @click="submit()"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
