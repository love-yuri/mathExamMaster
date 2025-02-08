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
        @click="
          answer.keys.push({
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
          <Checkbox
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
import { type MultipleChoiceAnswer } from '#/views/questionManager/questionBank/types';
import {
  Button,
  Checkbox,
  InputText,
  MultiSelect,
  Rating,
  WangEditor,
} from '@yuri/components';
import { onMounted, ref } from 'vue';
import {
  knowledgePointApi,
  checkEmpty,
  questionBankApi,
  checkSuccess,
} from '@yuri/common';
import { QuestionBank, QuestionTypeEnum, KnowledgePoint } from '@yuri/types';
import { message } from '@yuri/common';

const emits = defineEmits(['update', 'cancel']);

const isUpdate = ref(false);
const question = ref(new QuestionBank(QuestionTypeEnum.MULTIPLE_CHOICE));
const answer = ref<MultipleChoiceAnswer>({
  answer: [],
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
    message.default.error('请输入题目!');
    return;
  }
  if (answer.value.keys.length < 2) {
    message.default.error('请至少添加两个选项!');
    return;
  }
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
  answer.value.keys.splice(index, 1);
  const an = answer.value.answer!;
  if (an === undefined) {
    return;
  }
  answer.value.answer = an.filter((item) => item !== index);
  answer.value.answer.forEach((i, index) => {
    if (i > index) {
      answer.value.answer[index]!--;
    }
  });
}

/**
 * 清空题目
 */
function cleanQuestion() {
  question.value.reset();
  selectedKnowledgePoints.value.length = 0;
  answer.value.answer.length = 0;
  answer.value.keys.length = 0;
}

/**
 * 处理更新
 */
function openAsUpdate(v: QuestionBank, k: KnowledgePoint[]) {
  isUpdate.value = true;
  question.value.copy(v);
  answer.value = JSON.parse(v.answer!) as MultipleChoiceAnswer;
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
