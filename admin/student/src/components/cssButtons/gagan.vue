<template>
  <button
    :class="`button-86 ${clicked ? 'button-86-clicked' : ''}`"
    role="button"
  >
    <slot></slot>
  </button>
</template>
<script setup lang="ts">
defineProps<{
  clicked: boolean;
}>();
</script>
<style lang="scss" scoped>
.button-86 {
  all: unset;
  position: relative;
  z-index: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 30px;
  padding: 10px 20px;
  font-size: 16px;
  color: #f0f0f0;
  white-space: nowrap;
  touch-action: manipulation;
  cursor: pointer;
  user-select: none;
  user-select: none;
  background: transparent;
  border: none;

  &::before,
  &::after {
    position: absolute;
    right: 0;
    bottom: 0;
    z-index: -99999;
    content: '';
    transition: all 0.4s;
  }

  &::before {
    width: 100%;
    height: 100%;
    background: #dcdcdc; /* 灰白色调背景 */
    border-radius: 10px;
    transform: translate(0%, 0%);
  }

  &::after {
    width: 35px;
    height: 35px;
    background: #74717115;
    backdrop-filter: blur(5px);
    backdrop-filter: blur(5px);
    border-radius: 50px;
    transform: translate(10px, 10px);
  }

  &-clicked::before {
    background: #f9c;
  }

  &:hover::before,
  &-clicked::before {
    width: 110%;
    height: 110%;
    transform: translate(5%, 20%);
  }

  &:hover::after,
  &-clicked::after {
    width: 100%;
    height: 100%;
    border-radius: 10px;
    transform: translate(0, 0);
  }

  &:active::after {
    transition: 0s;
    transform: translate(0, 5%);
  }

  /* 如果需要在选中时有其他交互样式，可以添加更多的样式 */
  &-clicked:hover::before {
    width: 115%;
    height: 115%;
    transform: translate(5%, 20%);
  }

  &-clicked:active::after {
    transform: translate(0, 5%);
  }
}
</style>
