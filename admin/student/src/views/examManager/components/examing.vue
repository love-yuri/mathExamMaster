<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-23 18:55:25
 * @LastEditTime: 2024-12-25 19:04:16
 * @Description: 
-->
<template>
  <div>
    {{ examInfo }}
    <div class="mt-4 text-[22px]">
      剩余时间:
      <span class="font-semibold text-red-500">
        {{ leftTimeText }}
      </span>
    </div>
  </div>
</template>
<script lang="ts" setup>
import type { ExamInfoResult } from '#/api/examPageReleaseApi';
import { computed, onUnmounted, ref, watchEffect } from 'vue';

const { examInfo } = defineProps<{
  examInfo: ExamInfoResult;
}>();

const leftTime = ref(0);
let interval: ReturnType<typeof setInterval> | undefined;

const leftTimeText = computed(() => {
  const seconds = leftTime.value;
  const hours = Math.floor(seconds / 3600); // 计算小时
  const minutes = Math.floor((seconds % 3600) / 60); // 计算分钟
  const secs = seconds % 60; // 剩余秒数

  // 格式化为两位数
  const pad = (num: number) => num.toString().padStart(2, '0');

  return `${pad(hours)}:${pad(minutes)}:${pad(secs)}`;
});

function calculateTimeDiff(endTime: string): number {
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
