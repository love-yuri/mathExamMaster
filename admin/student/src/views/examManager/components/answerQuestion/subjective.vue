<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-23 19:12:39
 * @LastEditTime: 2025-02-24 11:43:42
 * @Description: 
-->
<template>
  <div v-if="question">
    <PreviewEditor :content="question.content" />
    <Card class="mt-4">
      <template #content>
        <div class="mt-2 flex items-center">
          <WangEditor
            v-model:content="
              (question.userAnswer.questionAnswer as SubjectiveAw)
                .answer as string
            "
            placeholder="请输入答案..."
            @change="editorContentChange"
          />
        </div>
      </template>
    </Card>
  </div>
</template>
<script setup lang="ts">
import { debounce } from '@yuri/common';
import { Card, PreviewEditor, WangEditor } from '@yuri/components';
import type { QuestionInfo, SubjectiveAw } from '@yuri/types';

const question = defineModel<QuestionInfo>('question');
const emit = defineEmits(['updateAnswer']);

const editorContentChange = debounce(() => {
  question.value!!.userAnswer.hasAnswer = true;
  emit('updateAnswer');
}, 2000);
</script>
