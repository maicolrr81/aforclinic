<script setup lang="ts">
const searchText = ref(``);

const isFormModalOpen = ref(false);

function submit() {
  console.log(`submit: `, searchText);
}
</script>

<template>
  <QPage class="container mx-auto">
    <QForm
      class="my-4 flex items-center justify-between gap-2"
      @submit="submit"
    >
      <div class="mx-auto max-w-[500px] grow">
        <QInput
          v-model="searchText"
          clearable
          dense
          outlined
        >
          <template #append>
            <QIcon
              name="search"
              class="cursor-pointer"
              @click="submit"
            />
          </template>
        </QInput>
      </div>
      <QBtn
        color="primary"
        icon="add"
        label="추가하기"
        @click="isFormModalOpen = true"
      />
    </QForm>
    <div class="grid grid-cols-1 gap-8 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-4">
      <QCard
        v-for="i in 30"
        :key="i"
        flat
        bordered
      >
        <QImg :src="`https://picsum.photos/300/200.webp?random=${i}`" />
        <QCardSection class="flex flex-col gap-y-1">
          <div>
            <a
              class="cursor-pointer text-xl font-bold hover:text-primary"
              @click="isFormModalOpen = true"
            >
              메인로고
            </a>
          </div>
          <div class="text-sm font-medium">
            수정일: 2025-01-20
          </div>
        </QCardSection>
      </QCard>
    </div>
    <LogoFormModal v-model="isFormModalOpen" />
  </QPage>
</template>
