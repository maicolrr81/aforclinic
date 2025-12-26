<script setup lang="ts">
import type { CancelAppointment } from '#shared/types/Appointment';

definePageMeta({
  layout: `mypage`,
  middleware: [`requires-auth`],
});

const { $api, $dialog, $overlay } = useNuxtApp();

const dayjs = useDayjs();

const [DefineAppointmentDisclosure, AppointmentDisclosure] = createReusableTemplate();

const { data, refresh } = await useAsyncData<ApiPagedResponse<Appointment>>(() => $api(`/public/appointments`));
const appointments = computed(() => (data.value?.data.content ?? []).map((item) => {
  let items: { name: string; price: number }[] = [];
  if (item.content) {
    const regex = /([^:,]+):(\d+)/g;
    const matches = [...item.content.matchAll(regex)];
    items = matches.map(([_, name, price]) => ({
      name: name?.trim() ?? ``,
      price: Number(price),
    }));
  }

  const totalCount = items.length;
  const totalPrice = items.reduce((sum, item) => sum + item.price, 0);

  return {
    ...item,
    items,
    totalCount,
    totalPrice,
  };
}));

function toTypeName(type: AppointmentType) {
  switch (type) {
    case `EVENT`: return `이벤트`;
    case `QUICK`: return `바로 예약`;
    case `PROCEDURE`: return `시술 예약`;
    default: return ``;
  }
}

function formatDateTime(datetime?: string): string {
  if (!datetime) { return ``; };
  const date = dayjs(datetime, `YYYY-MM-DD HH:mm`);
  const dayOfWeek = date.format(`ddd`); // 요일
  const hour = date.format(`A h:mm`); // 오전/오후 시간 (예: 오전 11:00)
  return `| ${date.format(`YYYY년 M월 D일`)}(${dayOfWeek}) ${hour}`;
}

async function cancel(id: string) {
  const confirm = await $dialog.confirm(`예약을 취소하시겠습니까?`, {
    title: `예약 취소 확인`,
  });

  if (!confirm) { return; }

  $overlay.visible = true;
  try {
    await $api(`/public/appointments/${id}`, {
      method: `patch`,
      body: {
        status: `CANCEL`,
      } satisfies CancelAppointment,
    });
    await $dialog.alert(`예약이 취소되었습니다.`);
    refresh();
  }
  catch {
    await $dialog.alert(`예약 취소에 실패했습니다.`);
  }
  finally {
    $overlay.visible = false;
  }
}
</script>

<template>
  <section class="p-4 !pb-0 md:p-8">
    <h2 class="text-lg font-bold md:text-xl">
      예약 내역 확인
    </h2>
    <p class="mt-1 text-[10px] text-cyan-800 md:text-xs">
      * 홈페이지에서 예약, 변경 내용만 확인이 가능합니다.<br>
      상담을 통해 예약을 변경한 경우에는 변경 전 예약 일정만 확인이 가능합니다.
    </p>
  </section>

  <DefineAppointmentDisclosure v-slot="{ appointment }">
    <HeadlessDisclosure
      v-slot="{ open }"
      as="div"
    >
      <div class="relative z-10 flex items-center gap-2 rounded-lg border bg-white p-4">
        <div
          class="flex-1 text-xs md:text-sm"
          :class="{
            'text-gray-400': appointment.past,
            'text-gray-400 line-through': appointment.cancel,
          }"
        >
          {{ toTypeName(appointment.type) }} {{ formatDateTime(appointment.date) }}
        </div>
        <template v-if="appointment.editable">
          <NuxtLink
            class="min-w-12 bg-cyan-100 px-2 py-1 text-[10px] text-cyan-600 hover:bg-cyan-200 md:text-xs"
            :to="`/mypage/appointments/${appointment.id}/reschedule`"
          >
            날짜변경
          </NuxtLink>
          <button
            class="min-w-12 bg-cyan-100 px-2 py-1 text-[10px] text-cyan-600 hover:bg-cyan-200 md:text-xs"
            @click="cancel(appointment.id)"
          >
            취소
          </button>
        </template>
        <HeadlessDisclosureButton class="flex items-center">
          <NuxtIcon
            class="text-gray-400 hover:text-gray-600"
            :name="open ? `mdi:chevron-up` : `mdi:chevron-down`"
            size="24"
          />
        </HeadlessDisclosureButton>
      </div>
      <HeadlessDisclosurePanel class="-mt-2 flex flex-col gap-2 bg-gray-100 p-4 pt-6 text-[10px] text-gray-600 md:text-xs">
        <div
          v-for="(item, index) of appointment.items"
          :key="index"
          class="flex items-center justify-between"
        >
          <div>{{ item.name }}</div>
          <div>{{ currency(item.price) }}원</div>
        </div>
        <div class="flex items-center justify-between text-xs md:text-sm">
          <div class="flex-1">
            총 {{ appointment.totalCount }} 건
          </div>
          <div>합계: {{ currency(appointment.totalPrice) }}원</div>
        </div>
      </HeadlessDisclosurePanel>
    </HeadlessDisclosure>
  </DefineAppointmentDisclosure>

  <section class="space-y-2 p-4 md:p-8">
    <AppointmentDisclosure
      v-for="(appointment, index) of appointments"
      :key="index"
      :appointment
    />
  </section>
</template>
