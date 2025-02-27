<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-23 19:12:39
 * @LastEditTime: 2025-02-23 21:16:36
 * @Description: 
-->
<template>
  <div v-if="question">
    <PreviewEditor :content="question.content" />
    <Card class="mt-4">
      <template #content>
        <div class="mt-2 flex items-center">
          <Button
            :severity="answer.answer === true ? 'success' : 'secondary'"
            icon="pi pi-check"
            rounded
            @click="chooseAnswer(0)"
          />
          <Button
            :severity="answer.answer === false ? 'success' : 'secondary'"
            class="ml-2"
            icon="pi pi-times"
            rounded
            @click="chooseAnswer(1)"
          />
        </div>
      </template>
    </Card>
  </div>
</template>
<script setup lang="ts">
import type { JudgeAw, QuestionInfo } from '@yuri/types';

import { Button, Card, PreviewEditor } from '@yuri/components';
import { computed } from 'vue';

const emit = defineEmits(['updateAnswer']);
const question = defineModel<QuestionInfo>('question');
const answer = computed(
  () => question.value?.userAnswer.questionAnswer as JudgeAw,
);
function chooseAnswer(index: number) {
  const aw = question.value?.userAnswer.questionAnswer as JudgeAw;
  aw.answer = index === 0;
  question.value!!.userAnswer.hasAnswer = true;
  emit('updateAnswer');
}
</script>
