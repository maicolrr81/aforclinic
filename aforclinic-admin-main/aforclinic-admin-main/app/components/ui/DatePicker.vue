<script setup lang="ts">
import type { QInputProps } from 'quasar';
import { DAYS, DAYS_SHORT, MONTHS, MONTHS_SHORT } from '~~/shared/constants/date.locale';

const {
  outlined = true,
  lazyRules = `ondemand`,
  rules = dateRules,
  ...props
} = defineProps<QInputProps>();

const modelValue = defineModel<string>();

const isOpen = ref(false);

const localValue = computed({
  get: () => modelValue.value,
  set: (newValue) => {
    modelValue.value = newValue;
    isOpen.value = false;
  },
});

const koreanLocale = {
  /* starting with Sunday */
  days: DAYS,
  daysShort: DAYS_SHORT,
  months: MONTHS,
  monthsShort: MONTHS_SHORT,
  firstDayOfWeek: 1, // 0-6, 0 - Sunday, 1 Monday, ...
  format24h: true,
  pluralDay: 'dias',
  // ✅ 헤더 날짜 형식 변경 (월, 일, 요일 순)
  headerTitle: (date: string | Date) => {
    return new Intl.DateTimeFormat('ko-KR', {
      month: 'short', // Mar
      day: '2-digit', // 14
      weekday: 'short', // Thu
    }).format(new Date(date));
  },
};
</script>

<template>
  <QInput
    v-bind="props"
    v-model="localValue"
    :outlined
    :lazy-rules
    :rules
    @click="isOpen = true"
  >
    <template #append>
      <QIcon
        name="event"
        class="cursor-pointer"
      >
        <QPopupProxy
          v-model="isOpen"
          cover
          transition-show="scale"
          transition-hide="scale"
        >
          <QDate
            v-model="localValue"
            color="blue"
            mask="YYYY-MM-DD"
            :locale="koreanLocale"
            today-btn
          />
        </QPopupProxy>
      </QIcon>
    </template>
  </QInput>
</template>

<style>
</style>
