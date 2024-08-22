<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-10 16:02:45
 * @LastEditTime: 2024-08-22 23:49:11
 * @Description: 主页面
-->
<template>
  <h1 class="text-[40px]">{{ baseUrl }}</h1>
  <Button @click="showv">sfsdfds</Button>
  <Button @click="parse">sfsdfds</Button>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { Button } from '@/components';
import { testApi } from '@/api/testApi';
// import message from '@/common/utils/message';

const baseUrl = ref(import.meta.env.VITE_BASE_URL);

async function showv() {}

async function parse() {
  await testApi.hello();
}
</script>
