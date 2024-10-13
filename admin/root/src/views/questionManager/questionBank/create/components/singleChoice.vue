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
      <Button
        class="mr-2"
        icon="pi pi-sync"
        label="重置答案"
        severity="danger"
        @click="cleanQuestion"
      />
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
        placeholder="请输入选项内容，可为空..."
      />
      <Button
        class="ml-2"
        icon="pi pi-delete-left"
        severity="danger"
        @click="removeKey(index)"
      />
    </div>

    <div class="flex flex-col">
      <div class="mb-2 text-center text-xl font-semibold">
        请选择正确答案...
      </div>
      <div class="flex flex-col gap-4">
        <div
          v-for="(item, index) in answer.keys"
          :key="index"
          class="flex items-center"
        >
          <span class="mr-2 text-xl">{{
            String.fromCharCode(65 + index)
          }}</span>
          <RadioButton
            v-model="answer.answer"
            :input-id="index.toString()"
            :value="index"
            name="dynamic"
          />
          <label :for="item.value" class="ml-2">{{ item.value }}</label>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { type SingleChoiceAnswer } from '#/views/questionManager/questionBank/types';
import {
  QuestionBank,
  questionBankApi,
  QuestionTypeEnum,
} from '#/api/questionBankApi';
import {
  Button,
  InputText,
  MultiSelect,
  RadioButton,
  WangEditor,
} from '#/components';
import { onMounted, ref } from 'vue';
import { checkEmpty, checkSuccess } from '#/common/utils/valueCheck';
import message from '#/common/utils/message';
import {
  type KnowledgePoint,
  knowledgePointApi,
} from '#/api/knowledgePointApi';

const question = ref(new QuestionBank(QuestionTypeEnum.SINGLE_CHOICE));
const answer = ref<SingleChoiceAnswer>({
  answer: undefined,
  keys: [],
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
  checkEmpty(answer.value.answer, '请选择正确答案!');
  if (question.value.content === '<p><br></p>') {
    message.error('请输入题目!');
    return;
  }
  if (answer.value.keys.length < 2) {
    message.error('请至少添加两个选项!');
    return;
  }
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
 * 删除选项 还需要清除被删除的选项的答案
 * @param index 选项索引
 */
function removeKey(index: number) {
  answer.value.keys.splice(index, 1);
  const answerIndex = answer.value.answer!;
  if (answerIndex === undefined) {
    return;
  }
  if (answerIndex === index) {
    answer.value.answer = undefined;
  } else if (answerIndex > index) {
    answer.value.answer = answerIndex - 1;
  }
}

/**
 * 清空题目
 */
function cleanQuestion() {
  question.value.reset();
  selectedKnowledgePoints.value.length = 0;
  answer.value.answer = undefined;
  answer.value.keys.length = 0;
}

/**
 * 挂载时加载
 */
onMounted(() => {
  loadKnowledgePoints();
});
</script>
