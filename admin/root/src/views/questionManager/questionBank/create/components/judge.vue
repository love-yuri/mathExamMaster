<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-08 20:59:15
 * @LastEditTime: 2025-02-22 19:15:44
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
        v-if="isUpdate"
        class="mr-2"
        icon="pi pi-spin pi-spinner"
        label="取消修改"
        severity="secondary"
        @click="$emit('cancel')"
      />
    </div>
  </div>
</template>
<script setup lang="ts">
import {
  checkEmpty,
  checkSuccess,
  knowledgePointApi,
  message,
  questionBankApi,
} from '@yuri/common';
import {
  Button,
  MultiSelect,
  RadioButton,
  Rating,
  WangEditor,
} from '@yuri/components';
import { JudgeAnswer, KnowledgePoint } from '@yuri/types';
import { onMounted, ref } from 'vue';

const emits = defineEmits(['cancel', 'update']);

const isUpdate = ref(false);
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
    }),
    !isUpdate.value,
    '题目',
    () => {
      if (isUpdate.value) {
        emits('update');
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
defineExpose({ openAsUpdate });
/**
 * 挂载时加载
 */
onMounted(() => {
  loadKnowledgePoints();
});
</script>
