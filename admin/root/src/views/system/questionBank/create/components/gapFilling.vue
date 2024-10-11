<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-08 21:02:28
 * @LastEditTime: 2024-10-11 21:04:32
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
        class="mx-2"
        icon="pi pi-plus"
        label="创建题目"
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
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { type GapFillingAnswer } from '#/views/system/questionBank/types';
import {
  QuestionBank,
  questionBankApi,
  QuestionTypeEnum,
} from '#/api/questionBankApi';
import { Button, InputText, MultiSelect, WangEditor } from '#/components';
import { onMounted, ref } from 'vue';
import {
  checkEmpty,
  checkListEmpty,
  checkSuccess,
} from '#/common/utils/valueCheck';
import message from '#/common/utils/message';
import {
  type KnowledgePoint,
  knowledgePointApi,
} from '#/api/knowledgePointApi';

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
    message.error('请输入题目!');
    return;
  }
  checkListEmpty(answer.value.answer, '请输入正确答案!', (v) => v.value);
  question.value.answer = JSON.stringify(answer.value);
  checkSuccess(
    questionBankApi.save({
      knowledgePointIds: selectedKnowledgePoints.value.map((it) => it.id!),
      questionBank: question.value,
    }),
    true,
    '题目',
  );
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
 * 挂载时加载
 */
onMounted(() => {
  loadKnowledgePoints();
});
</script>
