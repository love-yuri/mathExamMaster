<template>
  <div
    class="reviewing-container flex h-[calc(100vh-120px)] flex-row gap-4 p-3"
  >
    <!-- 左侧学生列表面板 -->
    <div
      class="student-panel flex w-[240px] flex-shrink-0 flex-col overflow-hidden rounded-lg border bg-white shadow-md"
    >
      <div class="border-b bg-gray-50 p-3">
        <h2 class="mb-2 text-lg font-medium text-gray-800">学生列表</h2>
        <div class="flex flex-col gap-2">
          <Button
            label="未阅卷"
            icon="pi pi-times-circle"
            raised
            severity="secondary"
            class="w-full justify-start"
          />
          <Button
            label="已阅卷"
            icon="pi pi-check-circle"
            raised
            severity="success"
            class="w-full justify-start"
          />
          <Button
            label="当前试卷"
            icon="pi pi-file"
            raised
            severity="info"
            class="w-full justify-start"
          />
        </div>
      </div>
      <div class="flex-grow overflow-y-auto p-2">
        <div
          v-if="students.length === 0"
          class="flex h-full flex-col items-center justify-center text-gray-500"
        >
          <i class="pi pi-spin pi-spinner mb-2 text-2xl"></i>
          <span>加载中...</span>
        </div>
        <Button
          v-for="student in students"
          :key="student.userId"
          @click="getStudetDetail(student)"
          :severity="
            currentStudent?.userId === student.userId
              ? 'info'
              : student.hasGrading
                ? 'success'
                : 'secondary'
          "
          :icon="student.hasGrading ? 'pi pi-check-circle' : 'pi pi-user'"
          class="mb-2 w-full justify-start transition-all duration-200 hover:shadow-sm"
          :class="{ 'shadow-md': currentStudent?.userId === student.userId }"
          :label="student.nickname"
        />
      </div>
    </div>

    <!-- 右侧评分区域 -->
    <div
      class="grading-panel flex flex-grow flex-col overflow-hidden rounded-lg border bg-white shadow-md"
    >
      <!-- 无学生选中时的提示 -->
      <div
        v-if="!currentUserScore.id"
        class="flex h-full flex-col items-center justify-center text-gray-500"
      >
        <i class="pi pi-file-edit mb-4 text-6xl opacity-50"></i>
        <p class="text-xl">请从左侧选择一名学生开始阅卷</p>
      </div>

      <template v-else>
        <!-- 顶部评分信息 -->
        <div class="sticky top-0 z-20 border-b bg-white shadow-sm">
          <div class="flex items-center justify-between px-6 py-3">
            <div class="flex items-center gap-4">
              <div class="text-lg">
                <span class="font-medium">学生: </span>
                <span class="font-bold text-blue-600">{{
                  currentStudent?.nickname
                }}</span>
              </div>
              <div class="text-lg">
                <span class="font-medium">当前总分: </span>
                <span class="font-bold text-blue-600">{{ totalScorre }}</span>
                <span class="mx-1">/</span>
                <span class="text-gray-600">{{
                  currentUserScore.totalScore
                }}</span>
              </div>
              <div class="text-sm text-gray-500">
                <i class="pi pi-clock mr-1"></i>
                <span>
                  状态:
                  {{ currentStudent?.hasGrading ? '已阅卷完成' : '阅卷中' }}
                </span>
              </div>
            </div>

            <Button
              v-if="!currentStudent?.hasGrading"
              label="结束阅卷"
              icon="pi pi-check"
              severity="danger"
              class="px-4 py-2"
              @click="finishGrading"
            />
          </div>

          <!-- 题目导航 -->
          <div
            class="question-nav flex items-center gap-1 overflow-x-auto bg-gray-50 px-6 py-2"
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
        </div>

        <!-- 题目列表 -->
        <div
          class="questions-container flex-grow overflow-y-auto p-4"
          ref="questionsContainerRef"
        >
          <div
            class="question-card mb-4 rounded-lg border p-4 transition-all duration-300"
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
            <div class="mb-2 flex items-center">
              <OverlayBadge
                :value="index + 1"
                :severity="detail.hasSetScore ? 'success' : 'secondary'"
                class="mr-4"
              />
              <h3 class="text-lg font-medium">第 {{ index + 1 }} 题</h3>
              <div class="ml-auto text-sm">
                <span class="text-gray-500">得分: </span>
                <span
                  :class="
                    detail.hasSetScore
                      ? 'font-bold text-green-600'
                      : 'text-gray-400'
                  "
                >
                  {{ detail.score }} / {{ detail.totalScore }}
                </span>
              </div>
            </div>

            <component
              @set-score="update"
              :is="getComponent(detail.type)"
              v-model:detail="currentUserScore.detail[index]!!"
            />
          </div>
        </div>
      </template>
    </div>

    <ConfirmDialog group="showScoreDetail" />
    <DefaultConfirmDialog group="finishGrading" cancle-label="结束" />
  </div>
