<template>
  <div
    v-if="!isExam"
    class="flex h-full w-full items-center justify-center bg-gray-50 p-6"
  >
    <!-- 应用 .exam-card 的样式 -->
    <Card
      class="rounded-xl bg-white p-8 shadow-md transition-all duration-300 ease-in-out hover:-translate-y-1 hover:shadow-lg"
    >
      <template #content>
        <div v-if="examInfo" class="min-w-[800px] space-y-6">
          <!-- 标题 -->
          <div class="text-primary mb-6 text-center text-[32px] font-bold">
            {{ examInfo?.title }}
          </div>

          <Divider class="my-4" />

          <div class="grid grid-cols-2 gap-6">
            <div
              class="rounded-lg bg-gray-50 p-6 transition-all duration-300 ease-in-out hover:bg-gray-100 hover:shadow-md"
            >
              <div
                class="mb-4 flex items-center border-b border-dashed border-gray-200 py-2 last:mb-0 last:border-b-0"
              >
                <span
                  class="mr-3 text-lg font-semibold leading-7 text-gray-700"
                >
                  考试科目:
                </span>
                <span class="text-xl font-bold leading-7 text-gray-900">{{
                  examPageSubject
                }}</span>
              </div>
              <div
                class="mb-4 flex items-center border-b border-dashed border-gray-200 py-2 last:mb-0 last:border-b-0"
              >
                <span
                  class="mr-3 text-lg font-semibold leading-7 text-gray-700"
                >
                  考试难度:
                </span>
                <Rating
                  :model-value="examInfo?.difficulty"
                  :stars="9"
                  readonly
                  class="text-xl font-bold leading-7 text-gray-900"
                />
              </div>
              <div
                v-show="false"
                class="mb-4 flex items-center border-b border-dashed border-gray-200 py-2 last:mb-0 last:border-b-0"
              >
                <span
                  class="mr-3 text-lg font-semibold leading-7 text-gray-700"
                >
                  试卷类型:
                </span>
                <span class="text-xl font-bold leading-7 text-gray-900">{{
                  examPageType
                }}</span>
              </div>
              <div
                class="mb-4 flex items-center border-b border-dashed border-gray-200 py-2 last:mb-0 last:border-b-0"
              >
                <span
                  class="mr-3 text-lg font-semibold leading-7 text-gray-700"
                >
                  结束时间:
                </span>
                <span class="text-xl font-bold leading-7 text-gray-900">{{
                  examInfo?.endTime
                }}</span>
              </div>
            </div>

            <div
              class="rounded-lg bg-gray-50 p-6 transition-all duration-300 ease-in-out hover:bg-gray-100 hover:shadow-md"
            >
              <div
                class="mb-4 flex items-center border-b border-dashed border-gray-200 py-2 last:mb-0 last:border-b-0"
              >
                <span
                  class="mr-3 text-lg font-semibold leading-7 text-gray-700"
                >
                  试卷总分:
                </span>
                <span class="text-xl font-bold leading-7 text-gray-900">
                  {{ examInfo?.totalScore }} 分
                </span>
              </div>
              <div
                class="mb-4 flex items-center border-b border-dashed border-gray-200 py-2 last:mb-0 last:border-b-0"
              >
                <span
                  class="mr-3 text-lg font-semibold leading-7 text-gray-700"
                >
                  考试时长:
                </span>
                <span class="text-xl font-bold leading-7 text-gray-900">
                  {{ (examInfo?.limitedTime ?? 1) / 60 }} 分钟
                </span>
              </div>
            </div>
          </div>

          <Divider class="my-4" />

          <div class="flex flex-col items-center justify-center pt-4">
            <!-- 应用 .exam-start-btn 的样式 -->
            <FameraButton
              class="rounded-md px-6 py-2 text-lg transition-all duration-300 ease-in-out hover:scale-105"
              @click="startExam"
            >
              {{ examNotStart ? '开始考试' : '继续考试' }}
            </FameraButton>
            <div v-if="!examNotStart" class="mt-6 text-[22px]">
              剩余时间:
              <span class="font-semibold text-red-500">
                {{ leftTimeText }}
              </span>
            </div>
          </div>
        </div>
        <!-- 添加一个加载状态 -->
        <div v-else class="min-w-[800px] py-10 text-center text-gray-500">
          正在加载考试信息...
        </div>
      </template>
    </Card>
  </div>
  <Examing v-else :exam-info="examInfo!!" />
</template>
<script setup lang="ts">
import type { ExamInfoResult } from '@yuri/types';

import { computed, onUnmounted, ref, watchEffect } from 'vue'; // 引入 onMounted

import { examPageReleaseApi } from '@yuri/common';
import { Card, Divider, FameraButton, Rating } from '@yuri/components';
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

// 监控 isExam 并清理定时器 和 全屏监听器
watchEffect(() => {
  if (isExam.value && interval) {
    clearInterval(interval);
    interval = null;
  }
  // 如果考试结束，移除全屏变化监听器
  if (!isExam.value) {
    // removeFullscreenListener();
  }
});

// onMounted(forceFullscreen); // 移除或注释掉这行，因为全屏在 startExam 中触发

// 在组件卸载时清理定时器和全屏监听器
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

// 请求进入全屏的函数
async function requestExamFullscreen() {
  const element = document.documentElement; // 全屏整个页面
  try {
    if (element.requestFullscreen) {
      await element.requestFullscreen({ navigationUI: 'hide' });
    } else if ((element as any).webkitRequestFullscreen) {
      /* Safari */
      await (element as any).webkitRequestFullscreen({ navigationUI: 'hide' });
    } else if ((element as any).mozRequestFullScreen) {
      /* Firefox */
      await (element as any).mozRequestFullScreen({ navigationUI: 'hide' });
    } else if ((element as any).msRequestFullscreen) {
      /* IE/Edge */
      await (element as any).msRequestFullscreen();
    }
  } catch (error) {
    console.error('请求全屏失败:', error);
  }
}

function startExam() {
  examPageReleaseApi.startExam(examInfo.value!!.releaseId).then(async (_) => {
    isExam.value = true;
    requestExamFullscreen();
  });
}
</script>
<!-- <style scoped> 块已被移除 -->
