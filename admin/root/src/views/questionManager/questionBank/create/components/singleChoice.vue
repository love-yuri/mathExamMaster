<template>
  <div>
    <div class="flex flex-col">
      <WangEditor
        v-model:content="question.content"
        placeholder="请输入题目..."
      />
    </div>
    <div class="my-2 flex items-center">
      <div class="mr-4 text-[20px]">难度:</div>
      <Rating v-model="question.difficulty" :stars="9" />
    </div>
    <MultiSelect
      v-model="selectedKnowledgePoints"
      :options="knowledgePoints"
      class="my-2 w-full"
      filter
      option-label="name"
      placeholder="请选择关联知识点..."
    />
    <div class="my-3 flex">
      <Button
        class=""
        icon="pi pi-plus"
        label="添加选项"
        severity="info"
        @click="question.answer.options.push('')"
      />
      <Button
        :icon="`pi ${isUpdate ? 'pi-pencil' : 'pi-plus'}`"
        :label="`${isUpdate ? '修改' : '创建'}题目`"
        class="mx-2"
        severity="success"
        @click="create"
      />
      <Button
        class="mr-2"
        icon="pi pi-sync"
        label="重置答案"
        severity="danger"
        @click="cleanQuestion"
      />
      <Button
        v-if="isUpdate"
        class="mr-2"
        icon="pi pi-spin pi-spinner"
        label="取消修改"
        severity="secondary"
        @click="$emit('cancel')"
      />
    </div>
    <div
      v-for="(_, index) in question.answer.options"
      :key="index"
      class="my-2 flex items-center"
    >
      <div class="flex-shrink-0 text-2xl">
        {{ String.fromCharCode(65 + index) }} &nbsp;
      </div>
      <RadioButton
        v-model="question.answer.answer"
        :input-id="index.toString()"
        :value="index"
        class="mr-2"
        name="dynamic"
      />
      <InputText
        v-model="question.answer.options[index]"
        class="w-full"
        placeholder="请输入选项内容，可为空..."
      />
      <Button
        class="ml-2"
        icon="pi pi-delete-left"
        severity="danger"
        @click="removeKey(index)"
      />
    </div>
  </div>
</template>
<script setup lang="ts">
import type { KnowledgePoint } from '@yuri/types';

import {
  checkEmpty,
  checkSuccess,
  knowledgePointApi,
  message,
  questionBankApi,
} from '@yuri/common';
import {
  Button,
  InputText,
  MultiSelect,
  RadioButton,
  Rating,
  WangEditor,
} from '@yuri/components';
import { SingleChoiceAnswer } from '@yuri/types';
import { onMounted, ref } from 'vue';

const emits = defineEmits(['cancel', 'update']);

const isUpdate = ref(false);
const question = ref(new SingleChoiceAnswer());

/**
 * 处理知识点选择
 */
const knowledgePoints = ref<KnowledgePoint[]>([]);
const selectedKnowledgePoints = ref<KnowledgePoint[]>([]);
const loadKnowledgePoints = async () => {
  const res = await knowledgePointApi.list();
  knowledgePoints.value = res;
};

/**
 * 创建题目
 * 需要检查题目内容和选项内容
 */
function create() {
  checkEmpty(question.value.content, '请输入题目!');
  checkEmpty(question.value.answer.answer, '请选择正确答案!');
  if (question.value.content === '<p><br></p>') {
    message.default.error('请输入题目!');
    return;
  }
  if (question.value.answer.options.length < 2) {
    message.default.error('请至少添加两个选项!');
    return;
  }
  const fun = isUpdate.value
    ? questionBankApi.updateSimple
    : questionBankApi.saveSimple;
  checkSuccess(
    fun({
      knowledgePointIds: selectedKnowledgePoints.value.map((it) => it.id!),
      questionBank: question.value,
    }),
    !isUpdate.value,
    '题目',
    () => {
      if (isUpdate.value) {
        emits('update');
      }
    },
  );
}

/**
 * 删除选项 还需要清除被删除的选项的答案
 * @param index 选项索引
 */
function removeKey(index: number) {
  question.value.answer.options.splice(index, 1);
  const answerIndex = question.value.answer.answer;
  if (answerIndex === undefined) {
    return;
  }
  if (answerIndex === index) {
    question.value.answer.answer = undefined;
  } else if (answerIndex > index) {
    question.value.answer.answer = answerIndex - 1;
  }
}

/**
 * 清空题目
 */
function cleanQuestion() {
  question.value.reset();
  selectedKnowledgePoints.value.length = 0;
  question.value.answer.answer = undefined;
  question.value.answer.options.length = 0;
}

/**
 * 处理更新
 */
function openAsUpdate(v: SingleChoiceAnswer, k: KnowledgePoint[]) {
  isUpdate.value = true;
  question.value.copy(v);
  selectedKnowledgePoints.value = k;
}
defineExpose({ openAsUpdate });

/**
 * 挂载时加载
 */
onMounted(() => {
  loadKnowledgePoints();
});
</script>
