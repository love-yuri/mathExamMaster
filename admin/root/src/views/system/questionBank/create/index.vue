<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 19:57:12
 * @LastEditTime: 2024-10-07 14:44:55
 * @Description: 创建题目
-->
<template>
  <div class="p-2">
    <div class="mb-2 flex items-center">
      <Button
        v-for="(item, index) in questionComponents"
        :key="index"
        :label="item.title"
        :severity="qaType === index ? 'success' : 'secondary'"
        class="mr-2"
        icon="pi pi-check"
        @click="qaType = index"
      />
    </div>
    <component :is="questionComponents[qaType]!.component" />
  </div>
</template>
<script setup lang="ts">
import { QuestionTypeEnum } from '#/api/questionBankApi';
import SingleChoise from './components/singleChoice.vue';
import { Button } from '#/components';
import { type Component, ref } from 'vue';

type QuestionComponent = {
  component: Component;
  title: string;
  type: string;
};

const qaType = ref(0);
const questionComponents: QuestionComponent[] = [
  {
    component: SingleChoise,
    title: '单选题',
    type: QuestionTypeEnum.SINGLE_CHOICE,
  },
];
</script>
