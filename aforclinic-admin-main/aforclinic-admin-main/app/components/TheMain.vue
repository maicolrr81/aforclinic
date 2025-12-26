<template>
  <QPageContainer class="bg-[#f4f5fa]">
    <slot />
    <QPageScroller
      position="bottom-right"
      :offset="[18, 18]"
    >
      <QBtn
        fab
        icon="keyboard_arrow_up"
        color="secondary"
      />
    </QPageScroller>
  </QPageContainer>
</template>