</template>

<script setup lang="ts">
import type { StudentDetailResult } from '@yuri/types';

import { computed, ref, watchEffect } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { examPageReleaseApi, message, userScoreApi } from '@yuri/common';
import {
  Button,
  ConfirmDialog,
  DefaultConfirmDialog,
  OverlayBadge,
} from '@yuri/components';
import { QuestionTypeEnum, UserScore } from '@yuri/types';
import { useConfirm } from 'primevue/useconfirm';

import ShowGapFillingAnswer from './showQuestion/gapFilling.vue';
import ShowJudgeChoose from './showQuestion/judge.vue';
import ShowMultipleChoice from './showQuestion/multipleChoice.vue';
import ShowSingleChoice from './showQuestion/singleChoice.vue';
import ShowSubjectAnswer from './showQuestion/subjective.vue';

const route = useRoute();
const router = useRouter();
const currentUserScore = ref(new UserScore());
const currentStudent = ref<StudentDetailResult>();
const releaseId = computed(() => route.params.id as string);
const students = ref<StudentDetailResult[]>([]);
const questionsContainerRef = ref();
const currentQuestionIndex = ref(-1);

const totalScorre = computed(() => {
  let score = 0;
  currentUserScore.value.detail.forEach((detail) => {
    score += detail.score;
  });
  return score;
});

function getComponent(type: QuestionTypeEnum) {
  switch (type) {
    case QuestionTypeEnum.GAP_FILLING: {
      return ShowGapFillingAnswer;
    }
    case QuestionTypeEnum.JUDGE: {
      return ShowJudgeChoose;
    }
    case QuestionTypeEnum.MULTIPLE_CHOICE: {
      return ShowMultipleChoice;
    }
    case QuestionTypeEnum.SINGLE_CHOICE: {
      return ShowSingleChoice;
    }
    case QuestionTypeEnum.SUBJECTIVE: {
      return ShowSubjectAnswer;
    }
    default: {
      return null;
    }
  }
}

/**
 * 检查是否已经阅卷结束，
 * 通知可以查看分数详情
 */
function checkShowScoreDetail() {
  if (students.value.some((stu) => !stu.hasGrading)) {
    return;
  }
  confirm.require({
    accept: () => {
      router.push({
        name: 'examPageReleaseScoreDetail',
        params: {
          id: releaseId.value,
        },
      });
    },
    acceptProps: {
      label: '前往',
    },
    group: 'showScoreDetail',
    header: '阅卷结束',
    message: '所有学生阅卷已完成，是否立即查看得分详情？',
    rejectProps: {
      label: '稍后查看',
      outlined: true,
      severity: 'secondary',
    },
  });
}

function update() {
  if (currentStudent.value?.hasGrading) {
    message.default.error('阅卷已结束，无法修改分数！');
    return;
  }
  userScoreApi.update(currentUserScore.value).then((res) => {
    if (res) {
      message.default.success('分数更新成功');
    }
  });
}

/**
 * 结束评分
 */
const confirm = useConfirm();

/**
 * 结束阅卷
 */
function finishGrading() {
  confirm.require({
    accept: () => {
      userScoreApi
        .reviewingCompleted(currentStudent.value!!.relationId)
        .then((res) => {
          if (res) {
            message.default.success('阅卷已完成');
            currentStudent.value!!.hasGrading = true;
            checkShowScoreDetail();
          }
        });
    },
    group: 'finishGrading',
    message: '确定要结束当前学生的阅卷吗？结束后将无法修改分数。',
  });
}

watchEffect(async () => {
  students.value = await examPageReleaseApi.studentDetail(releaseId.value);
});

function getStudetDetail(student: StudentDetailResult) {
  currentStudent.value = student;
  userScoreApi.detail(currentStudent.value?.relationId!!).then((res) => {
    currentUserScore.value.copy(res);
    // 重置当前选中题目
    currentQuestionIndex.value = -1;
  });
}

/**
 * 滚动到指定题目
 */
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
.reviewing-container {
  background-color: #f5f7fa;
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
