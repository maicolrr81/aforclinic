<script setup lang="ts">
import type { CreateQuickAppointment } from '#shared/types/Appointment';

const { $api, $dialog, $overlay } = useNuxtApp();

const dayjs = useDayjs();

const { data } = await useAsyncData<ApiResponse<LabelValuePairs>>(() => $api(`/public/categories/options`));
const items = computed(() => data.value?.data ?? []);

const user = useUserStore();

const nickname = ref(user.data?.nickname ?? ``);
const hasErrorNickname = ref(false);

const contact = ref(user.data?.phoneNumber ?? ``);
const hasErrorContact = ref(false);

const selectedCategory = ref(``);
const selectedCategoryName = computed(() => {
  const selectedItem = items.value.find(item => item.value === selectedCategory.value);
  return selectedItem?.label;
});
const hasErrorSelectedCategory = ref(false);

const selectedDate = ref(new Date());
const selectedDateString = computed(() => dayjs(selectedDate.value).format(`YYYY-MM-DD`));
const hasErrorSelectedDate = ref(false);

const selectedTime = ref(``);
const hasErrorSelectedTime = ref(false);

const isAgreedPrivacy = ref(false);
const isAgeOver14 = ref(false);
const isAgreedMarketing = ref(false);
const isEssentialAgreed = computed(() => isAgreedPrivacy.value && isAgeOver14.value);

function clear() {
  nickname.value = ``;
  contact.value = ``;
  selectedCategory.value = ``;
  selectedDate.value = new Date();
  selectedTime.value = ``;
  isAgreedPrivacy.value = false;
  isAgeOver14.value = false;
}

function validate() {
  let hasError = false;
  if (!nickname.value) {
    hasErrorNickname.value = true;
    hasError = true;
  }
  if (!contact.value) {
    hasErrorContact.value = true;
    hasError = true;
  }
  if (!selectedCategory.value) {
    hasErrorSelectedCategory.value = true;
    hasError = true;
  }
  if (!selectedDate.value) {
    hasErrorSelectedDate.value = true;
    hasError = true;
  }
  if (!selectedTime.value) {
    hasErrorSelectedTime.value = true;
    hasError = true;
  }
  return !hasError;
}

async function submit() {
  if (!validate()) { return; }

  const confirm = await $dialog.confirm(
    `이름: ${nickname.value}
연락처: ${contact.value}
상담부위: ${selectedCategoryName.value}
예약일자: ${selectedDateString.value} ${selectedTime.value}
위 내용이 맞다면 "확인"을 눌러주세요.`,
    {
      title: `🎉 예약 내용을 확인해주세요.`,
    },
  );

  if (!confirm) { return; }

  $overlay.visible = true;

  try {
    await $api(`/public/appointments`, {
      method: `post`,
      body: {
        type: `QUICK`,
        nickname: nickname.value,
        contact: contact.value,
        content: `${selectedCategoryName.value}`,
        date: `${selectedDateString.value} ${selectedTime.value}`,
        privacyAgreed: isAgreedPrivacy.value,
        ageConfirmed: isAgeOver14.value,
        marketingAgreed: isAgreedMarketing.value,
      } satisfies CreateQuickAppointment,
    });
    clear();
    $dialog.alert(`예약 요청되었습니다. 병원에서 연락드릴 예정입니다. 감사합니다 :)`);
  }
  catch {
    await $dialog.alert(`예약 신청 중 오류가 발생했습니다.`);
  }
  finally {
    $overlay.visible = false;
  }
}
</script>

