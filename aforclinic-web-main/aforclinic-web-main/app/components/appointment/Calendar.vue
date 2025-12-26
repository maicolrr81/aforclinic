<script setup lang="ts">
const emit = defineEmits<{
  change: [date: Date];
}>();

const modelValue = defineModel<Date>({ default: undefined });

const dayjs = useDayjs();

const attributes = computed<any>(() => [
  {
    key: `today`,
    highlight: {
      color: `gray`,
      fillMode: `outline`,
    },
    dates: [new Date()],
  },
  {
    key: `selected`,
    highlight: `blue`,
    dates: [modelValue.value],
  },
]);

const minDate = computed(() => dayjs().format(`YYYY-MM-DD`));
const maxDate = computed(() => dayjs().add(2, `month`).endOf(`month`).format(`YYYY-MM-DD`));
const disabledDates = computed(() => {
  const closed: Date[] = [];

  const min = dayjs(minDate.value, `YYYY-MM-DD`);
  const max = dayjs(maxDate.value, `YYYY-MM-DD`);

  let cursor = min.startOf('day');
  while (cursor.isBefore(max) || cursor.isSame(max, 'day')) {
    if (cursor.day() === 0 || cursor.day() === 3) { // Sunday = 0, Wednesday = 3
      closed.push(cursor.toDate());
    }
    cursor = cursor.add(1, 'day');
  }

  return closed;
});

const masks = computed(() => ({ title: `YYYY년 MM월` }));

function onDayClick(e: {
  date: Date;
  isDisabled: boolean;
}) {
  if (e.isDisabled) { return; }
  modelValue.value = e.date;
  emit(`change`, e.date);
}
</script>

<template>
  <VCalendar
    :attributes
    :disabled-dates
    :masks
    :min-date
    :max-date
    expanded
    borderless
    timezone="Asia/Seoul"
    @dayclick="onDayClick"
  />
</template>
