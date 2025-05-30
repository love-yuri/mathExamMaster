<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-26 19:36:07
 * @LastEditTime: 2025-03-03 16:42:54
 * @Description: 
-->
<template>
  <div>
    <!-- <PreviewEditor :content="detail.content" /> -->
    <Card class="">
      <template #content>
        <div class="flex flex-row items-center justify-between">
          <div class="flex">
            <Tag value="多选" />
            <Button
              v-for="(_, index) in questionAnswer.options"
              :key="index"
              :label="`${String.fromCharCode(65 + index)}`"
              :severity="severity(index)"
              class="mx-1"
            />
          </div>
          <div class="flex items-center">
            <div class="flex items-center">
              用户得分:
              <span class="ml-1 text-[24px] font-bold text-blue-500">{{
                detail.score
              }}</span>
            </div>
            <SplitButton
              :model="[
                {
                  label: '预览题目',
                  icon: 'pi pi-eye',
                  command: () => show(detail.questionId),
                },
              ]"
              class="ml-2 flex-shrink-0"
              icon="pi pi-ellipsis-v"
              label="评分"
              raised
              severity="info"
              @click="() => quickScoreRef?.open(detail)"
            />
          </div>
        </div>
      </template>
    </Card>
    <Preview ref="previewRef" />
    <QuickScore ref="quickScoreRef" @select="setScore" />
  </div>
</template>
<script setup lang="ts">
import type { MultipleChoiceAw, UserScoreDetail } from '@yuri/types';

import { computed, unref, useTemplateRef } from 'vue';

import { areArraysEqual, questionBankApi } from '@yuri/common';
import { Button, Card, SplitButton, Tag } from '@yuri/components';

import Preview from '#/views/questionManager/questionBank/components/preview.vue';

import QuickScore from './quickScore.vue';

const emits = defineEmits(['setScore']);
const detail = defineModel<UserScoreDetail>('detail', { required: true });

const quickScoreRef = useTemplateRef('quickScoreRef');
const previewRef = useTemplateRef('previewRef');
const questionAnswer = computed(
  () => detail.value.questionAnswer as MultipleChoiceAw,
);
const isTrueAnswer = computed(() => {
  return areArraysEqual(userAnswer.value.answer, questionAnswer.value.answer);
});

function severity(index: number) {
  if (isTrueAnswer.value) {
    if (questionAnswer.value.answer.includes(index)) {
      return 'success';
    }
    return 'secondary';
  } else {
    if (questionAnswer.value.answer.includes(index)) {
      return userAnswer.value.answer.length > 0 ? 'success' : 'danger';
    }
    return userAnswer.value.answer.includes(index) ? 'danger' : 'secondary';
  }
}
const userAnswer = computed(
  () => detail.value.userAnswer.questionAnswer as unknown as MultipleChoiceAw,
);

function show(id: string) {
  questionBankApi.get(id).then((res) => {
    unref(previewRef)?.open(res);
  });
}

function setScore(score: number) {
  detail.value.score = score;
  detail.value.hasSetScore = true;
  emits('setScore', score);
}
</script>
