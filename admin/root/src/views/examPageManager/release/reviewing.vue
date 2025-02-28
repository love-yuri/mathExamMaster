<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-24 18:10:30
 * @LastEditTime: 2025-02-28 10:01:25
 * @Description: 
-->
<template>
  <div class="flex flex-row p-2">
    <div class="flex w-[200px] flex-shrink-0 flex-col rounded-sm border-2 p-2">
      <Button
        @click="getStudetDetail(student.relationId)"
        :severity="currentStudent === student.userId ? 'primary' : 'secondary'"
        class="mb-2 w-full"
        v-for="student in students"
        :label="student.nickname"
        :key="student.userId"
      />
    </div>
    <div class="flex-grow">
      <div
        class="mb-2 rounded-sm border border-gray-300 p-2"
        v-for="detail in currentUserScore.detail"
        :key="detail.questionId"
      >
        <component :is="getComponent(detail.type)" :detail="detail" />
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import type { StudentDetailResult } from '@yuri/types';

import { computed, ref, watchEffect } from 'vue';
import { useRoute } from 'vue-router';

import { examPageReleaseApi, userScoreApi } from '@yuri/common';
import { Button } from '@yuri/components';
import { QuestionTypeEnum, UserScore } from '@yuri/types';

import ShowGapFillingAnswer from './showQuestion/gapFilling.vue';
import ShowJudgeChoose from './showQuestion/judge.vue';
import ShowMultipleChoice from './showQuestion/multipleChoice.vue';
import ShowSingleChoice from './showQuestion/singleChoice.vue';
import ShowSubjectAnswer from './showQuestion/subjective.vue';

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

const route = useRoute();
const currentUserScore = ref(new UserScore());
const currentStudent = ref('');
const releaseId = computed(() => route.params.id as string);
const students = ref<StudentDetailResult[]>([]);

watchEffect(async () => {
  students.value = await examPageReleaseApi.studentDetail(releaseId.value);
});

function getStudetDetail(id: string) {
  userScoreApi.detail(id).then((res) => {
    currentUserScore.value = res;
  });
}
</script>
