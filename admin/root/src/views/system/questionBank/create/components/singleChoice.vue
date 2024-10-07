<template>
  <div>
    <div class="flex flex-col">
      <div class="mb-2 flex-shrink-0 text-2xl">请输入题目...</div>
      <WangEditor v-model:content="question.content" />
    </div>
    <div
      v-for="(item, index) in answer.keys"
      :key="index"
      class="my-2 flex items-center"
    >
      <div class="mb-2 flex-shrink-0 text-2xl">
        选项{{ String.fromCharCode(65 + index) }}: &nbsp;
      </div>
      <InputText
        v-model="item.value"
        class="w-full"
        placeholder="请输入选项..."
      />
      <Button
        class="ml-2"
        icon="pi pi-delete-left"
        severity="danger"
        @click="answer.keys.splice(index, 1)"
      />
    </div>
    <div class="my-3 flex">
      <Button
        class=""
        icon="pi pi-plus"
        label="添加选项"
        severity="info"
        @click="
          answer.keys.push({
            value: '',
          })
        "
      />
      <Button
        class="mx-2"
        icon="pi pi-plus"
        label="创建题目"
        severity="success"
        @click="create"
      />
    </div>
    <div class="flex flex-col">
      <div class="mb-2 text-xl font-semibold">请选择正确答案...</div>
      <div class="flex flex-col gap-4">
        <div
          v-for="(item, index) in answer.keys"
          :key="index"
          class="flex items-center"
        >
          <RadioButton
            v-model="answer.answer"
            :input-id="index.toString()"
            :value="item.value"
            name="dynamic"
          />
          <label :for="item.value" class="ml-2">{{ item.value }}</label>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { type SingleChoiceAnswer } from '#/views/system/questionBank/types';
import { QuestionBank, QuestionTypeEnum } from '#/api/questionBankApi';
import { Button, InputText, RadioButton, WangEditor } from '#/components';
import { ref } from 'vue';
import { checkEmpty, checkListEmpty } from '#/common/utils/valueCheck';

const question = ref(new QuestionBank(QuestionTypeEnum.SINGLE_CHOICE));
const answer = ref<SingleChoiceAnswer>({
  answer: 0,
  keys: [],
});

/**
 * 创建题目
 * 需要检查题目内容和选项内容
 */
function create() {
  checkEmpty(question.value.content, '请输入题目!');
  checkEmpty(answer.value.answer, '请选择正确答案!');
  checkListEmpty(answer.value.keys, '请输入选项');
}
</script>
