<script setup lang="ts">
import type { ProductCardProps } from '#shared/types/ComponentProps';

const {
  productId,
  productName,
  description = ``,
  adjustedPrice,
  discountedPrice = 0,
  event = false,
  until = ``,
} = defineProps<ProductCardProps>();

const modelValue = defineModel<string[]>();

const dayjs = useDayjs();

function remaing(until?: string) {
  if (!until) { return; }
  const date = dayjs(until, `YYYY-MM-DD`);
  const diff = date.diff(dayjs(), `day`);
  return diff;
}

function percent(adjustedPrice: number, discountedPrice: number = 0) {
  return Math.round((1 - discountedPrice / adjustedPrice) * 100);
}
</script>

<template>
  <div
    class="relative rounded-lg border p-4"
    :class="{
      'pt-6': event,
    }"
  >
    <UiBadge
      v-if="event"
      class="absolute -top-3 left-3"
    >
      EVENT
    </UiBadge>
    <label class="flex items-center gap-3 text-sm font-medium md:text-base">
      <input
        v-model="modelValue"
        type="checkbox"
        class="h-4 w-4 accent-cyan-800 md:h-5 md:w-5"
        :value="productId"
      >
      {{ productName }}
    </label>
    <div
      v-if="description"
      class="mt-2 flex items-start gap-2 ps-8"
    >
      <p class="flex-1 whitespace-break-spaces text-xs text-gray-400 transition-all md:text-sm">
        {{ description }}
      </p>
    </div>
    <div class="mt-2 flex flex-col items-end justify-between">
      <span
        v-if="until"
        class="text-xs text-cyan-800 md:text-sm"
      >
        ~ {{ until }} ({{ remaing(until) }}일 남음)
      </span>
      <div class="flex items-end gap-2">
        <span
          v-if="adjustedPrice > discountedPrice"
          class="text-base font-semibold text-cyan-800"
        >
          {{ percent(adjustedPrice, discountedPrice) }}%
        </span>
        <span
          v-if="adjustedPrice !== discountedPrice"
          class="text-sm text-gray-300 line-through"
        >
          {{ currency(adjustedPrice) }}
        </span>
        <span
          v-if="discountedPrice"
          class="text-lg font-semibold"
        >
          {{ currency(discountedPrice) }}
        </span>
      </div>
    </div>
  </div>
</template>
