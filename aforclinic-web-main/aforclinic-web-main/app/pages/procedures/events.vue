<script setup lang="ts">
import type { Event } from '#shared/types/Event';

definePageMeta({ layout: `procedures` });

const { $api } = useNuxtApp();
const route = useRoute();

const cart = useCartStore();

// 이벤트 목록
const { data: events } = await useAsyncData<Event[]>(async () => {
  const data = await $api<ApiPagedResponse<Event>>(`/public/events`);
  return data.data.content ?? [];
});

const eventId = ref<string>(events.value?.at(0)?.id ?? ``);

const text = computed(() => route.query.text || ``);
const params = computed(() => ({
  eventId: eventId.value,
  text: text.value,
}));

const selectedEvent = computed(() => events.value?.find(event => event.id === eventId.value));

// 상품
const { data: items } = await useAsyncData<EventProduct[]>(async () => {
  const data = await $api<ApiResponse<EventProduct[]>>(`/public/events/products`, {
    method: `get`,
    params: params.value,
  });
  return data.data ?? [];
}, {
  watch: [() => params.value],
});
</script>

<template>
  <section class="px-4 pb-4 md:px-8">
    <div class="flex gap-x-4 overflow-x-auto whitespace-nowrap text-sm text-gray-500">
      <template
        v-for="(event, index) of events"
        :key="index"
      >
        <template v-if="index > 0">
          /
        </template>
        <button
          class="hover:font-medium hover:text-gray-800"
          :class="{
            'font-medium text-gray-800': event.id === eventId,
          }"
          @click="eventId = event.id"
        >
          {{ event.title }}
        </button>
      </template>
    </div>
  </section>

  <section class="mt-4 flex flex-col gap-6 px-4 pb-4 md:px-8 md:pb-8">
    <ProductCard
      v-for="(item, index) of items"
      :key="index"
      v-model="cart.items"
      :product-id="item.productId"
      :product-name="item.productName"
      :description="item.productDescription"
      :adjusted-price="item.adjustedPrice"
      :discounted-price="item.discountedPrice"
      :event="true"
      :until="selectedEvent?.endDate"
    />
  </section>
</template>
