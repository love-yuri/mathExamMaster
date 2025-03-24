<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-08 20:59:15
 * @LastEditTime: 2025-03-24 19:48:32
 * @Description: 单选题
-->
<template>
  <div>
    <div class="flex flex-col">
      <WangEditor
        v-model:content="question.content"
        placeholder="请输入题目..."
      />
    </div>
    <Select
      v-model="question.difficulty"
      :options="[
        { label: '易', value: 2 },
        { label: '中', value: 4 },
        { label: '难', value: 6 },
        { label: '极难', value: 8 },
      ]"
      option-label="label"
      option-value="value"
      placeholder="请选择难度"
      class="mt-2 w-full"
    />
    <MultiSelect
      v-model="selectedKnowledgePoints"
      :options="knowledgePoints"
      class="my-2 w-full"
      filter
      option-label="name"
      placeholder="请选择关联知识点..."
    />
    <MultiSelect
      v-model="questionCategoryIds"
      :options="categories"
      class="my-2 w-full"
      option-value="id"
      filter
      option-label="name"
      placeholder="请选择试卷分类..."
    />
    <div class="my-3 flex">
      <div class="flex items-center">
        <span class="ml-2 text-xl font-semibold">正确答案: &nbsp;&nbsp;</span>
        <div class="mx-2">
          <RadioButton v-model="question.answer.answer" :value="false" />
          <label class="ml-2">错误</label>
        </div>
        <div>
          <RadioButton v-model="question.answer.answer" :value="true" />
          <label class="ml-2">正确</label>
        </div>
      </div>
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
        class="mr-2"
        icon="pi pi-pen-to-square"
        label="添加描述"
        severity="success"
        @click="showDescription = true"
      />
      <Button
        class="mr-2"
        icon="pi pi-slack"
        label="ai自动生成题目"
        @click="aiCreateQuestion"
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
    <SetDescription
      v-model:show="showDescription"
      v-model:content="question.description"
    />
    <AiCreate ref="aiCreateRef" :type="question.type" />
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref, useTemplateRef } from 'vue';

import {
  checkEmpty,
  checkSuccess,
  knowledgePointApi,
  message,
  questionBankApi,
  questionCategoryApi,
} from '@yuri/common';
import {
  Button,
  MultiSelect,
  RadioButton,
  Select,
  WangEditor,
} from '@yuri/components';
import {
  JudgeAnswer,
  KnowledgePoint,
  QuestionAnswer,
  QuestionCategory,
} from '@yuri/types';

import AiCreate from './aiCreate.vue';
import SetDescription from './setDescription.vue';

const emits = defineEmits(['cancel', 'update']);

const showDescription = ref(false);
const isUpdate = ref(false);
const aiCreateRef = useTemplateRef('aiCreateRef');
const question = ref(new JudgeAnswer());

/**
 * 处理知识点选择
 */
const knowledgePoints = ref<KnowledgePoint[]>([]);
const selectedKnowledgePoints = ref<KnowledgePoint[]>([]);
const loadKnowledgePoints = async () => {
  const res = await knowledgePointApi.list();
  knowledgePoints.value = res;
};

const categories = ref<QuestionCategory[]>([]);
const questionCategoryIds = ref<string[]>([]);

const loadCategories = async () => {
  const res = await questionCategoryApi.list();
  categories.value = res;
};

/**
 * 创建题目
 * 需要检查题目内容和选项内容
 */
function create() {
  checkEmpty(question.value.content, '请输入题目!');
  if (question.value.content === '<p><br></p>') {
    message.default.error('请输入题目!');
    return;
  }
  // question.value.answer = JSON.stringify(answer.value);
  const fun = isUpdate.value
    ? questionBankApi.updateSimple
    : questionBankApi.saveSimple;
  checkSuccess(
    fun({
      knowledgePointIds: selectedKnowledgePoints.value.map((it) => it.id!),
      questionBank: question.value,
      questionCategoryIds: questionCategoryIds.value,
    }),
    !isUpdate.value,
    '题目',
    (res) => {
      if (isUpdate.value) {
        emits('update');
      }
      if (res && !isUpdate.value) {
        cleanQuestion();
      }
    },
  );
  if (isUpdate.value) {
    emits('update');
  }
}

/**
 * 清空题目
 */
function cleanQuestion() {
  question.value.reset();
  selectedKnowledgePoints.value.length = 0;
}

/**
 * 处理更新
 */
function openAsUpdate(v: JudgeAnswer, k: KnowledgePoint[]) {
  isUpdate.value = true;
  question.value.copy(v);
  selectedKnowledgePoints.value = k;
}

/**
 * ai生成题目
 */
function aiCreateQuestion() {
  aiCreateRef.value?.open((param: QuestionAnswer) => {
    const q = param as JudgeAnswer;
    question.value.copy(q);
  });
}

defineExpose({ openAsUpdate });
/**
 * 挂载时加载
 */
onMounted(() => {
  loadKnowledgePoints();
  loadCategories();
});
</script>
