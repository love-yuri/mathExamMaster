<!-- eslint-disable no-console -->
<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:36:21
 * @LastEditTime: 2024-10-22 22:08:49
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

// const content = ref(`<p><yuri data-w-e-type="yuri" dd="just do it">yuri is yes</yuri></p>`);
const content = ref(
  `<p><yuri-math math="c = \\pm\\sqrt{a^2 + b^2}"></yuri-math>we<yuri-math math="c = \\pm\\sqrt{a^2 + b^2}"></yuri-math><yuri-math math="c = \\pm\\sqrt{a^2 + b^2}"></yuri-math></p>`,
);
</script>
