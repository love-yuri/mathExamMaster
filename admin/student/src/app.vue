<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-08 15:42:02
 * @LastEditTime: 2025-02-08 16:47:45
 * @Description: app
-->

<template>
  <Toast />
  <RouterView />
</template>

<script lang="ts" setup>
import { message } from '@yuri/common';
import Toast from 'primevue/toast';
import { useToast } from 'primevue/usetoast';
import { onMounted } from 'vue';

defineOptions({ name: 'App' });

/**
 * 初始化Toast
 * 将 toast实例注册进pinia中
 */
function init(): void {
  message.initToast(useToast());
}

onMounted(init);
</script>
