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

const { open, onChange } = useFileUpload();

const id = ref(data.value.id!);
const title = ref(data.value.title!);
const imageId = ref(data.value.imageId!);
const linkUri = ref(data.value.linkUri!);
const startDate = ref(data.value.startDate!);
const endDate = ref(data.value.endDate!);
const displayOrder = ref(data.value.displayOrder ?? 0);
const status = ref(data.value.status!);

const popupTypeOptions = computed<{
  label: string;
  value: string;
}[]>(() => [
  { label: `목록 팝업`, value: 'CAROUSEL' },
  { label: `개별 팝업`, value: 'MODAL' },
]);
const popupType = ref<string>(data.value.type ?? '');
const positionTop = ref(data.value.positionTop);
const positionLeft = ref(data.value.positionLeft);

onChange(item => imageId.value = item.id);

async function submit() {
  try {
    if (!title.value) {
      $dialog.alert(`제목이 없습니다.`);
      return;
    }
    if (!imageId.value) {
      $dialog.alert(`이미지가 없습니다.`);
      return;
    }
    if (!startDate.value) {
      $dialog.alert(`시작일이 없습니다.`);
      return;
    }
    if (!endDate.value) {
      $dialog.alert(`종료일이 없습니다.`);
      return;
    }
    if (startDate.value > endDate.value) {
      $dialog.alert(`시작일이 종료일보다 클 수 없습니다.`);
      return;
    }

    await $api(`/popups/${id.value}`, {
      method: `put`,
      body: {
        type: popupType.value,
        positionTop: positionTop.value,
        positionLeft: positionLeft.value,
        title: title.value,
        imageId: imageId.value,
        linkUri: linkUri.value,
        startDate: startDate.value,
        endDate: endDate.value,
        displayOrder: displayOrder.value,
        status: status.value,
      } satisfies UpdatePopup,
    });
    navigateTo(`/popups`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `팝업 수정 중 오류가 발생했습니다.`);
  }
}

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
      <QCardSection class="flex justify-between  pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            팝업 수정
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>

      <QCardSection class="space-y-4">
        <QSelect
          v-model="popupType"
          :options="popupTypeOptions"
          label="유형"
          map-options
          emit-value
          :readonly="true"
        />
        <div
          v-if="popupType === 'MODAL'"
          class="flex gap-x-2"
        >
          <QInput
            v-model="positionLeft"
            type="number"
            label="위치-X (px)"
            :min="0"
            step="1"
            class="flex-1"
          />
          <QInput
            v-model="positionTop"
            type="number"
            label="위치-Y (px)"
            :min="0"
            step="1"
            class="flex-1"
          />
        </div>
        <QInput
          v-model="title"
          type="text"
          label="제목"
        />
        <QImg
          v-if="imageId"
          :src="`${runtimeConfig.public.apiBase}/files/${imageId}`"
        />
        <QBtn
          class="w-full"
          color="blue-5"
          @click="open()"
        >
          이미지 선택 (이미지 10MB 미만으로 선택 - 목록 팝업: 1000*470제한)
        </QBtn>
        <QInput
          v-model="linkUri"
          type="text"
          label="링크"
        />
        <UiDatePicker
          v-model="startDate"
          label="시작일"
        />
        <UiDatePicker
          v-model="endDate"
          label="종료일"
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
          label="저장"
          @click="submit()"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
