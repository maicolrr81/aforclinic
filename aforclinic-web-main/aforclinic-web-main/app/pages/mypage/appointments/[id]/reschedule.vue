<script setup lang="ts">
definePageMeta({
  middleware: [`requires-auth`],
});

const { $api, $dialog, $overlay } = useNuxtApp();

const dayjs = useDayjs();
const route = useRoute();

const { data, error } = await useAsyncData<Partial<Appointment>>(async () => {
  const data = await $api<ApiResponse<Appointment>>(`/public/appointments/${route.params.id}`);
  return data.data;
}, {
  default: () => ({}),
});

// 오류가 발생
if (error.value) {
  showError({ statusCode: 500 });
}

const id = computed(() => data.value.id);
const items = computed(() => {
  let items: { name: string; price: number }[] = [];
  if (data.value?.content) {
    const regex = /([^:,]+):(\d+)/g;
    const matches = [...data.value?.content.matchAll(regex)];
    items = matches.map(([_, name, price]) => ({
      name: name?.trim() ?? ``,
      price: Number(price),
    }));
  }
  return items;
});
const totalCount = computed(() => items.value.length);
const totalPrice = computed(() => items.value.reduce((sum, item) => sum + item.price, 0));

const selectedDate = ref<Date>();
const selectedDateString = computed(() => dayjs(selectedDate.value).format(`YYYY-MM-DD`));
const hasErrorSelectedDate = ref(false);

const selectedTime = ref(``);
const hasErrorSelectedTime = ref(false);

watchEffect(() => {
  const datetime = dayjs(data.value.date, `YYYY-MM-DD HH:mm`);
  selectedDate.value = datetime.toDate();
  selectedTime.value = datetime.format(`HH:mm`);
});

function validate() {
  let hasError = false;
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
    `예약일자: ${selectedDateString.value} ${selectedTime.value}
위 내용이 맞다면 "확인"을 눌러주세요.`,
    {
      title: `🎉 예약 내용을 확인해주세요.`,
    },
  );

  if (!confirm) { return; }

  $overlay.visible = true;

  try {
    await $api(`/public/appointments/${id.value}`, {
      method: `patch`,
      body: {
        date: `${selectedDateString.value} ${selectedTime.value}`,
      } satisfies RescheduleAppointment,
    });
    await $dialog.alert(`${dayjs(selectedDate.value).format('YYYY년 M월 D일')} ${selectedTime.value} 예약 되었습니다.`);
    navigateTo(`/mypage`);
  }
  catch {
    await $dialog.alert(`예약 일자 변경 중 오류가 발생했습니다.`);
  }
  finally {
    $overlay.visible = false;
  }
}
</script>

<template>
  <form @submit.prevent="submit()">
    <section class="px-4 py-4 md:px-8">
      <h2 class="mb-4 text-xl font-medium">
        시술 목록
      </h2>
      <div class="flex flex-col gap-6 py-4 md:py-8">
        <div
          v-for="(item, index) of items"
          :key="index"
          class="flex items-center justify-between"
        >
          <div>{{ item.name }}</div>
          <div>{{ currency(item.price) }}</div>
        </div>
      </div>
      <p class="text-end text-sm text-cyan-800">
        * 모든 상품은 부가세 포함입니다.
      </p>
    </section>

    <section class="flex flex-col gap-2 border-b px-4 py-4 md:px-8">
      <h2 class="mb-4 text-xl font-medium">
        시술 합계
      </h2>
      <div class="flex items-center justify-between font-medium">
        <span>선택한 시술 개수</span>
        <span>{{ totalCount }} 개</span>
      </div>
      <div class="flex items-center justify-between font-medium">
        <span>총 결제 예상 금액</span>
        <span>{{ currency(totalPrice) }}원</span>
      </div>
      <p class="text-end text-sm text-cyan-800">
        * 결제는 내원 후 진행해 주세요.
      </p>
    </section>

    <section class="px-4 py-4 md:px-8">
      <h2
        class="mb-4 font-medium"
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
        class="mb-4 font-medium"
        :class="{
          'text-red-500': hasErrorSelectedTime,
        }"
      >
        시간을 선택해주세요.
      </h2>
      <ClientOnly>
        <AppointmentAvailableTimes
          v-model="selectedTime"
          :selected-date="selectedDate!"
          @change="() => hasErrorSelectedTime = false"
        />
      </ClientOnly>
    </section>

    <section class="grid px-4 py-8 md:px-8">
      <button
        type="submit"
        class="rounded-full bg-cyan-100 py-3 text-center font-medium hover:bg-cyan-200"
      >
        시술 예약 변경하기
      </button>
    </section>
  </form>
</template>
