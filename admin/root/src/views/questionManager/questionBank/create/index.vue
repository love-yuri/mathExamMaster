<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 19:57:12
 * @LastEditTime: 2025-02-27 14:44:58
 * @Description: 创建题目
-->
<template>
  <div class="p-2">
    <div class="mb-2 flex items-center">
      <SelectButton
        v-model="qaType"
        :options="questionComponents"
        data-key="type"
        option-label="title"
      />
    </div>
    <component :is="qaType!.component" />
  </div>
</template>
<script setup lang="ts">
import type { Component } from 'vue';

import { SelectButton } from '@yuri/components';
import { QuestionTypeEnum } from '@yuri/types';
import { markRaw, ref } from 'vue';

import GapFilling from './components/gapFilling.vue';
import Judge from './components/judge.vue';
import MultipleChoice from './components/multipleChoice.vue';
import SingleChoise from './components/singleChoice.vue';
import Subjective from './components/subjective.vue';

type QuestionComponent = {
  component: Component;
  title: string;
  type: string;
};

const questionComponents: QuestionComponent[] = [
  {
    component: markRaw(SingleChoise),
    title: '单选题',
    type: QuestionTypeEnum.SINGLE_CHOICE,
  },
  {
    component: markRaw(MultipleChoice),
    title: '多选题',
    type: QuestionTypeEnum.MULTIPLE_CHOICE,
  },
  {
    component: markRaw(Judge),
    title: '判断题',
    type: QuestionTypeEnum.JUDGE,
  },
  {
    component: markRaw(GapFilling),
    title: '填空题',
    type: QuestionTypeEnum.GAP_FILLING,
  },
  {
    component: markRaw(Subjective),
    title: '主观题',
    type: QuestionTypeEnum.SUBJECTIVE,
  },
];
const qaType = ref(questionComponents[0]);
</script>
