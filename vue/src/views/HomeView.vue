<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-10 16:02:45
 * @LastEditTime: 2024-08-11 00:40:41
 * @Description: 主页面
-->
<template>
  <h1 class="text-[40px]">{{ baseUrl }}</h1>
  <Button>sfsdfds</Button>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { Button } from '@/components';

const baseUrl = ref(import.meta.env.VITE_BASE_URL);
</script>
