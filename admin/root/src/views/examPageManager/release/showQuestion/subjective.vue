<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-23 19:12:39
 * @LastEditTime: 2025-03-03 16:27:25
 * @Description: 
-->
<template>
  <div>
    <Card>
      <template #content>
        <div class="flex justify-between">
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
          <div class="flex items-center">
            用户得分:
            <span class="ml-1 text-[24px] font-bold text-blue-500">{{
              detail.score
            }}</span>
          </div>
        </div>

        <div class="mt-2 h-[1px] bg-gray-600"></div>
        <div class="flex items-center">
          <WangEditor
            :hide-toolbar-config="true"
            :readonly="true"
            :content="userAnswer.answer"
            placeholder="用户答案为空..."
          />
        </div>
      </template>
    </Card>
    <Preview ref="previewRef" />
    <QuickScore ref="quickScoreRef" @select="setScore" />
  </div>
</template>
<script setup lang="ts">
import type { SubjectiveAw, UserScoreDetail } from '@yuri/types';

import { computed, unref, useTemplateRef } from 'vue';

import { questionBankApi } from '@yuri/common';
import { Card, SplitButton, WangEditor } from '@yuri/components';

import Preview from '#/views/questionManager/questionBank/components/preview.vue';

import QuickScore from './quickScore.vue';

const emits = defineEmits(['setScore']);

const detail = defineModel<UserScoreDetail>('detail', { required: true });

const quickScoreRef = useTemplateRef('quickScoreRef');
const previewRef = useTemplateRef('previewRef');

const userAnswer = computed(
  () => detail.value.userAnswer.questionAnswer as unknown as SubjectiveAw,
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
