<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-23 19:12:39
 * @LastEditTime: 2025-02-28 10:06:22
 * @Description: 
-->
<template>
  <div v-if="question">
    <PreviewEditor :content="question.content" />
    <Card class="mt-4">
      <template #content>
        <div class="flex flex-col p-2">
          <div
            v-for="(_, index) in answer.answer"
            :key="index"
            class="my-2 flex text-[20px]"
          >
            <span class="mr-2 flex-shrink-0">第{{ index + 1 }}空:</span>
            <InputText
              v-model="
                (
                  (question.userAnswer.questionAnswer as GapFillingAw)
                    .answer as string[]
                )[index]
              "
              class="w-full"
              @change="change"
            />
          </div>
        </div>
      </template>
    </Card>
  </div>
</template>
<script setup lang="ts">
import type { GapFillingAw, QuestionInfo } from '@yuri/types';

import { computed } from 'vue';

import { Card, InputText, PreviewEditor } from '@yuri/components';

const emit = defineEmits(['updateAnswer']);
const question = defineModel<QuestionInfo>('question');
const answer = computed(
  () => question.value?.userAnswer.questionAnswer as GapFillingAw,
);
function change() {
  let hasAnswer = true;
  for (const item of answer.value.answer) {
    if (item === '') {
      hasAnswer = false;
    }
  }
  question.value!!.userAnswer.hasAnswer = hasAnswer;
  emit('updateAnswer');
}
</script>
