<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-26 19:36:07
 * @LastEditTime: 2025-02-28 14:13:26
 * @Description: 
-->
<template>
  <div>
    <!-- <PreviewEditor :content="detail.content" /> -->
    <Card class="">
      <template #content>
        <div class="flex flex-row items-center justify-between">
          <div class="flex">
            <Tag value="单选" />
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
    <QuickScore ref="quickScoreRef" />
  </div>
</template>
<script setup lang="ts">
import type { SingleChoiceAw, UserScoreDetail } from '@yuri/types';

import { computed, unref, useTemplateRef } from 'vue';

import { questionBankApi } from '@yuri/common';
import { Button, Card, SplitButton, Tag } from '@yuri/components';

import Preview from '#/views/questionManager/questionBank/components/preview.vue';

import QuickScore from './quickScore.vue';

const props = defineProps<{
  detail: UserScoreDetail;
}>();

const quickScoreRef = useTemplateRef('quickScoreRef');
const previewRef = useTemplateRef('previewRef');
const questionAnswer = computed(
  () => props.detail.questionAnswer as SingleChoiceAw,
);

function severity(index: number) {
  if (userAnswer.value.answer === questionAnswer.value.answer) {
    if (index === questionAnswer.value.answer) {
      return 'success';
    }
    return 'secondary';
  } else {
    if (index === questionAnswer.value.answer) {
      return 'success';
    }
    return index === userAnswer.value.answer ? 'danger' : 'secondary';
  }
}
const userAnswer = computed(
  () => props.detail.userAnswer.questionAnswer as unknown as SingleChoiceAw,
);

function show(id: string) {
  questionBankApi.get(id).then((res) => {
    unref(previewRef)?.open(res);
  });
}
</script>
