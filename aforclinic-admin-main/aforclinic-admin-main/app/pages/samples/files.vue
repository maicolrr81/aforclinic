<script setup lang="ts">
const { data, open } = useFileUpload();
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection>
        <QToolbar>
          <QToolbarTitle>
            파일 업로드
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>
      <QCardSection>
        <QBtn
          color="primary"
          @click="open()"
        >
          업로드
        </QBtn>
      </QCardSection>
      <QCardSection class="grid grid-cols-4 gap-2">
        <QImg
          v-for="(metadata, index) of data"
          :key="index"
          :src="metadata.src"
        />
        {{ data[0]?.id }}
      </QCardSection>
    </QCard>
  </QPage>
</template>
