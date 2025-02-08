<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-13 15:04:45
 * @LastEditTime: 2025-02-08 15:10:10
 * @Description: 预览题目
-->
<template>
  <div>
    <Model width="800">
      <PreviewEditor :content="questionBank.content" />
      <template v-if="questionBank.type === QuestionTypeEnum.SINGLE_CHOICE">
        <div class="flex flex-col p-2">
          <Button
            v-for="(item, index) in singleChoiceAnswer.keys"
            :key="index"
            :label="`${String.fromCharCode(65 + index)}: ${item.value}`"
            :severity="
              index === singleChoiceAnswer.answer ? 'success' : 'secondary'
            "
            class="my-2"
          />
        </div>
      </template>
      <template
        v-else-if="questionBank.type === QuestionTypeEnum.MULTIPLE_CHOICE"
      >
        <div class="flex flex-col p-2">
          <Button
            v-for="(item, index) in multipleChoiceAnswer.keys"
            :key="index"
            :label="`${String.fromCharCode(65 + index)}: ${item.value}`"
            :severity="
              multipleChoiceAnswer.answer.includes(index)
                ? 'success'
                : 'secondary'
            "
            class="my-2"
          />
        </div>
      </template>
      <template v-else-if="questionBank.type === QuestionTypeEnum.GAP_FILLING">
        <div class="flex flex-col p-2">
          <div
            v-for="(item, index) in gapFillingAnswer.answer"
            :key="index"
            class="my-2 text-[20px]"
          >
            第{{ index + 1 }}空: {{ item.value }}
          </div>
        </div>
      </template>
      <template v-else-if="questionBank.type === QuestionTypeEnum.JUDGE">
        <div class="mt-2 flex items-center">
          <div class="mr-3 text-xl">正确答案:</div>
          <Button
            :icon="judgeAnswer.answer ? 'pi pi-check' : 'pi pi-times'"
            :severity="judgeAnswer.answer ? 'success' : 'danger'"
            rounded
          />
        </div>
      </template>
    </Model>
  </div>
</template>
<script setup lang="ts">
import { useVbenModal } from '@vben/common-ui';
import { Button, PreviewEditor } from '#/components';
import {
  type GapFillingAnswer,
  type JudgeAnswer,
  type MultipleChoiceAnswer,
  type SingleChoiceAnswer,
} from '#/views/questionManager/questionBank/types';
import { computed, ref } from 'vue';
import { QuestionBank, QuestionTypeEnum } from '@yuri/types';

/* 处理预览弹窗 */
const [Model, modelApi] = useVbenModal({
  onConfirm() {
    modelApi.close();
  },
  title: '查看题目',
});

const questionBank = ref<QuestionBank>(new QuestionBank());

/**
 * 解析答案
 */
const singleChoiceAnswer = computed(
  () => JSON.parse(questionBank.value.answer) as SingleChoiceAnswer,
);

const multipleChoiceAnswer = computed(
  () => JSON.parse(questionBank.value.answer) as MultipleChoiceAnswer,
);

const gapFillingAnswer = computed(
  () => JSON.parse(questionBank.value.answer) as GapFillingAnswer,
);

const judgeAnswer = computed(
  () => JSON.parse(questionBank.value.answer) as JudgeAnswer,
);

/**
 * 导出打开预览
 * 需要传入题目数据
 */
function open(value: QuestionBank) {
  modelApi.open();
  questionBank.value.copy(value);
}

defineExpose({ open });
</script>