<template>
  <form @submit.prevent="submit()">
    <section class="space-y-4 p-4 md:p-8">
      <h1 class="text-lg font-bold md:text-xl">
        나에게 맞는<br>
        시술이 궁금하다면<br>
        바로 예약해 주세요.
      </h1>
      <p class="text-xs text-gray-400 md:text-sm">
        상담을 통해 가장 효과 좋고 적합한 시술을 추천해 드립니다.
      </p>
    </section>

    <section class="space-y-2 px-4 md:space-y-4 md:px-8">
      <div class="flex gap-4">
        <fieldset class="flex flex-1 flex-col gap-2">
          <input
            v-model="nickname"
            type="input"
            class="w-full rounded border px-4 py-2 text-xs md:p-4 md:text-sm"
            :class="{
              'ring-1 ring-red-500': hasErrorNickname,
            }"
            placeholder="이름"
            @input="hasErrorNickname = false"
          >
          <p
            v-if="hasErrorNickname"
            class="text-sm text-red-500"
          >
            이름을 입력해주세요.
          </p>
        </fieldset>
        <fieldset class="flex flex-1 flex-col gap-2">
          <input
            v-model="contact"
            type="input"
            class="w-full rounded border px-4 py-2 text-xs md:p-4 md:text-sm"
            :class="{
              'ring-1 ring-red-500': hasErrorContact,
            }"
            placeholder="연락처"
            @input="hasErrorContact = false"
          >
          <p
            v-if="hasErrorContact"
            class="p-1 text-sm text-red-500"
          >
            연락처를 입력해주세요.
          </p>
        </fieldset>
      </div>
      <fieldset class="flex flex-col gap-2">
        <div class="relative flex">
          <select
            v-model="selectedCategory"
            class="flex-1 appearance-none rounded border px-4 py-2 text-xs focus:outline-none md:p-4 md:text-sm"
            :class="{
              'ring-1 ring-red-500': hasErrorSelectedCategory,
            }"
            placeholder="상담 부위"
            @change="hasErrorSelectedCategory = false"
          >
            <option value="">
              상담 부위를 선택해주세요.
            </option>
            <option
              v-for="(item, index) of items"
              :key="index"
              :value="item.value"
            >
              {{ item.label }}
            </option>
          </select>
          <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center pe-4">
            <NuxtIcon
              name="mdi:chevron-down"
              size="24"
            />
          </div>
        </div>
        <p
          v-if="hasErrorSelectedCategory"
          class="p-1 text-sm text-red-500"
        >
          상담 부위를 선택해주세요.
        </p>
      </fieldset>
    </section>

    <section class="mt-4 px-4 py-4 md:px-8">
      <h2
        class="mb-4 text-sm font-medium md:text-base"
        :class="{
          'text-red-500': hasErrorSelectedDate,
        }"
      >
        날짜를 선택해주세요.
      </h2>
      <ClientOnly>
        <AppointmentCalendar
          v-model="selectedDate"
          @change="() => {
            hasErrorSelectedDate = false;
            selectedTime = ``;
          }"
        />
      </ClientOnly>
    </section>

    <section class="px-4 py-4 md:px-8">
      <h2
        class="mb-4 text-sm font-medium md:text-base"
        :class="{
          'text-red-500': hasErrorSelectedTime,
        }"
      >
        시간을 선택해주세요.
      </h2>
      <ClientOnly>
        <AppointmentAvailableTimes
          v-model="selectedTime"
          :selected-date="selectedDate"
          @change="() => hasErrorSelectedTime = false"
        />
      </ClientOnly>
    </section>

    <section class="mt-4 px-4 py-4 md:px-8">
      <h2 class="mb-4 text-sm font-medium md:text-base">
        아래 내용에 동의해주세요.
      </h2>
      <fieldset class="flex justify-between py-2">
        <label class="flex items-center gap-2 text-sm md:text-base">
          <input
            v-model="isAgreedPrivacy"
            type="checkbox"
            class="h-4 w-4 accent-cyan-800 md:h-5 md:w-5"
          >
          (필수) 개인정보 수집 이용 동의
        </label>
        <NuxtLink
          to="/policy/privacy"
          target="_blank"
          class="text-xs hover:underline md:text-sm"
        >
          상세보기
        </NuxtLink>
      </fieldset>
      <fieldset class="py-2">
        <label class="flex items-center gap-2 text-sm md:text-base">
          <input
            v-model="isAgeOver14"
            type="checkbox"
            class="h-4 w-4 accent-cyan-800 md:h-5 md:w-5"
          >
          (필수) 예약자가 만 14세 이상입니다.
        </label>
        <p class="mt-2 ps-6 text-[10px] font-medium text-cyan-800 md:ps-8 md:text-xs">
          만 14세 미만 고객은 카톡플친이나 전화로 문의해주세요.
        </p>
      </fieldset>
    </section>

    <section class="grid px-4 py-8 md:px-8">
      <button
        type="submit"
        class="rounded-full bg-cyan-100 py-3 text-center text-sm font-medium hover:bg-cyan-200 md:text-base"
        :class="{
          'pointer-events-none grayscale': !isEssentialAgreed,
        }"
      >
        시술 예약하기
      </button>
    </section>
  </form>
</template>
