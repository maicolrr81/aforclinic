<script setup lang="ts">
const html = ref(`<p style="text-align: left">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla posuere sodales lorem, pellentesque placerat nulla molestie et. Ut viverra, tellus sed auctor maximus, est nisi dictum diam, eget condimentum ipsum turpis id odio. Fusce viverra ex ante, id dignissim sem dictum in. Sed vitae eros diam. Nunc quam odio, lobortis id auctor nec, ullamcorper vitae enim. Vestibulum placerat sodales urna, vitae tempus dolor consectetur eu. Nam interdum in dolor eget eleifend. Duis condimentum lacus pellentesque, vulputate enim ut, cursus urna. Donec mollis tempor odio facilisis dictum. Nam eu ex eros. Phasellus vitae tincidunt felis. Mauris vel augue id tortor dapibus interdum. Duis iaculis lacus sit amet tristique imperdiet. Cras rutrum bibendum metus, et tincidunt turpis semper in.</p><p style="text-align: center"><img style="width=300px;" src="https://picsum.photos/200/300"></p>`);
</script>

<template>
  <QPage class="container mx-auto">
    <ClientOnly>
      <TiptapEditor v-model="html" />
      <hr class="my-4">
      <QCard
        class="prose max-w-none p-0"
        flat
      >
        <div
          class="tiptap"
          v-html="html"
        />
      </QCard>
    </ClientOnly>
  </QPage>
</template>
