<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-10 15:47:59
 * @LastEditTime: 2024-08-11 20:18:25
 * @Description: App.vue
-->
<template>
  <Toast />
  <RouterView />
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import Toast from 'primevue/toast';
import { useToastStore } from '@/stores/toast';
import { useToast } from 'primevue/usetoast';

/**
 * 初始化Toast
 * 将 toast实例注册进pinia中
 */
function initToast(): void {
  const toastStore = useToastStore();
  const toast = useToast();
  toastStore.init(toast);
}

/**
 * 初始函数，用于初始化全局的组件
 */
function init(): void {
  initToast();
}

onMounted(init);
</script>
