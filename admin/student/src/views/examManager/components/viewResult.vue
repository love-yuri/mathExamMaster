<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-03-15 10:00:00
 * @LastEditTime: 2025-03-15 10:00:00
 * @Description: 学生考后题目预览组件
-->
<template>
  <div class="flex h-[calc(100vh-120px)] flex-col bg-gray-50 p-2">
    <!-- 顶部信息栏 -->
    <div
      class="flex items-center justify-between rounded-lg bg-white px-4 py-3 shadow-md"
    >
      <!-- 分数信息 -->
      <div class="text-lg font-medium">
        总得分:
        <span class="font-bold text-blue-600">{{ totalScore }}</span>
        <span class="mx-1">/</span>
        <span class="text-gray-600">{{ currentUserScore.totalScore }}</span>
      </div>
    </div>

    <!-- 题目导航栏 -->
    <div
      class="question-nav my-2 flex items-center gap-1 overflow-x-auto rounded-lg bg-white px-4 py-2 shadow-sm"
    >
      <span class="mr-2 whitespace-nowrap text-gray-600">题目导航:</span>
      <Button
        v-for="(detail, index) in currentUserScore.detail"
        :key="detail.questionId"
        :label="(index + 1).toString()"
        :severity="detail.hasSetScore ? 'success' : 'secondary'"
        :outlined="currentQuestionIndex !== index"
        :raised="currentQuestionIndex === index"
        size="small"
        class="transition-all duration-200"
        @click="scrollToQuestion(index)"
      />
    </div>

    <!-- 题目列表容器 -->
    <div
      class="questions-container flex-grow overflow-y-auto p-1"
      ref="questionsContainerRef"
    >
      <div
        class="question-card mb-3 rounded-lg border bg-white p-4 transition-all duration-300"
        v-for="(detail, index) in currentUserScore.detail"
        :key="detail.questionId"
        :id="`question-${index}`"
        :class="{
          'border-blue-500 bg-blue-50 shadow-md':
            currentQuestionIndex === index,
          'hover:border-gray-300 hover:shadow-sm':
            currentQuestionIndex !== index,
        }"
      >
        <!-- 题目标题和分数 -->
        <div class="mb-2 flex items-center">
          <OverlayBadge
            :value="index + 1"
            :severity="detail.hasSetScore ? 'success' : 'secondary'"
            class="mr-4"
          />
          <h3 class="text-lg font-medium">第 {{ index + 1 }} 题</h3>
          <div class="ml-auto text-sm">
            <span class="text-gray-500">得分: </span>
            <span class="font-bold text-blue-500">{{ detail.score }}</span>
          </div>
        </div>

        <!-- 单选题 -->
        <div v-if="detail.type === QuestionTypeEnum.SINGLE_CHOICE">
          <div class="flex flex-row items-center justify-between">
            <div class="flex">
              <Tag value="单选" />
              <Button
                v-for="(_, optIndex) in getSingleChoiceAnswer(detail).options"
                :key="optIndex"
                :label="`${String.fromCharCode(65 + optIndex)}`"
                :severity="getSingleChoiceSeverity(detail, optIndex)"
                class="mx-1"
              />
            </div>
            <SplitButton
              class="ml-2 flex-shrink-0"
              icon="pi pi-eye"
              label="预览题目"
              raised
              severity="info"
              @click="showQuestion(detail.questionId)"
              :model="[
                {
                  label: '查看描述',
                  icon: 'pi pi-info-circle',
                  command: () => showDescription(detail.questionId),
                },
              ]"
            />
          </div>
        </div>

        <!-- 多选题 -->
        <div v-else-if="detail.type === QuestionTypeEnum.MULTIPLE_CHOICE">
          <div class="flex flex-row items-center justify-between">
            <div class="flex">
              <Tag value="多选" />
              <Button
                v-for="(_, optIndex) in getMultipleChoiceAnswer(detail).options"
                :key="optIndex"
                :label="`${String.fromCharCode(65 + optIndex)}`"
                :severity="getMultipleChoiceSeverity(detail, optIndex)"
                class="mx-1"
              />
            </div>
            <SplitButton
              class="ml-2 flex-shrink-0"
              icon="pi pi-eye"
              label="预览题目"
              raised
              severity="info"
              @click="showQuestion(detail.questionId)"
              :model="[
                {
                  label: '查看描述',
                  icon: 'pi pi-info-circle',
                  command: () => showDescription(detail.questionId),
                },
              ]"
            />
          </div>
        </div>

        <!-- 判断题 -->
        <div v-else-if="detail.type === QuestionTypeEnum.JUDGE">
          <div class="flex flex-row items-center justify-between">
            <div class="flex">
              <Tag value="判断" class="mr-1" />
              <Button
                :severity="getJudgeSeverity(detail, true)"
                icon="pi pi-check"
                rounded
              />
              <Button
                :severity="getJudgeSeverity(detail, false)"
                class="ml-2"
                icon="pi pi-times"
                rounded
              />
            </div>
            <SplitButton
              class="ml-2 flex-shrink-0"
              icon="pi pi-eye"
              label="预览题目"
              raised
              severity="info"
              @click="showQuestion(detail.questionId)"
              :model="[
                {
                  label: '查看描述',
                  icon: 'pi pi-info-circle',
                  command: () => showDescription(detail.questionId),
                },
              ]"
            />
          </div>
        </div>

        <!-- 填空题 -->
        <div v-else-if="detail.type === QuestionTypeEnum.GAP_FILLING">
          <div class="flex flex-row items-center justify-between">
            <div class="flxe-grow flex">
              <Tag value="填空" />
              <div class="flex flex-grow flex-col p-2">
                <div
                  v-for="(item, gapIndex) in getGapFillingAnswer(detail).answer"
                  :key="gapIndex"
                  class="my-1 flex flex-grow items-center text-[20px]"
                >
                  <span class="mr-2 flex-shrink-0">
                    第{{ gapIndex + 1 }}空:
                  </span>
                  <span class="mx-2 flex-shrink-0">我的答案: </span>
                  <InputText
                    readonly
                    :value="getUserGapFillingAnswer(detail).answer[gapIndex]"
                    class="mx-2"
                  />
                  <span class="mx-2 flex-shrink-0">正确答案: </span>
                  <InputText readonly :value="item" />
                </div>
              </div>
            </div>
            <SplitButton
              class="ml-2 flex-shrink-0"
              icon="pi pi-eye"
              label="预览题目"
              raised
              severity="info"
              @click="showQuestion(detail.questionId)"
              :model="[
                {
                  label: '查看描述',
                  icon: 'pi pi-info-circle',
                  command: () => showDescription(detail.questionId),
                },
              ]"
            />
          </div>
        </div>

        <!-- 主观题 -->
        <div v-else-if="detail.type === QuestionTypeEnum.SUBJECTIVE">
          <div class="flex justify-between">
            <div class="flex items-center">
              <SplitButton
                class="ml-2 mr-2 flex-shrink-0"
                icon="pi pi-eye"
                label="预览题目"
                raised
                severity="info"
                @click="showQuestion(detail.questionId)"
                :model="[
                  {
                    label: '查看描述',
                    icon: 'pi pi-info-circle',
                    command: () => showDescription(detail.questionId),
                  },
                ]"
              />
            </div>
            <div class="flex items-center">
              得分:
              <span class="ml-1 text-[24px] font-bold text-blue-500">{{
                detail.score
              }}</span>
            </div>
          </div>

          <div class="mt-2 h-[1px] bg-gray-600"></div>
          <div class="flex items-center">
            <WangEditor
              :hide-toolbar-config="true"
              :readonly="true"
              :content="getSubjectiveAnswer(detail).answer"
              placeholder="答案为空..."
            />
          </div>
        </div>
      </div>
    </div>
    <Preview ref="previewRef" />
    <ShowDescription ref="descriptionRef" />
  </div>
