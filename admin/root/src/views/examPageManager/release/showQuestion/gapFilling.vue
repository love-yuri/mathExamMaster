<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-26 19:36:07
 * @LastEditTime: 2025-03-13 19:41:43
 * @Description: 
-->
<template>
  <div v-loading="loading">
    <!-- <PreviewEditor :content="detail.content" /> -->
    <Card class="">
      <template #content>
        <div class="flex flex-row items-center justify-between">
          <div class="flxe-grow flex">
            <Tag value="填空" />
            <div class="flex flex-grow flex-col p-2">
              <div
                v-for="(item, index) in userAnswer.answer"
                :key="index"
                class="my-1 flex flex-grow items-center text-[20px]"
              >
                <span class="mr-2 flex-shrink-0">第{{ index + 1 }}空:</span>
                <span class="mx-2 flex-shrink-0">用户答案: </span>
                <InputText readonly :value="item" class="mx-2" />
                <span class="mx-2 flex-shrink-0">正确答案: </span>
                <InputText readonly :value="questionAnswer.answer[index]" />
              </div>
            </div>
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
                  command: () => aiCreateScore(),
                  icon: 'pi pi-slack',
                  label: 'AI评分',
                },
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
    <ShowAiScore ref="showAiScoreRef" />
  </div>
</template>
<script setup lang="ts">
import type { GapFillingAw, UserScoreDetail } from '@yuri/types';

import { computed, ref, unref, useTemplateRef } from 'vue';

import { message, questionBankApi, systemApi } from '@yuri/common';
import { Card, InputText, SplitButton, Tag } from '@yuri/components';

import Preview from '#/views/questionManager/questionBank/components/preview.vue';

import QuickScore from './quickScore.vue';
import ShowAiScore from './showAiScore.vue';

const emits = defineEmits(['setScore']);
const detail = defineModel<UserScoreDetail>('detail', { required: true });

const loading = ref(false);
const showAiScoreRef = useTemplateRef('showAiScoreRef');
const quickScoreRef = useTemplateRef('quickScoreRef');
const previewRef = useTemplateRef('previewRef');
const questionAnswer = computed(
  () => detail.value.questionAnswer as GapFillingAw,
);

const userAnswer = computed(
  () => detail.value.userAnswer.questionAnswer as unknown as GapFillingAw,
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

/**
 * AI评分
 */
function aiCreateScore() {
  message.default.success('AI评分中...');
  loading.value = true;
  systemApi.aiCreateScore(detail.value).then((res) => {
    loading.value = false;
    if (!res) {
      message.default.error('AI评分失败!');
      return;
    }
    showAiScoreRef.value?.open(res);
    setScore(res.score);
  });
}
</script>
