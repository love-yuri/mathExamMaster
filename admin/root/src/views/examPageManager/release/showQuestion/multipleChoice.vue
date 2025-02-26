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
            :severity="answer.answer.includes(index) ? 'success' : 'secondary'"
            class="my-2"
            @click="chooseAnswer(index)"
          />
        </div>
      </template>
    </Card>
  </div>
</template>
<script setup lang="ts">
import { Card, PreviewEditor, Button } from '@yuri/components';
import type { MultipleChoiceAw, QuestionInfo } from '@yuri/types';
import { computed } from 'vue';

const question = defineModel<QuestionInfo>('question');
const emit = defineEmits(['updateAnswer']);

const answer = computed(
  () => question.value?.userAnswer.questionAnswer as MultipleChoiceAw,
);
function chooseAnswer(index: number) {
  const aw = question.value?.userAnswer.questionAnswer as MultipleChoiceAw;
  if (aw.answer.includes(index)) {
    aw.answer = aw.answer.filter((item) => item !== index);
  } else {
    aw.answer.push(index);
  }
  question.value!!.userAnswer.hasAnswer = aw.answer.length > 0;
  emit('updateAnswer');
}
</script>
