<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-23 18:55:25
 * @LastEditTime: 2025-02-27 19:18:23
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
              @click="overExam"
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
              <template v-for="item in questions" :key="item.type">
                <div
                  class="mt-4 rounded-sm bg-gray-200 px-[5px] py-[4px] text-[22px] font-bold"
                >
                  {{ QuestionTypeMap[item.type] }}
                </div>
                <div class="mt-2 grid grid-cols-5 gap-2">
                  <Button
                    v-for="question in item.infos"
                    :key="question.id"
                    :label="`${question.index}`"
                    :severity="`${question.id === currentQuestionInfo?.id ? 'info' : question?.userAnswer.hasAnswer ? 'success' : 'secondary'} `"
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
      <component
        @update-answer="updateAnswer"
        :is="currentCommponent"
        v-model:question="currentQuestionInfo"
      />
    </div>
    <ConfirmDialog group="overExam">
      <template #container="{ message, acceptCallback, rejectCallback }">
        <div
          class="bg-surface-0 dark:bg-surface-900 flex flex-col items-center rounded p-8"
        >
          <p class="mb-0">{{ message.message }}</p>
          <div class="mt-6 flex items-center gap-2">
            <Button
              class="w-32"
              icon="pi pi-trash"
              label="确定"
              severity="danger"
              @click="acceptCallback"
            />
            <Button
              class="w-32"
              icon="pi pi-times"
              label="取消"
              outlined
              severity="secondary"
              @click="rejectCallback"
            />
          </div>
        </div>
      </template>
    </ConfirmDialog>
  </div>
</template>
<script lang="ts" setup>
import type {
  ExamInfoResult,
  QuestionInfo,
  QuestionInfoResult,
  UserAnswer,
} from '@yuri/types';

import {
  computed,
  markRaw,
  nextTick,
  onUnmounted,
  ref,
  watchEffect,
} from 'vue';

import { debounce, examPageApi, message } from '@yuri/common';
import { Button, Card, ConfirmDialog } from '@yuri/components';
import { QuestionTypeEnum, QuestionTypeMap } from '@yuri/types';
import { useConfirm } from 'primevue/useconfirm';

import { router } from '#/router';

import GapFilling from './answerQuestion/gapFilling.vue';
import Judge from './answerQuestion/judge.vue';
import MultipleChoice from './answerQuestion/multipleChoice.vue';
import SingleChoice from './answerQuestion/singleChoice.vue';
import Subjective from './answerQuestion/subjective.vue';

const { examInfo } = defineProps<{
  examInfo: ExamInfoResult;
}>();

/**
 * 处理题目显示和答案
 */
const currentCommponent = computed(() => {
  switch (currentQuestionInfo.value?.type) {
    case QuestionTypeEnum.GAP_FILLING: {
      return markRaw(GapFilling);
    }
    case QuestionTypeEnum.JUDGE: {
      return markRaw(Judge);
    }
    case QuestionTypeEnum.MULTIPLE_CHOICE: {
      return markRaw(MultipleChoice);
    }
    case QuestionTypeEnum.SINGLE_CHOICE: {
      return markRaw(SingleChoice);
    }
    case QuestionTypeEnum.SUBJECTIVE: {
      return markRaw(Subjective);
    }
  }
  return markRaw(SingleChoice);
});

const questions = ref<QuestionInfoResult[]>([]);
const currentQuestionInfo = ref<QuestionInfo>();
const showEditor = ref(false);
async function selectQuestion(question: QuestionInfo) {
  // 先卸载组件防止切换时数据冲突报错
  if (question.type === QuestionTypeEnum.SUBJECTIVE) {
    showEditor.value = false;
    await nextTick(); // 等待dom渲染完成
    showEditor.value = true;
    await nextTick(); // 等待dom渲染完成
  }
  currentQuestionInfo.value = question;
}

/**
 * 更新用户答案
 */
const updateAnswer = debounce(() => {
  const param: UserAnswer[] = questions.value.flatMap((item) => {
    return item.infos.map((info) => info.userAnswer);
  });
  examPageApi
    .updateUserAnswer({
      relationId: examInfo.relationId,
      userAnswers: param,
    })
    .then(() => {
      message.default.success('保存成功');
    });
}, 2000);

/**
 * 交卷
 */
const confirm = useConfirm();
function overExam() {
  confirm.require({
    accept: () => {
      examPageApi.overExam(examInfo.relationId).then(() => {
        message.default.success('交卷成功!!');
        router.push({
          name: 'overExam',
        });
      });
    },
    group: 'overExam',
    message: '是否要提前交卷?',
  });
}

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

function calculateTimeDiff(endTime?: string): number {
  if (!endTime || endTime === null) {
    return 0;
  }
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

  examPageApi
    .questionInfo({
      examPageId: examInfo.examPageId,
      relationId: examInfo.relationId,
    })
    .then((res) => {
      questions.value = res;
      if (res.length > 0 && res[0]!!.infos.length > 0) {
        currentQuestionInfo.value = res[0]?.infos[0]!!;
      }
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
