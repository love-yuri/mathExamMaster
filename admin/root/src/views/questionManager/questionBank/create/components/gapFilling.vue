<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-08 21:02:28
 * @LastEditTime: 2025-02-08 15:11:05
 * @Description: 填空题
-->
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
        label="添加答案"
        severity="info"
        @click="
          answer.answer.push({
            value: '',
          })
        "
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
    <div class="flex flex-col">
      <div class="flex flex-col">
        <div
          v-for="(item, index) in answer.answer"
          :key="index"
          class="mb-2 flex h-12 flex-row items-center"
        >
          <div class="flex-shrink-0">第 {{ index + 1 }} 问答案:</div>
          <InputText
            v-model="item.value"
            class="ml-2 w-full"
            placeholder="请输入答案..."
          />
          <Button
            class="ml-2"
            icon="pi pi-delete-left"
            severity="danger"
            @click="removeKey(index)"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { type GapFillingAnswer } from '#/views/questionManager/questionBank/types';
import {
  Button,
  InputText,
  MultiSelect,
  Rating,
  WangEditor,
} from '#/components';
import { onMounted, ref } from 'vue';
import { knowledgePointApi, checkEmpty, checkListEmpty, questionBankApi, checkSuccess, message } from '@yuri/common';
import { QuestionBank, QuestionTypeEnum, KnowledgePoint } from '@yuri/types';

const emits = defineEmits(['cancel', 'update']);

const isUpdate = ref(false);
const question = ref(new QuestionBank(QuestionTypeEnum.GAP_FILLING));
const answer = ref<GapFillingAnswer>({
  answer: [],
});

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
  if (question.value.content === '<p><br></p>') {
    message.default.error('请输入题目!');
    return;
  }
  checkListEmpty(answer.value.answer, '请输入正确答案!', (v) => v.value);
  question.value.answer = JSON.stringify(answer.value);
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
  answer.value.answer.splice(index, 1);
}

/**
 * 清空题目
 */
function cleanQuestion() {
  question.value.reset();
  selectedKnowledgePoints.value.length = 0;
  answer.value.answer.length = 0;
}

/**
 * 处理更新
 */
function openAsUpdate(v: QuestionBank, k: KnowledgePoint[]) {
  isUpdate.value = true;
  question.value.copy(v);
  answer.value = JSON.parse(v.answer!) as GapFillingAnswer;
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
