<template>
  <div
    v-if="!isExam"
    class="flex h-full w-full items-center justify-center bg-gray-100 p-6"
  >
    <Card class="rounded-lg bg-white p-6 shadow-lg">
      <template #content>
        <div class="min-w-[800px] space-y-6">
          <!-- 标题 -->
          <div class="text-center text-[32px] font-bold text-gray-800">
            {{ examInfo?.title }}
          </div>

          <div class="grid grid-cols-2 gap-4">
            <div>
              <div class="info">
                <span class="title">考试科目: </span>
                <span class="content">{{ examPageSubject }}</span>
              </div>
              <div class="info">
                <span class="title">考试难度: </span>
                <Rating :model-value="examInfo?.difficulty" :stars="9" />
              </div>
              <div v-show="false" class="info">
                <span class="title">试卷类型: </span>
                <span class="content">{{ examPageType }}</span>
              </div>
              <div class="info">
                <span class="title">结束时间: </span>
                <span class="content">{{ examInfo?.endTime }}</span>
              </div>
            </div>

            <div>
              <div class="info">
                <span class="title">试卷总分: </span>
                <span class="content">{{ examInfo?.totalScore }} 分</span>
              </div>
              <div class="info">
                <span class="title">考试时长: </span>
                <span class="content">
                  {{ (examInfo?.limitedTime ?? 1) / 60 }} 分钟
                </span>
              </div>
            </div>
          </div>
          <div class="flex flex-col items-center justify-center">
            <FameraButton @click="startExam">
              {{
                // eslint-disable-next-line vue/eqeqeq
                examNotStart ? '开始考试' : '继续考试'
              }}
            </FameraButton>
            <div v-if="!examNotStart" class="mt-4 text-[22px]">
              剩余时间:
              <span class="font-semibold text-red-500">
                {{ leftTimeText }}
              </span>
            </div>
          </div>
        </div>
      </template>
    </Card>
  </div>
  <Examing v-else :exam-info="examInfo!!" />
</template>
<script setup lang="ts">
import type { ExamInfoResult } from '@yuri/types';

import { computed, onUnmounted, ref, watchEffect } from 'vue';

import { examPageReleaseApi } from '@yuri/common';
import { Card, FameraButton, Rating } from '@yuri/components';
import {
  ExamPageMap,
  ExamPageType,
  ExamPageUserRelationStatusType,
  SubjectType,
  SubjectTypeMap,
} from '@yuri/types';

import Examing from './components/examing.vue';

const { id } = defineProps<{
  id?: string;
}>();

const isExam = ref(false);
const leftTime = ref(0);
const examInfo = ref<ExamInfoResult>();
const examPageType = computed(
  () => ExamPageMap[examInfo.value?.type ?? ExamPageType.DEFULT],
);
const examPageSubject = computed(
  () => SubjectTypeMap[examInfo.value?.subject ?? SubjectType.HighMath],
);

const examNotStart = computed(() => {
  // eslint-disable-next-line eqeqeq
  return examInfo.value?.status == ExamPageUserRelationStatusType.NOT_START;
});

const leftTimeText = computed(() => {
  const seconds = leftTime.value;
  const hours = Math.floor(seconds / 3600); // 计算小时
  const minutes = Math.floor((seconds % 3600) / 60); // 计算分钟
  const secs = seconds % 60; // 剩余秒数

  // 格式化为两位数
  const pad = (num: number) => num.toString().padStart(2, '0');

  return `${pad(hours)}:${pad(minutes)}:${pad(secs)}`;
});

let interval: null | ReturnType<typeof setInterval> = null;

watchEffect(async () => {
  if (!id) {
    return;
  }

  // 获取考试信息
  examInfo.value = await examPageReleaseApi.examInfo(id);
  // 清理之前的定时器
  if (interval) {
    clearInterval(interval);
  }
  if (!examNotStart.value) {
    // 计算剩余时间
    leftTime.value =
      examInfo.value.limitedTime -
      calculateTimeDiff(examInfo.value!!.examStartTime!!);

    // 定义定时器
    interval = setInterval(() => {
      leftTime.value--;
      if (leftTime.value <= 0) {
        clearInterval(interval!);
        interval = null;
      }
    }, 1000);
  }
});

// 监控 isExam 并清理定时器
watchEffect(() => {
  if (isExam.value && interval) {
    clearInterval(interval);
    interval = null;
  }
});

// 在组件卸载时清理定时器
onUnmounted(() => {
  if (interval) {
    clearInterval(interval);
    interval = null;
  }
});

function calculateTimeDiff(endTime: string): number {
  const start = Date.now(); // 转为时间戳（毫秒）
  const end = new Date(endTime).getTime(); // 转为时间戳（毫秒）

  const differenceInMilliseconds = start - end; // 时间差（毫秒）
  return Math.floor(differenceInMilliseconds / 1000); // 转换为秒
}

function startExam() {
  examPageReleaseApi.startExam(examInfo.value!!.releaseId).then((_) => {
    isExam.value = true;
  });
}
</script>
<style lang="scss" scoped>
.info {
  display: flex;
  align-items: center;

  .title {
    --tw-text-opacity: 1;

    margin-right: 5px;
    font-size: 19px;
    font-weight: 500;
    line-height: 29px;
    color: rgb(55 65 81 / var(--tw-text-opacity));
  }

  .content {
    --tw-text-opacity: 1;

    font-size: 22px;
    font-weight: 700;
    line-height: 30px;
    color: rgb(17 24 39 / var(--tw-text-opacity));
  }
}
</style>
