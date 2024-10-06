<template>
  <div class="p-2">
    <div class="mb-2 flex items-center">
      <Button
        v-for="(item, index) in questionTypes"
        :key="index"
        :label="item"
        :severity="qaType === index ? 'success' : 'secondary'"
        class="mr-2"
        icon="pi pi-check"
        @click="qaType = index"
      />
    </div>
    <div class="flex flex-col">
      <div class="flex-shrink-0 text-2xl">请输入题目...</div>
      <WangEditor />
    </div>
  </div>
</template>
<script setup lang="ts">
import { Button, InputText, WangEditor } from '#/components';
import { ref } from 'vue';

const questionTypes = ['单选题', '多选题', '判断题', '填空题', '简答题'];

const qaType = ref(0);
</script>
