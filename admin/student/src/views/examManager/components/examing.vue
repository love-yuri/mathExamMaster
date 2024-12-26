<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-23 18:55:25
 * @LastEditTime: 2024-12-26 18:56:29
 * @Description: 
-->
<template>
  <div class="flex h-full">
    <!-- 左侧导航栏 -->
    <div class="h-full w-[300px] flex-shrink-0 border-r-2 p-4">
      <div>
        <Card>
          <template #content>
            <div class="text-[22px]">
              剩余时间:
              <span class="font-semibold text-red-500">
                {{ leftTimeText }}
              </span>
            </div>
            <Button
              class="mt-2 w-full"
              icon="pi pi-times"
              label="交卷"
              severity="danger"
            />
          </template>
        </Card>
        <Card class="mt-2">
          <template #content>
            <div>
              <div class="mb-3 flex justify-between">
                <Button label="未完成" raised rounded severity="secondary" />
                <Button label="已完成" raised rounded severity="success" />
                <Button label="当前" raised rounded severity="info" />
              </div>
              <div
                class="rounded-sm bg-gray-200 px-[5px] py-[4px] text-[22px] font-bold"
              >
                选择题
              </div>
              <div class="mt-2 grid grid-cols-5 gap-2">
                <Button label="1" raised rounded severity="secondary" />
                <Button label="2" raised rounded severity="success" />
                <Button label="3" raised rounded severity="info" />
                <Button label="3" raised rounded severity="info" />
                <Button label="3" raised rounded severity="info" />
                <Button label="3" raised rounded severity="info" />
                <Button label="3" raised rounded severity="info" />
              </div>
            </div>
          </template>
        </Card>
      </div>
    </div>

    <!-- 主答题区域 -->
    <div class="h-full flex-grow p-6"></div>
  </div>
</template>
<script lang="ts" setup>
import { Button, Card } from '#/components';
import type { ExamInfoResult } from '#/api/examPageReleaseApi';
import { computed, onUnmounted, ref, watchEffect } from 'vue';

const { examInfo } = defineProps<{
  examInfo: ExamInfoResult;
}>();

const leftTime = ref(0);
let interval: ReturnType<typeof setInterval> | undefined;

const leftTimeText = computed(() => {
  if (leftTime.value <= 0) {
    return '00:00:00';
  }
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
