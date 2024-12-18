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
  width: 100px;
  height: 30px;
  font-size: 16px;
  background: transparent;
  border: none;
  position: relative;
  color: #f0f0f0;
  cursor: pointer;
  z-index: 1;
  padding: 10px 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  white-space: nowrap;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;

  &::before,
  &::after {
    content: '';
    position: absolute;
    bottom: 0;
    right: 0;
    z-index: -99999;
    transition: all 0.4s;
  }

  &::before {
    transform: translate(0%, 0%);
    width: 100%;
    height: 100%;
    background: #dcdcdc; /* 灰白色调背景 */
    border-radius: 10px;
  }

  &::after {
    transform: translate(10px, 10px);
    width: 35px;
    height: 35px;
    background: #74717115;
    backdrop-filter: blur(5px);
    -webkit-backdrop-filter: blur(5px);
    border-radius: 50px;
  }

  &-clicked::before {
    background: #ff99cc;
  }

  &:hover::before,
  &-clicked::before {
    transform: translate(5%, 20%);
    width: 110%;
    height: 110%;
  }

  &:hover::after,
  &-clicked::after {
    border-radius: 10px;
    transform: translate(0, 0);
    width: 100%;
    height: 100%;
  }

  &:active::after {
    transition: 0s;
    transform: translate(0, 5%);
  }

  /* 如果需要在选中时有其他交互样式，可以添加更多的样式 */
  &-clicked:hover::before {
    transform: translate(5%, 20%);
    width: 115%;
    height: 115%;
  }

  &-clicked:active::after {
    transform: translate(0, 5%);
  }
}
</style>
