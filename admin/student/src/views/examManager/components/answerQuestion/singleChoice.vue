<template>
  <div v-if="question">
    <PreviewEditor :content="question.content" />
    <Card class="mt-4">
      <template #content>
        <div class="flex flex-col p-2">
          <Button
            v-for="(item, index) in answer.options"
            :key="index"
            :label="`${String.fromCharCode(65 + index)}: ${item}`"
            :severity="answer.answer === index ? 'success' : 'secondary'"
            class="my-2"
            @click="chooseAnswer(index)"
          />
        </div>
      </template>
    </Card>
  </div>
</template>
<script setup lang="ts">
import type { QuestionInfo, SingleChoiceAw } from '@yuri/types';

import { computed } from 'vue';

import { Button, Card, PreviewEditor } from '@yuri/components';

const emit = defineEmits(['updateAnswer']);

const question = defineModel<QuestionInfo>('question');
const answer = computed(
  () => question.value?.userAnswer.questionAnswer as SingleChoiceAw,
);
function chooseAnswer(index: number) {
  const aw = question.value?.userAnswer.questionAnswer as SingleChoiceAw;
  aw.answer = index;
  question.value!!.userAnswer.hasAnswer = true;
  emit('updateAnswer');
}
</script>
