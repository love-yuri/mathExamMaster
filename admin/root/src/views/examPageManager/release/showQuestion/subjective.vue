<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-23 19:12:39
 * @LastEditTime: 2025-02-27 18:53:42
 * @Description: 
-->
<template>
  <div>
    <Button
      class="ml-2 flex-shrink-0"
      icon="pi pi-eye"
      label="预览题目"
      raised
      severity="info"
      @click="show(detail.questionId)"
    />
    <Card class="mt-4">
      <template #content>
        <div class="mt-2 flex items-center">
          <WangEditor
            :show-toolbar-config="false"
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

import Preview from '#/views/questionManager/questionBank/components/preview.vue';
import { questionBankApi } from '@yuri/common';
import { Button, Card, WangEditor } from '@yuri/components';
import { computed, unref, useTemplateRef } from 'vue';

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
