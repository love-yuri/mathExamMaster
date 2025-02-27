<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-26 19:36:07
 * @LastEditTime: 2025-02-27 18:46:51
 * @Description: 
-->
<template>
  <div>
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
                <InputText :value="item" class="mx-2" />
                <span class="mx-2 flex-shrink-0">正确答案: </span>
                <InputText :value="questionAnswer.answer[index]" />
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
import type { GapFillingAw, UserScoreDetail } from '@yuri/types';

import Preview from '#/views/questionManager/questionBank/components/preview.vue';
import { questionBankApi } from '@yuri/common';
import { Button, Card, InputText, Tag } from '@yuri/components';
import { computed, unref, useTemplateRef } from 'vue';

const props = defineProps<{
  detail: UserScoreDetail;
}>();

const previewRef = useTemplateRef('previewRef');
const questionAnswer = computed(
  () => props.detail.questionAnswer as GapFillingAw,
);

const userAnswer = computed(
  () => props.detail.userAnswer.questionAnswer as unknown as GapFillingAw,
);

function show(id: string) {
  questionBankApi.get(id).then((res) => {
    unref(previewRef)?.open(res);
  });
}
</script>
