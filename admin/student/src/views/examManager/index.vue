<template>
  <div class="flex w-full flex-col items-center justify-center">
    <div class="my-2 flex justify-between">
      <GaganButton
        :clicked="currentMode === 0"
        class="!mr-1"
        @click="currentMode = 0"
      >
        进行中
      </GaganButton>
      <GaganButton :clicked="currentMode === 1" @click="currentMode = 1">
        已结束
      </GaganButton>
    </div>

    <div class="mt-2">
      <div
        v-for="exam in examList"
        :key="exam.id"
        class="mt-1bg-white rounded-md p-2"
      >
        <FameraButton
          @click="
            router.push({
              name: 'doExam',
              params: {
                id: exam.id,
              },
            })
          "
        >
          {{ exam.name }}
        </FameraButton>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {
  type ExamListResult,
  examPageReleaseApi,
} from '#/api/examPageReleaseApi';
import { FameraButton, GaganButton } from '#/components';
import { router } from '#/router';
import { ref, watchEffect } from 'vue';

const currentMode = ref(0);
const examList = ref<ExamListResult[]>([]);

watchEffect(async () => {
  examList.value = await examPageReleaseApi.examList({
    mode: currentMode.value,
  });
});
</script>
