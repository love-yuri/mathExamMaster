<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-23 19:12:39
 * @LastEditTime: 2025-02-28 15:17:24
 * @Description: 
-->
<template>
  <div>
    <Card>
      <template #content>
        <Button
          class="ml-2 flex-shrink-0"
          icon="pi pi-eye"
          label="预览题目"
          raised
          severity="info"
          @click="show(detail.questionId)"
        />
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
  </div>
</template>
<script setup lang="ts">
import type { SubjectiveAw, UserScoreDetail } from '@yuri/types';

import { computed, unref, useTemplateRef } from 'vue';

import { questionBankApi } from '@yuri/common';
import { Button, Card, WangEditor } from '@yuri/components';

import Preview from '#/views/questionManager/questionBank/components/preview.vue';

const props = defineProps<{
  detail: UserScoreDetail;
}>();

const previewRef = useTemplateRef('previewRef');

const userAnswer = computed(
  () => props.detail.userAnswer.questionAnswer as unknown as SubjectiveAw,
);

function show(id: string) {
  questionBankApi.get(id).then((res) => {
    unref(previewRef)?.open(res);
  });
}
</script>
