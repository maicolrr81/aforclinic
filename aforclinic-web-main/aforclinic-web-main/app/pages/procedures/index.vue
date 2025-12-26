<script setup lang="ts">
definePageMeta({ layout: `procedures` });

const { $api } = useNuxtApp();
const route = useRoute();

const cart = useCartStore();

const text = computed(() => route.query.text || ``);
const params = computed(() => ({
  text: text.value,
}));

// 상품
const { data } = await useAsyncData<ApiPagedResponse<Product>>(() => $api(`/public/products`, {
  method: `get`,
  params: params.value,
}), {
  watch: [() => params.value],
});

const items = computed(() => data.value?.data.content ?? []);
</script>

<template>
  <section class="flex flex-col gap-6 p-4 md:px-8 md:pb-8">
    <ProductCard
      v-for="item of items"
      :key="item.id"
      v-model="cart.items"
      :product-id="item.id"
      :product-name="item.name"
      :description="item.description"
      :adjusted-price="item.adjustedPrice"
      :discounted-price="item.discountedPrice"
      :event="!!item.event"
      :until="item.event?.endDate"
    />
  </section>
</template>
