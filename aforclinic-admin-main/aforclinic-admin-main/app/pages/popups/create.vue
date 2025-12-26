<script setup lang="ts">
const runtimeConfig = useRuntimeConfig();

const { $api, $dialog } = useNuxtApp();

const dayjs = useDayjs();
const { open, onChange } = useFileUpload();

const title = ref(``);
const imageId = ref(``);
const linkUri = ref(``);
const startDate = ref(dayjs().format(`YYYY-MM-DD`));
const endDate = ref(``);
const displayOrder = ref(0);

const popupTypeOptions = computed<{
  label: string;
  value: string;
}[]>(() => [
  { label: `목록 팝업`, value: 'CAROUSEL' },
  { label: `개별 팝업`, value: 'MODAL' },
]);
const popupType = ref<string>('CAROUSEL');
const positionTop = ref();
const positionLeft = ref();

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

    await $api(`/popups`, {
      method: `post`,
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
      } satisfies CreatePopup,
    });
    navigateTo(`/popups`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `팝업 등록 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto max-w-screen-md">
    <QCard>
      <QCardSection class="flex justify-between  pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            팝업 등록
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
          color="grey"
          label="취소"
          to="/popups"
        />
        <QBtn
          type="submit"
          class="!px-8 !py-2"
          color="blue"
          label="등록"
          @click="submit()"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