</template>

<script setup lang="ts">
import type {
  GapFillingAw,
  JudgeAw,
  MultipleChoiceAw,
  SingleChoiceAw,
  SubjectiveAw,
  UserScoreDetail,
} from '@yuri/types';

import { computed, ref, unref, watchEffect } from 'vue';
import { useRoute } from 'vue-router';

import {
  areArraysEqual,
  message,
  questionBankApi,
  userScoreApi,
} from '@yuri/common';
import {
  Button,
  InputText,
  OverlayBadge,
  SplitButton,
  Tag,
  WangEditor,
} from '@yuri/components';
import { QuestionTypeEnum, UserScore } from '@yuri/types';

import Preview from './preview.vue';
import ShowDescription from './showDescription.vue';

const route = useRoute();
const currentUserScore = ref(new UserScore());
const relationId = computed(() => route.params.id as string);

const previewRef = ref();
const descriptionRef = ref();
const questionsContainerRef = ref();
const currentQuestionIndex = ref(-1);

watchEffect(() => {
  if (!relationId.value) {
    return;
  }
  userScoreApi.detail(relationId.value).then((res) => {
    currentUserScore.value.copy(res);
  });
});

const totalScore = computed(() => {
  let score = 0;
  currentUserScore.value.detail.forEach((detail) => {
    score += detail.score;
  });
  return score;
});

