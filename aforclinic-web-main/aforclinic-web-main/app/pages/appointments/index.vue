<script setup lang="ts">
import type { CreateProcedureAppointment } from '#shared/types/Appointment';

const { $api, $dialog, $modal, $overlay } = useNuxtApp();

const scroll = useWindowScroll();

const auth = useAuthStore();
const cart = useCartStore();
const dayjs = useDayjs();

const params = computed(() => ({
  productIds: cart.items,
}));

const selectedIds = ref<string[]>([]);

const { data } = await useAsyncData<ApiPagedResponse<Product>>(() => {
  selectedIds.value = [];

  if (params.value.productIds.length === 0) {
    return Promise.resolve({
      data: {
        content: [] as Product[],
      },
    } satisfies ApiPagedResponse<Product>);
  }
  return $api<ApiPagedResponse<Product>>(`/public/products`, {
    method: `get`,
    params: params.value,
  });
}, {
  server: false,
  watch: [() => params.value],
});

const items = computed(() => data.value?.data.content ?? []);

const selectedItems = computed(() => items.value.filter(item => selectedIds.value.includes(item.id)));
const selectedItemsCount = computed(() => selectedItems.value.length);
const selectedItemsPrice = computed(() => selectedItems.value.reduce((sum, item) => sum + (item.discountedPrice), 0));
const selectedItemsString = computed(() => selectedItems.value.map(item => `${item.name}:${item.discountedPrice}`));

const selectedAll = computed({
  get: () => {
    if (items.value.length === 0) {
      return false;
    }
    return selectedItems.value.length === items.value.length;
  },
  set: (newValue) => {
    if (newValue) {
      selectedIds.value = items.value.map(item => item.id);
    }
    else {
      selectedIds.value = [];
    }
  },
});

const selectedDate = ref(new Date());
const selectedDateString = computed(() => dayjs(selectedDate.value).format(`YYYY-MM-DD`));
const hasErrorSelectedDate = ref(false);

const selectedTime = ref(``);
const hasErrorSelectedTime = ref(false);

const isAgreedPrivacy = ref(false);
const isAgeOver14 = ref(false);
const isAgreedMarketing = ref(false);
const isEssentialAgreed = computed(() => isAgreedPrivacy.value && isAgeOver14.value);

function remove() {
  cart.items = cart.items.filter(item => !selectedIds.value.includes(item));
}

function clear() {
  selectedDate.value = new Date();
  selectedTime.value = ``;
  isAgreedPrivacy.value = false;
  isAgeOver14.value = false;
  // 선택한 시술 장바구니에서 제거
  remove();
}

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
  if (!auth.isAuthenticated) {
    const confirm = await $dialog.confirm(`로그인이 필요한 서비스입니다.`);
    if (confirm) {
      $modal.state.isLoginModalOpen = true;
    }
    return;
  }

  if (!validate()) { return; }

  if (selectedItemsCount.value === 0) {
    await $dialog.alert(`예약할 시술을 선택하세요.`);
    return;
  }

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
    await $api(`/public/appointments`, {
      method: `post`,
      body: {
        type: `PROCEDURE`,
        content: `${selectedItemsString.value}`,
        date: `${selectedDateString.value} ${selectedTime.value}`,
        privacyAgreed: isAgreedPrivacy.value,
        ageConfirmed: isAgeOver14.value,
        marketingAgreed: isAgreedMarketing.value,
      } satisfies CreateProcedureAppointment,
    });
    await $dialog.alert(`${dayjs(selectedDate.value).format('YYYY년 M월 D일')} ${selectedTime.value}로 예약 요청되었습니다.
병원에서 연락드릴 예정입니다. 감사합니다 :)`);
    clear();
    scroll.y.value = 0;
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
    <section class="flex items-center justify-between border-b px-4 py-3 md:px-8">
      <label class="flex items-center gap-2 text-sm font-medium md:text-base">
        <input
          v-model="selectedAll"
          type="checkbox"
          class="h-4 w-4 accent-cyan-800 md:h-5 md:w-5"
          :disabled="items.length === 0"
        >
        전체 선택 ({{ selectedIds.length }}/{{ items.length }})
      </label>
      <button
        type="button"
        class="text-sm font-medium md:text-base"
        @click="remove()"
      >
        선택 삭제
      </button>
    </section>

    <section class="px-4 md:px-8">
      <div class="flex flex-col gap-6 py-4 md:py-8">
        <ProductCard
          v-for="item of items"
          :key="item.id"
          v-model="selectedIds"
          :product-id="item.id"
          :product-name="item.name"
          :description="item.description"
          :adjusted-price="item.adjustedPrice"
          :discounted-price="item.discountedPrice"
          :event="!!item.event"
          :until="item.event?.endDate"
        />
      </div>
      <p class="text-end text-sm text-cyan-800">
        * 모든 상품은 부가세 포함입니다.
      </p>
    </section>

    <section class="grid px-4 py-4 md:px-8">
      <NuxtLink
        to="/procedures"
        class="rounded-full bg-cyan-100 py-3 text-center text-sm font-medium hover:bg-cyan-200 md:text-base"
      >
        다른 시술 상품 추가하기
      </NuxtLink>
    </section>

    <section class="flex flex-col gap-2 border-b px-4 py-4 md:px-8">
      <h2 class="mb-4 text-base font-medium md:text-lg">
        시술 합계
      </h2>
      <div class="flex items-center justify-between text-sm font-medium md:text-base">
        <span>선택한 시술 개수</span>
        <span>{{ selectedItemsCount }} 개</span>
      </div>
      <div class="flex items-center justify-between text-sm font-medium md:text-base">
        <span>총 결제 예상 금액</span>
        <span>{{ currency(selectedItemsPrice) }}원</span>
      </div>
      <p class="text-end text-xs text-cyan-800 md:text-sm">
        * 결제는 내원 후 진행해 주세요.
      </p>
    </section>

    <section class="px-4 py-4 md:px-8">
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

    <section class="px-4 py-4 md:px-8">
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
