<!-- eslint-disable no-console -->
<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:36:21
 * @LastEditTime: 2024-10-06 22:23:22
 * @Description: 
-->
<template>
  <div class="p-2">
    <WangEditor v-model:content="content" />
    {{ content }}
  </div>
</template>
<script setup lang="ts">
import WangEditor from '#/components/wangEditor/index.vue';
import { ref } from 'vue';

const content = ref('');
</script>