// 单选题处理
function getSingleChoiceAnswer(detail: UserScoreDetail) {
  return detail.questionAnswer as SingleChoiceAw;
}

function getUserSingleChoiceAnswer(detail: UserScoreDetail) {
  return detail.userAnswer.questionAnswer as unknown as SingleChoiceAw;
}

function getSingleChoiceSeverity(detail: UserScoreDetail, index: number) {
  const questionAnswer = getSingleChoiceAnswer(detail);
  const userAnswer = getUserSingleChoiceAnswer(detail);

  if (userAnswer.answer === questionAnswer.answer) {
    if (index === questionAnswer.answer) {
      return 'success';
    }
    return 'secondary';
  } else {
    if (index === questionAnswer.answer) {
      return userAnswer.answer ? 'success' : 'danger';
    }
    return index === userAnswer.answer ? 'danger' : 'secondary';
  }
}

// 多选题处理
function getMultipleChoiceAnswer(detail: UserScoreDetail) {
  return detail.questionAnswer as MultipleChoiceAw;
}

function getUserMultipleChoiceAnswer(detail: UserScoreDetail) {
  return detail.userAnswer.questionAnswer as unknown as MultipleChoiceAw;
}

function getMultipleChoiceSeverity(detail: UserScoreDetail, index: number) {
  const questionAnswer = getMultipleChoiceAnswer(detail);
  const userAnswer = getUserMultipleChoiceAnswer(detail);
  const isTrueAnswer = areArraysEqual(userAnswer.answer, questionAnswer.answer);

  if (isTrueAnswer) {
    if (questionAnswer.answer.includes(index)) {
      return 'success';
    }
    return 'secondary';
  } else {
    if (questionAnswer.answer.includes(index)) {
      return userAnswer.answer.length > 0 ? 'success' : 'danger';
    }
    return userAnswer.answer.includes(index) ? 'danger' : 'secondary';
  }
}

// 判断题处理
function getJudgeAnswer(detail: UserScoreDetail) {
  return detail.questionAnswer as JudgeAw;
}

function getUserJudgeAnswer(detail: UserScoreDetail) {
  return detail.userAnswer.questionAnswer as unknown as JudgeAw;
}

function getJudgeSeverity(detail: UserScoreDetail, value: boolean) {
  const questionAnswer = getJudgeAnswer(detail);
  const userAnswer = getUserJudgeAnswer(detail);

  if (userAnswer.answer === questionAnswer.answer) {
    if (value === questionAnswer.answer) {
      return 'success';
    }
    return 'secondary';
  } else {
    if (value === questionAnswer.answer) {
      return userAnswer.answer ? 'success' : 'danger';
    }
    return value === userAnswer.answer ? 'danger' : 'secondary';
  }
}

// 填空题处理
function getGapFillingAnswer(detail: UserScoreDetail) {
  return detail.questionAnswer as GapFillingAw;
}

function getUserGapFillingAnswer(detail: UserScoreDetail) {
  return detail.userAnswer.questionAnswer as unknown as GapFillingAw;
}

// 主观题处理
function getSubjectiveAnswer(detail: UserScoreDetail) {
  return detail.userAnswer.questionAnswer as unknown as SubjectiveAw;
}

// 预览题目
function showQuestion(id: string) {
  questionBankApi.get(id).then((res) => {
    unref(previewRef)?.open(res);
  });
}

// 显示题目描述
function showDescription(id: string) {
  questionBankApi.get(id).then((res) => {
    if (res.description) {
      unref(descriptionRef)?.open(res.description);
    } else {
      message.default.info('该题目没有描述');
      unref(descriptionRef)?.open(res.description);
    }
  });
}

// 滚动到指定题目
function scrollToQuestion(index: number) {
  currentQuestionIndex.value = index;

  setTimeout(() => {
    const element = document.querySelector(`#question-${index}`);
    if (element) {
      // 计算元素位置，使其滚动到容器中间位置
      const container = questionsContainerRef.value;
      if (container) {
        const containerRect = container.getBoundingClientRect();
        const elementRect = element.getBoundingClientRect();

        // 计算滚动位置，使元素位于容器中间
        const scrollTop =
          elementRect.top -
          containerRect.top +
          container.scrollTop -
          containerRect.height / 2 +
          elementRect.height / 2;

        container.scrollTo({
          behavior: 'smooth',
          top: scrollTop,
        });
      }
    }
  }, 50);
}
</script>

<style scoped>
.question-container {
  width: 100%;
}

.questions-container::-webkit-scrollbar {
  width: 8px;
}

.questions-container::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

.questions-container::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 4px;
}

.questions-container::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}

.question-nav::-webkit-scrollbar {
  height: 4px;
}

.question-nav::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.question-nav::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 4px;
}
</style>
