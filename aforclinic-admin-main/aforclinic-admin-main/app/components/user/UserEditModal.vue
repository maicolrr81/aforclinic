<script setup lang="ts">
import type { QForm } from 'quasar';

const {
  userId,
} = defineProps<{
  userId: string;
}>();

const emit = defineEmits<{
  ok: [result?: string];
  close: [];
}>();

const { $api } = useNuxtApp();

const form = useTemplateRef<QForm | null>(`form`);

// const { onChange } = useFileUpload({ multiple: false, accept: 'image/*' });

if (import.meta.client) {
  // console.log(`====userId`, userId);
}

const { data: userData, error, execute, clear } = useAsyncData<Partial<User>>(async () => {
  const res = await $api<ApiResponse<User>>(`/users/${userId}`);
  return res.data;
}, {
  default: () => ({}),
  immediate: false,
  deep: true,
});

const { error: updateError, execute: update } = useAsyncData(() => $api<ApiResponse>(`/users/${userData.value.id}`, {
  method: `put`,
  body: {
    nickname: userData.value.nickname,
    birthDate: userData.value.birthDate,
    phoneNumber: userData.value.phoneNumber,
  },
}), {
  immediate: false,
});

const { execute: deleteExecute } = useAsyncData(() => $api(`/users/${userData.value.id}`, {
  method: `delete`,
}), {
  immediate: false,
});

async function deleteClick() {
  await deleteExecute();

  emit('ok', `삭제`);
}

async function saveClick() {
  if (!(await form.value?.validate(false))) {
    console.log(`유효성 오류`);
    return;
  }

  // console.log(userData.value.id);

  try {
    await update();
    console.log(`updateError`, updateError.value);
  }
  catch {
    console.log(`updateError`, updateError);
  }

  emit('ok', '1234');
}

watch(error, () => {
  emit(`close`);
});

// onChange((...files) => {
//   userData.value.profileImage = files[0]?.src;
// });
</script>

<template>
  <QDialog
    @before-show="execute()"
    @before-hide="clear()"
  >
    <QCard class="w-[48rem]">
      <QCardSection class="row items-center">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            사용자 수정
          </QToolbarTitle>
          <QSpace />
          <QBtn
            v-close-popup
            icon="close"
            flat
            round
            dense
          />
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <QForm
          ref="form"
          class="grid grid-cols-1 gap-x-4 gap-y-2"
        >
          <UiEnumSelect
            v-model="userData.role"
            name="adminrole"
            :rules="roleRules"
            class="mb-5"
            readonly
          />
          <QInput
            v-model="userData.username"
            type="text"
            label="아이디(이메일)"
            outlined
            class="mb-5"
            readonly
          >
            <template #append>
              <QAvatar v-if="userData.provider === 'KAKAO'">
                <img src="/images/kakao_icon.png">
              </QAvatar>
            </template>
          </QInput>
          <QInput
            v-model="userData.nickname"
            type="text"
            label="이름"
            outlined
            :rules="nameRules"
            lazy-rules="ondemand"
          />
          <QInput
            v-model="userData.birthDate"
            label="생년월일"
            outlined
            clearable
            :rules="dateRules"
            lazy-rules="ondemand"
          >
            <template #append>
              <UiDatePicker v-model="userData.birthDate" />
            </template>
          </QInput>
          <QInput
            v-model="userData.phoneNumber"
            type="tel"
            label="전화번호"
            outlined
            clearable
            :rules="phoneNumberRules"
            lazy-rules="ondemand"
          />
          <QInput
            v-model="userData.createdAt"
            type="text"
            label="생성일"
            outlined
            readonly
            class="mb-5"
          />
          <QInput
            v-model="userData.deletedAt"
            type="text"
            label="삭제일"
            outlined
            readonly
          />
        </QForm>
      </QCardSection>
      <QCardActions class="p-4">
        <QBtn
          color="red"
          @click="deleteClick"
        >
          삭제
        </QBtn>
        <QSpace />
        <QBtn v-close-popup>
          취소
        </QBtn>
        <QBtn
          color="blue"
          @click="saveClick"
        >
          저장
        </QBtn>
      </QCardActions>
    </QCard>
  </QDialog>
</template>

<style module>
</style>
