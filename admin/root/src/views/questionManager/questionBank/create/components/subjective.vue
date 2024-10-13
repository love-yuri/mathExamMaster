<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-08 21:03:18
 * @LastEditTime: 2024-10-11 21:06:59
 * @Description: 主观题
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
    <Button
      class="my-2"
      icon="pi pi-plus"
      label="创建题目"
      severity="success"
      @click="create"
    />
    <Button
      class="mx-2"
      icon="pi pi-sync"
      label="重置答案"
      severity="danger"
      @click="cleanQuestion"
    />
  </div>
</template>
<script setup lang="ts">
import {
  QuestionBank,
  questionBankApi,
  QuestionTypeEnum,
} from '#/api/questionBankApi';
import { Button, MultiSelect, WangEditor } from '#/components';
import { onMounted, ref } from 'vue';
import { checkEmpty, checkSuccess } from '#/common/utils/valueCheck';
import message from '#/common/utils/message';
import {
  type KnowledgePoint,
  knowledgePointApi,
} from '#/api/knowledgePointApi';

const question = ref(new QuestionBank(QuestionTypeEnum.SUBJECTIVE));

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
  question.value.answer = '{}';
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
}

/**
 * 挂载时加载
 */
onMounted(() => {
  loadKnowledgePoints();
});
</script>
