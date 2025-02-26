<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-24 18:10:30
 * @LastEditTime: 2025-02-26 19:57:55
 * @Description: 
-->
<template>
  <div class="flex flex-row p-2">
    <div class="flex w-[200px] flex-shrink-0 flex-col rounded-sm border-2 p-2">
      <Button
        @click="getStudetDetail(student.relationId)"
        :severity="currentStudent == student.userId ? 'primary' : 'secondary'"
        class="mb-2 w-full"
        v-for="student in students"
        :label="student.nickname"
        :key="student.userId"
      />
    </div>
    <div class="flex-grow">
      <div
        class="mb-2 border rounded-sm border-gray-300 p-2"
        v-for="detail in currentUserScore.detail"
      >
        <template v-if="detail.type == QuestionTypeEnum.SINGLE_CHOICE">
          <ShowSingleChoice :detail="detail" />
        </template>
      </div>
    </div>
  </div>
</template>
<script setup lang="tsx">
import { GapFillingAnswer, JudgeAnswer, MultipleChoiceAnswer, QuestionTypeEnum, SingleChoiceAnswer, SubjectiveAnswer, UserScore, type StudentDetailResult, type UserScoreDetail } from '@yuri/types';
import { Button } from '@yuri/components';
import { computed, defineComponent, ref, watchEffect, type PropType } from 'vue';
import { useRoute } from 'vue-router';
import { examPageReleaseApi, userScoreApi } from '@yuri/common';
import ShowSingleChoice from './showQuestion/singleChoice.vue';

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
