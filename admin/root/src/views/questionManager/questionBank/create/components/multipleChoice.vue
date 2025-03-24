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
    <div
      v-for="(_, index) in question.answer.options"
      :key="index"
      class="my-2 flex items-center"
    >
      <div class="flex-shrink-0 text-2xl">
        {{ String.fromCharCode(65 + index) }} &nbsp;
      </div>
      <Checkbox
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
  checkListEmpty,
  checkSuccess,
  knowledgePointApi,
  message,
  questionBankApi,
  questionCategoryApi,
} from '@yuri/common';
import {
  Button,
  Checkbox,
  InputText,
  MultiSelect,
  Select,
  WangEditor,
} from '@yuri/components';
import {
  KnowledgePoint,
  MultipleChoiceAnswer,
  QuestionAnswer,
  QuestionCategory,
} from '@yuri/types';

import AiCreate from './aiCreate.vue';
import SetDescription from './setDescription.vue';

const emits = defineEmits(['update', 'cancel']);

const showDescription = ref(false);
const isUpdate = ref(false);
const aiCreateRef = useTemplateRef('aiCreateRef');
const question = ref(new MultipleChoiceAnswer());

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
  checkListEmpty(question.value.answer.answer, '请选择正确答案!');
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
}

/**
 * 删除选项 还需要清除被删除的选项的答案
 * @param index 选项索引
 */
function removeKey(index: number) {
  question.value.answer.options.splice(index, 1);
  question.value.answer.answer = question.value.answer.answer.filter(
    (item) => item !== index,
  );
  question.value.answer.answer.forEach((i, index) => {
    if (i > index) {
      question.value.answer.answer[index]!--;
    }
  });
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
function openAsUpdate(v: MultipleChoiceAnswer, k: KnowledgePoint[]) {
  isUpdate.value = true;
  question.value.copy(v);
  selectedKnowledgePoints.value = k;
}

/**
 * ai生成题目
 */
function aiCreateQuestion() {
  aiCreateRef.value?.open((param: QuestionAnswer) => {
    const q = param as MultipleChoiceAnswer;
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
