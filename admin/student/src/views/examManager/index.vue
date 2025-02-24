<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-19 08:37:51
 * @LastEditTime: 2025-02-23 16:48:07
 * @Description: 
-->
<template>
  <div
    v-if="!currentReleaseId"
    class="flex w-full flex-col items-center justify-center"
  >
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
        <FameraButton @click="startExam(exam.id)">
          {{ exam.name }}
        </FameraButton>
      </div>
    </div>
  </div>
  <DoExam v-else :id="currentReleaseId" />
</template>

<script setup lang="ts">
import { type ExamListResult } from '@yuri/types';
import { FameraButton, GaganButton } from '@yuri/components';
import { ref, watch } from 'vue';
import DoExam from './doExam.vue';
import { examPageReleaseApi, useExamStore } from '@yuri/common';
import { storeToRefs } from 'pinia';

const examStore = useExamStore();
const { releaseId } = storeToRefs(examStore);

const currentReleaseId = ref<string | undefined>();
const currentMode = ref(0);
const examList = ref<ExamListResult[]>([]);

watch(
  currentMode,
  async () => {
    if (releaseId?.value) {
      try {
        const res = await examPageReleaseApi.check(releaseId.value);
        currentReleaseId.value = res ? releaseId.value : undefined;
        if (res) {
          return;
        }
        releaseId.value = undefined;
      } catch (error) {
        currentReleaseId.value = undefined;
      }
      
    }

    examList.value = await examPageReleaseApi.examList({
      mode: currentMode.value,
    });
  },
  { immediate: true },
);

function startExam(id: string) {
  if (currentMode.value === 1) {
    return;
  }
  currentReleaseId.value = id;
  examStore.startExam(id);
}
</script>
