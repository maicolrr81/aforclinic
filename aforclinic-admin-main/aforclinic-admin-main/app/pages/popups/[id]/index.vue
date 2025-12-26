<script setup lang="ts">
const runtimeConfig = useRuntimeConfig();

const { $api, $dialog } = useNuxtApp();
const route = useRoute();

const { data, error } = await useAsyncData<Partial<Popup>>(async () => {
  const data = await $api<ApiResponse<Popup>>(`/popups/${route.params.id}`);
  return data.data;
}, {
  default: () => ({}),
});

// 데이터가 없는 경우
if (error.value) {
  showError({ statusCode: 404 });
}

const id = ref(data.value.id!);
const title = ref(data.value.title!);
const imageId = ref(data.value.imageId!);
const linkUri = ref(data.value.linkUri!);
const startDate = ref(data.value.startDate!);
const endDate = ref(data.value.endDate!);
const createdAt = ref(data.value.createdAt);
const createdBy = ref(data.value.createdBy);
const modifiedAt = ref(data.value.modifiedAt);
const modifiedBy = ref(data.value.modifiedBy);

async function remove() {
  const confirmed = await $dialog.confirm(`팝업을 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });
  if (confirmed) {
    try {
      await $api(`/popups/${id.value}`, {
        method: `patch`,
        body: {
          status: `DELETED`,
        } satisfies Partial<UpdatePopup>,
      });
      navigateTo(`/popups`, { replace: true });
    }
    catch {
      $dialog.alert(`팝업 삭제 중 오류가 발생했습니다.`);
    }
  }
}
</script>

<template>
  <QPage class="container mx-auto max-w-screen-md">
    <QCard>
      <QCardSection class="space-y-4">
        <QList separator>
          <QItem>
            <QItemLabel header>
              제목
            </QItemLabel>
            <QItemSection>
              {{ title }}
            </QItemSection>

            <QItemLabel header>
              링크
            </QItemLabel>
            <QItemSection>
              {{ linkUri }}
            </QItemSection>
          </QItem>

          <QItem>
            <QItemLabel header>
              시작일
            </QItemLabel>
            <QItemSection>
              {{ startDate }}
            </QItemSection>

            <QItemLabel header>
              종료일
            </QItemLabel>
            <QItemSection>
              {{ endDate }}
            </QItemSection>
          </QItem>

          <QItem>
            <QItemLabel header>
              등록일
            </QItemLabel>
            <QItemSection>
              {{ createdAt }}
            </QItemSection>

            <QItemLabel header>
              수정일
            </QItemLabel>
            <QItemSection>
              {{ modifiedAt }}
            </QItemSection>
          </QItem>

          <QItem>
            <QItemLabel header>
              등록자
            </QItemLabel>
            <QItemSection>
              {{ createdBy }}
            </QItemSection>

            <QItemLabel header>
              수정자
            </QItemLabel>
            <QItemSection>
              {{ modifiedBy }}
            </QItemSection>
          </QItem>
        </QList>

        <QImg
          v-if="imageId"
          :src="`${runtimeConfig.public.apiBase}/files/${imageId}`"
        />
      </QCardSection>

      <QCardActions class="justify-end gap-x-4 p-4">
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
          to="/popups"
        />
        <QBtn
          type="submit"
          class="!px-8 !py-2"
          color="blue"
          label="수정"
          :to="`/popups/${id}/edit`"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
