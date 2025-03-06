<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-24 18:10:30
 * @LastEditTime: 2025-03-05 16:28:59
 * @Description: 
-->
<template>
  <div class="flex h-[calc(100vh-120px)] flex-row p-2">
    <div
      class="flex w-[200px] flex-shrink-0 flex-col overflow-y-auto rounded-sm border-2 p-2"
    >
      <div class="mb-3 flex flex-col justify-between">
        <Button label="未阅卷" raised severity="secondary" />
        <Button label="已阅卷" raised severity="success" class="my-1" />
        <Button label="当前试卷" raised severity="info" />
      </div>
      <div class="mt-2 h-[1px] bg-gray-600"></div>
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
        class="mb-2 w-full"
        :label="student.nickname"
      />
    </div>
    <div class="flex-grow overflow-y-auto p-2">
      <div
        v-if="currentUserScore.id"
        class="flex items-center justify-between rounded-lg bg-white px-4 py-1 shadow-md"
      >
        <!-- 评分信息 -->
        <div class="text-lg font-medium">
          当前总分:
          <span class="text-blue-600">{{ totalScorre }}</span>
          试卷总分:
          <span class="text-gray-600">{{ currentUserScore.totalScore }}</span>
        </div>

        <!-- 操作按钮 -->
        <Button
          v-if="!currentUserScore.hasGrading"
          label="结束阅卷"
          class="p-button-danger px-4 py-2"
          @click="finishGrading"
        />
      </div>
      <div class="mb-2 flex p-2">
        <Button
          v-for="(detail, index) in currentUserScore.detail"
          :key="detail.questionId"
          :label="(index + 1).toString()"
          :severity="detail.hasSetScore ? 'success' : 'secondary'"
          class="mx-1"
        />
      </div>
      <div class="p-1">
        <div
          class="mb-2 rounded-sm p-2"
          v-for="(detail, index) in currentUserScore.detail"
          :key="detail.questionId"
        >
          <OverlayBadge
            :value="index + 1"
            :severity="detail.hasSetScore ? 'success' : 'secondary'"
          >
            <component
              @set-score="update"
              :is="getComponent(detail.type)"
              v-model:detail="currentUserScore.detail[index]!!"
            />
          </OverlayBadge>
        </div>
      </div>
    </div>
    <DefaultConfirmDialog group="finishGrading" />
  </div>
</template>
<script setup lang="ts">
import type { StudentDetailResult } from '@yuri/types';

import { computed, ref, watchEffect } from 'vue';
import { useRoute } from 'vue-router';

import { examPageReleaseApi, message, userScoreApi } from '@yuri/common';
import { Button, DefaultConfirmDialog, OverlayBadge } from '@yuri/components';
import { QuestionTypeEnum, UserScore } from '@yuri/types';
import { useConfirm } from 'primevue/useconfirm';

import ShowGapFillingAnswer from './showQuestion/gapFilling.vue';
import ShowJudgeChoose from './showQuestion/judge.vue';
import ShowMultipleChoice from './showQuestion/multipleChoice.vue';
import ShowSingleChoice from './showQuestion/singleChoice.vue';
import ShowSubjectAnswer from './showQuestion/subjective.vue';

const route = useRoute();
const currentUserScore = ref(new UserScore());
const currentStudent = ref<StudentDetailResult>();
const releaseId = computed(() => route.params.id as string);
const students = ref<StudentDetailResult[]>([]);

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

function update() {
  userScoreApi.update(currentUserScore.value).then((res) => {
    if (res) {
      message.default.success('更新成功');
    }
  });
}

/**
 * 结束评分
 */
const confirm = useConfirm();
function finishGrading() {
  confirm.require({
    accept: () => {
      currentUserScore.value.hasGrading = true;
      userScoreApi.update(currentUserScore.value).then((res) => {
        if (res) {
          message.default.success('结束阅卷成功');
          currentStudent.value!!.hasGrading = true;
        }
      });
    },
    group: 'finishGrading',
    message: '是否结束阅卷?',
  });
}

watchEffect(async () => {
  students.value = await examPageReleaseApi.studentDetail(releaseId.value);
});

function getStudetDetail(student: StudentDetailResult) {
  currentStudent.value = student;
  userScoreApi.detail(currentStudent.value?.relationId!!).then((res) => {
    currentUserScore.value = res;
  });
}
</script>
