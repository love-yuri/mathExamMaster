<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-23 18:55:25
 * @LastEditTime: 2024-12-28 16:04:17
 * @Description: 
-->
<template>
  <div class="flex h-full">
    <!-- 左侧导航栏 -->
    <div class="h-full w-[300px] flex-shrink-0 border-r-2 p-4">
      <div>
        <Card>
          <template #content>
            <div class="text-[22px]">
              剩余时间:
              <span class="font-semibold text-red-500">
                {{ leftTimeText }}
              </span>
            </div>
            <Button
              class="mt-2 w-full"
              icon="pi pi-times"
              label="交卷"
              severity="danger"
            />
          </template>
        </Card>
        <Card class="mt-2">
          <template #content>
            <div>
              <div class="mb-3 flex justify-between">
                <Button label="未完成" raised rounded severity="secondary" />
                <Button label="已完成" raised rounded severity="success" />
                <Button label="当前" raised rounded severity="info" />
              </div>
              <template v-for="type in questions.keys()" :key="type">
                <div
                  class="mt-4 rounded-sm bg-gray-200 px-[5px] py-[4px] text-[22px] font-bold"
                >
                  {{ QuestionTypeMap[type] }}
                </div>
                <div class="mt-2 grid grid-cols-5 gap-2">
                  <Button
                    v-for="question in questions.get(type)"
                    :key="question.id"
                    :label="`${answerMap.get(question.id || '')?.index}`"
                    :severity="`${question.id === currentQuestion.id ? 'info' : answerMap.get(question.id || '')?.hasAnswer ? 'success' : 'secondary'} `"
                    raised
                    rounded
                    @click="selectQuestion(question)"
                  />
                </div>
              </template>
            </div>
          </template>
        </Card>
      </div>
    </div>

    <!-- 主答题区域 -->
    <div class="h-full flex-grow p-6">
      <div v-if="currentQuestion">
        <PreviewEditor :content="currentQuestion.content" />
        <template
          v-if="currentQuestion.type === QuestionTypeEnum.SINGLE_CHOICE"
        >
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
          v-else-if="currentQuestion.type === QuestionTypeEnum.MULTIPLE_CHOICE"
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
        <template
          v-else-if="currentQuestion.type === QuestionTypeEnum.GAP_FILLING"
        >
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
        <template v-else-if="currentQuestion.type === QuestionTypeEnum.JUDGE">
          <div class="mt-2 flex items-center">
            <div class="mr-3 text-xl">正确答案:</div>
            <Button
              :icon="judgeAnswer.answer ? 'pi pi-check' : 'pi pi-times'"
              :severity="judgeAnswer.answer ? 'success' : 'danger'"
              rounded
            />
          </div>
        </template>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { Button, Card, PreviewEditor } from '#/components';
import type { ExamInfoResult } from '#/api/examPageReleaseApi';
import { computed, onUnmounted, ref, watchEffect } from 'vue';
import {
  type QuestionAnswer,
  QuestionBank,
  QuestionTypeEnum,
  QuestionTypeMap,
} from '#/api/questionBankApi';
import { examPageApi } from '#/api/examPageApi';

const { examInfo } = defineProps<{
  examInfo: ExamInfoResult;
}>();

/**
 * 处理题目显示和答案
 */
const questions = ref<Map<QuestionTypeEnum, QuestionBank[]>>(new Map());
const answerMap = ref<Map<string, QuestionAnswer>>(new Map());
const currentQuestion = ref<QuestionBank>(new QuestionBank());
function selectQuestion(question: QuestionBank) {
  currentQuestion.value = question;
}

/**
 * 单选题json数据
 */
export interface SingleChoiceAnswer {
  /**
   * 正确答案
   * 选项的顺序 从0开始
   */
  answer?: number;

  /**
   * 选项列表
   */
  keys: {
    // 选项的值
    value: string;
  }[];
}

/**
 * 多选题json数据
 */
interface MultipleChoiceAnswer {
  answer: number[];
  keys: {
    // 选项的值
    value: string;
  }[];
}

/**
 * 判断题json数据
 */
export interface JudgeAnswer {
  answer: boolean;
}

/**
 * 填空题json数据
 */
export interface GapFillingAnswer {
  answer: {
    // 答案
    value: string;
  }[];
}

const singleChoiceAnswer = computed(
  () => JSON.parse(currentQuestion.value!!.answer) as SingleChoiceAnswer,
);

const multipleChoiceAnswer = computed(
  () => JSON.parse(currentQuestion.value!!.answer) as MultipleChoiceAnswer,
);

const gapFillingAnswer = computed(
  () => JSON.parse(currentQuestion.value!!.answer) as GapFillingAnswer,
);

const judgeAnswer = computed(
  () => JSON.parse(currentQuestion.value!!.answer) as JudgeAnswer,
);

/**
 * 处理倒计时
 */
const leftTime = ref(0);
let interval: ReturnType<typeof setInterval> | undefined;
const leftTimeText = computed(() => {
  if (leftTime.value <= 0) {
    return '00:00:00';
  }
  const seconds = leftTime.value;
  const hours = Math.floor(seconds / 3600); // 计算小时
  const minutes = Math.floor((seconds % 3600) / 60); // 计算分钟
  const secs = seconds % 60; // 剩余秒数

  // 格式化为两位数
  const pad = (num: number) => num.toString().padStart(2, '0');

  return `${pad(hours)}:${pad(minutes)}:${pad(secs)}`;
});

function calculateTimeDiff(endTime: string): number {
  const start = Date.now(); // 转为时间戳（毫秒）
  const end = new Date(endTime).getTime(); // 转为时间戳（毫秒）

  const differenceInMilliseconds = start - end; // 时间差（毫秒）
  return Math.floor(differenceInMilliseconds / 1000); // 转换为秒
}

onUnmounted(() => {
  if (interval) {
    clearInterval(interval);
    interval = undefined;
  }
});

watchEffect(() => {
  // 清理之前的定时器
  if (interval) {
    clearInterval(interval);
  }
  // 计算剩余时间
  leftTime.value =
    examInfo.limitedTime - calculateTimeDiff(examInfo.examStartTime!!);

  examPageApi.questionInfo(examInfo.examPageId).then((res) => {
    questions.value = new Map<string, QuestionBank[]>(
      Object.entries(res),
    ) as Map<QuestionTypeEnum, QuestionBank[]>;
    // 初始化答案map
    let index = 1;
    questions.value.forEach((v) => {
      if (!currentQuestion.value.id && v.length > 0) {
        currentQuestion.value.copy(v[0]!!);
      }
      v.forEach((item) => {
        answerMap.value.set(item.id!!, {
          hasAnswer: false,
          index: index++,
        });
      });
    });
  });

  // 定义定时器
  interval = setInterval(() => {
    leftTime.value--;
    if (leftTime.value <= 0) {
      clearInterval(interval!);
      interval = undefined;
    }
  }, 1000);
});
</script>
