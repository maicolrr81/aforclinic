<script setup lang="ts">
import type { Event } from '~~/shared/types/Event';

const { $api, $dialog } = useNuxtApp();
const route = useRoute();

const { data, error } = await useAsyncData<Partial<Event>>(async () => {
  const data = await $api<ApiResponse<Event>>(`/events/${route.params.id}`);
  return data.data;
}, {
  immediate: true,
  default: () => ({}),
});

// 데이터가 없는 경우
if (error.value) {
  showError({ statusCode: 404 });
}

const post = computed(() => data.value.post);
const postId = computed(() => post.value?.id);

function discountRate(adjustedPrice: number, discountedPrice: number) {
  return Math.round((1 - discountedPrice / adjustedPrice) * 100);
}

async function remove() {
  const confirmed = await $dialog.confirm(`이벤트를 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });
  if (confirmed) {
    try {
      await $api(`/events/${route.params.id}`, {
        method: `patch`,
        body: {
          status: `DELETED`,
          ...(postId.value && {
            post: {
              id: postId.value,
              status: `DELETED`,
            },
          }),
        } satisfies UpdateEventStatus | UpdateEventStatusWithPost,
      });
      navigateTo(`/products/events`, { replace: true });
    }
    catch {
      $dialog.alert(`이벤트 삭제 중 오류가 발생했습니다.`);
    }
  }
}
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="pb-0">
        <QToolbar class="pe-0">
          <QToolbarTitle class="text-center">
            {{ data.title }}
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
              v-model="data.title"
              label="제목"
              readonly
            />
            <QInput
              v-model="data.description"
              type="textarea"
              label="설명"
              autogrow
              readonly
            />
            <div class="flex gap-4">
              <QInput
                v-model="data.startDate"
                type="text"
                class="flex-1"
                label="시작일"
                readonly
              />
              <QInput
                v-model="data.endDate"
                type="text"
                class="flex-1"
                label="종료일"
                readonly
              />
            </div>
          </div>
        </QForm>
      </QCardSection>

      <template v-if="!!data.post">
        <QCardSection class="border-t">
          <ClientOnly>
            <TiptapEditor v-model="data.post.content" />
          </ClientOnly>
        </QCardSection>
      </template>
      <template v-else>
        <QCardSection class="border-t">
          <div class="flex items-center justify-between">
            <h3 class="pb-4 pl-3 text-lg">
              이미지
            </h3>
          </div>
          <div
            v-if="data.imageId"
            class="mt-4"
          >
            <QImg
              class="max-h-96 w-full"
              :src="`${$config.public.apiBase}/files/${data.imageId}`"
              fit="contain"
            />
          </div>
        </QCardSection>

        <QCardSection class="border-t">
          <h3 class="pb-4 pl-3 text-lg">
            상품
          </h3>
          <fieldset class="flex-1 space-y-4">
            <QList
              bordered
              class="rounded"
            >
              <QExpansionItem
                expand-separator
                label="이벤트에 등록된 상품 목록"
                class="text-primary"
                default-opened
              >
                <QItem
                  v-for="product of data.products"
                  :key="product.productId"
                  class="flex flex-col items-start gap-x-4 gap-y-2 text-black lg:flex-row lg:items-center"
                  clickable
                >
                  <div
                    class="truncate"
                    :title="product.productName"
                  >
                    {{ product.productName }}
                  </div>
                  <QSpace />
                  <div class="w-full text-lg text-primary lg:w-44">
                    <QInput
                      v-model="product.adjustedPrice"
                      label="정가"
                      type="number"
                      step="100"
                      min="0"
                      dense
                      class="w-full"
                      readonly
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
                      readonly
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
                </QItem>
              </QExpansionItem>
            </QList>
          </fieldset>
        </QCardSection>
      </template>

      <QCardActions class="justify-between border-t p-4">
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
          label="수정"
          :to="`/products/events/${route.params.id}/edit`"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
