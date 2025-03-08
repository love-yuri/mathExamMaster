<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-03-07 09:05:38
 * @LastEditTime: 2025-03-08 21:42:36
 * @Description: 
-->
<template>
  <div class="h-full">
    <div v-if="!showReview" class="p-2">
      <div
        class="flex items-center justify-between gap-4 rounded-2xl bg-white p-4 shadow-md"
      >
        <Card
          v-for="stat in stats"
          :key="stat.label"
          class="flex-1 text-center shadow-md"
        >
          <template #content>
            <div class="text-sm text-gray-600">{{ stat.label }}</div>
            <div class="text-2xl font-semibold text-blue-600">
              {{ stat.value }}
            </div>
          </template>
        </Card>
      </div>
      <div v-if="scoreDetail">
        <ScoreDistribution :data="scoreDetail" />
        <QuestionDetail :data="scoreDetail.questionStatistics" />
      </div>
    </div>
    <div v-else class="flex h-full items-center justify-center">
      <Button
        class="h-32 w-64 !text-[30px]"
        label="前往阅卷"
        @click="
          router.push({
            name: 'examPageReleaseReviewing',
            params: {
              id: releaseId,
            },
          })
        "
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import type { ScoreDetailResult } from '@yuri/types';

import { computed, ref, watchEffect } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { userScoreApi } from '@yuri/common';
import { Button, Card } from '@yuri/components';

import QuestionDetail from './charts/questionDetail.vue';
import ScoreDistribution from './charts/scoreDistribution.vue';

const route = useRoute();
const router = useRouter();
const showReview = ref(false);
const releaseId = computed(() => route.params.id as string);
const scoreDetail = ref<ScoreDetailResult>();

watchEffect(async () => {
  try {
    await userScoreApi.scoreDetail(releaseId.value).then((res) => {
      scoreDetail.value = res;
    });
  } catch {
    showReview.value = true;
  }
});

const stats = computed(() => {
  if (!scoreDetail.value) return [];
  return [
    {
      label: '班级平均分',
      value: `${scoreDetail.value.averageScore.toFixed(2)}`,
    },
    {
      label: '班级中位分',
      value: `${scoreDetail.value.medianScore.toFixed(2)}`,
    },
    { label: '班级最低分', value: `${scoreDetail.value.minScore}` },
    { label: '班级最高分', value: `${scoreDetail.value.maxScore}` },
    { label: '试卷总分', value: `${scoreDetail.value.totalScore}` },
    {
      label: '及格率',
      value: `${(scoreDetail.value.passRate * 100).toFixed(1)}%`,
    },
  ];
});
</script>
