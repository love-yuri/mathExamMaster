<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-24 18:10:30
 * @LastEditTime: 2025-03-03 16:33:47
 * @Description: 
-->
<template>
  <div class="flex h-[calc(100vh-120px)] flex-row p-2">
    <div
      class="flex w-[200px] flex-shrink-0 flex-col overflow-y-auto rounded-sm border-2 p-2"
    >
      <Button
        @click="getStudetDetail(student.relationId)"
        :severity="
          currentRelationId === student.relationId ? 'primary' : 'secondary'
        "
        class="mb-2 w-full"
        v-for="student in students"
        :label="student.nickname"
        :key="student.userId"
      />
    </div>
    <div class="flex-grow overflow-y-auto p-2">
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
  </div>
</template>
<script setup lang="ts">
import type { StudentDetailResult } from '@yuri/types';

import { computed, ref, watchEffect } from 'vue';
import { useRoute } from 'vue-router';

import { examPageReleaseApi, message, userScoreApi } from '@yuri/common';
import { Button, OverlayBadge } from '@yuri/components';
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
const currentRelationId = ref('');
const releaseId = computed(() => route.params.id as string);
const students = ref<StudentDetailResult[]>([]);

function update() {
  userScoreApi.update(currentUserScore.value).then((res) => {
    if (res) {
      message.default.success('更新成功');
    }
  });
}

watchEffect(async () => {
  students.value = await examPageReleaseApi.studentDetail(releaseId.value);
});

function getStudetDetail(id: string) {
  currentRelationId.value = id;
  userScoreApi.detail(id).then((res) => {
    currentUserScore.value = res;
  });
}
</script>
