<script setup lang="ts">
import type { AppointmentAvailableTimesProps } from '#shared/types/ComponentProps';

const {
  selectedDate,
} = defineProps<AppointmentAvailableTimesProps>();

const emit = defineEmits<{
  change: [time: string];
}>();

const modelValue = defineModel<string>({ default: `` });

const dayjs = useDayjs();

const availableTimes = computed(() => {
  const now = dayjs();
  const currentTime = now.format('HH:mm');
  const date = dayjs(selectedDate);
  const day = date.day(); // 0: 일, 1: 월, ..., 6: 토
  const isToday = now.isSame(date, 'day');

  // 시작 시간
  const baseTimes = [
    '10:30',
    '11:00',
    '11:30',
    '12:00',
    '12:30',
    '14:00',
    '14:30',
    '15:00',
  ];

  const weekdayExtraTimesA = [
    '15:30',
    '16:00',
    '16:30',
    '17:00',
    '17:30',
    '18:00',
    '18:30',
  ];

  const weekdayExtraTimesB = [
    ...weekdayExtraTimesA,
    '19:30',
  ];

  let times = [...baseTimes];

  // 월, 금
  if (day === 1 || day === 5) {
    times = [...baseTimes, ...weekdayExtraTimesA];
  }
  // 화, 목
  else if (day === 2 || day === 4) {
    times = [...baseTimes, ...weekdayExtraTimesB];
  }
  // 토요일: baseTimes 중 15:00까지만
  else if (day === 6) {
    times = baseTimes.filter(time => time <= '15:00');
  }

  return isToday ? times.filter(time => time > currentTime) : times;
});

function onTimeClick(time: string) {
  modelValue.value = time;
  emit(`change`, time);
}
</script>

<template>
  <div class="grid grid-cols-2 gap-4 sm:grid-cols-4 md:grid-cols-6">
    <button
      v-for="(availableTime, index) of availableTimes"
      :key="index"
      type="button"
      class="truncate rounded-full bg-gray-100 px-3 py-2 text-sm font-medium hover:bg-gray-200"
      :class="{ '!bg-cyan-200 ': modelValue === availableTime }"
      @click="onTimeClick(availableTime)"
    >
      {{ availableTime }}
    </button>
  </div>
</template>
