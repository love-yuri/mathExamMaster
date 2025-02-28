<!-- eslint-disable no-console -->
<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:36:21
 * @LastEditTime: 2025-02-08 16:20:29
 * @Description: 
-->
<template>
  <div class="p-2">
    <WangEditor v-model:content="content" />
    {{ content }}
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue';

import { WangEditor } from '@yuri/components';

// const content = ref(`<p><yuri data-w-e-type="yuri" dd="just do it">yuri is yes</yuri></p>`);
const content = ref('');
</script>
