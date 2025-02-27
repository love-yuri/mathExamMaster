<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-26 19:36:07
 * @LastEditTime: 2025-02-27 18:38:16
 * @Description: 
-->
<template>
  <div>
    <!-- <PreviewEditor :content="detail.content" /> -->
    <Card class="">
      <template #content>
        <div class="flex flex-row items-center justify-between">
          <div class="flex">
            <Tag value="判断" class="mr-1" />
            <Button :severity="severity(true)" icon="pi pi-check" rounded />
            <Button
              :severity="severity(false)"
              class="ml-2"
              icon="pi pi-times"
              rounded
            />
          </div>
          <div class="flex items-center">
            <div class="flex items-center">
              用户得分:
              <span class="ml-1 text-[24px] font-bold text-blue-500">{{
                detail.score
              }}</span>
            </div>
            <Button
              class="ml-2 flex-shrink-0"
              icon="pi pi-eye"
              label="预览题目"
              raised
              severity="info"
              @click="show(detail.questionId)"
            />
          </div>
        </div>
      </template>
    </Card>
    <Preview ref="previewRef" />
  </div>
</template>
<script setup lang="ts">
import type { JudgeAw, UserScoreDetail } from '@yuri/types';

import Preview from '#/views/questionManager/questionBank/components/preview.vue';
import { questionBankApi } from '@yuri/common';
import { Button, Card, Tag } from '@yuri/components';
import { computed, unref, useTemplateRef } from 'vue';

const props = defineProps<{
  detail: UserScoreDetail;
}>();

const previewRef = useTemplateRef('previewRef');
const questionAnswer = computed(() => props.detail.questionAnswer as JudgeAw);

function severity(a: boolean) {
  if (userAnswer.value.answer === questionAnswer.value.answer) {
    if (a === questionAnswer.value.answer) {
      return 'success';
    }
    return 'secondary';
  } else {
    if (a === questionAnswer.value.answer) {
      return 'success';
    }
    return a === userAnswer.value.answer ? 'danger' : 'secondary';
  }
}
const userAnswer = computed(
  () => props.detail.userAnswer.questionAnswer as unknown as JudgeAw,
);

function show(id: string) {
  questionBankApi.get(id).then((res) => {
    unref(previewRef)?.open(res);
  });
}
</script>
