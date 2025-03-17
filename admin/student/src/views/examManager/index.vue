<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-19 08:37:51
 * @LastEditTime: 2025-02-28 10:06:51
 * @Description: 
-->
<template>
  <div class="exam-manager-container">
    <div v-if="!currentReleaseId" class="card">
      <TabView
        v-model:active-index="currentMode"
        class="mb-4"
        @tab-change="handleTabChange"
      >
        <TabPanel header="进行中" :value="0">
          <div class="exam-list-container">
            <div v-if="loading" class="empty-state">
              <i class="pi pi-spin pi-spinner" style="font-size: 2rem"></i>
              <p>加载中...</p>
            </div>
            <div v-else-if="examList.length === 0" class="empty-state">
              <i class="pi pi-info-circle" style="font-size: 2rem"></i>
              <p>暂无进行中的考试</p>
            </div>
            <div v-else class="grid">
              <div
                v-for="exam in examList"
                :key="exam.id"
                class="col-12 md:col-6 lg:col-4 xl:col-3 p-2"
              >
                <Card class="h-full">
                  <template #title>{{ exam.name }}</template>
                  <template #content>
                    <div class="justify-content-end flex">
                      <Button
                        label="开始考试"
                        icon="pi pi-play"
                        @click="startExam(exam.id)"
                        severity="success"
                        :loading="loadingExamId === exam.id"
                      />
                    </div>
                  </template>
                </Card>
              </div>
            </div>
          </div>
        </TabPanel>
        <TabPanel header="已结束" :value="1">
          <div class="exam-list-container">
            <div v-if="examList.length === 0" class="empty-state">
              <i class="pi pi-info-circle" style="font-size: 2rem"></i>
              <p>暂无已结束的考试</p>
            </div>
            <div v-else class="grid">
              <div
                v-for="exam in examList"
                :key="exam.id"
                class="col-12 md:col-6 lg:col-4 xl:col-3 p-2"
              >
                <Card class="h-full">
                  <template #title>{{ exam.name }}</template>
                  <template #content>
                    <div class="justify-content-end flex">
                      <Button
                        label="查看详情"
                        icon="pi pi-eye"
                        @click="startExam(exam.id)"
                        severity="info"
                        :disabled="currentMode === 1"
                      />
                    </div>
                  </template>
                </Card>
              </div>
            </div>
          </div>
        </TabPanel>
        <TabPanel header="阅卷结束" :value="2">
          <div class="exam-list-container">
            <div v-if="examList.length === 0" class="empty-state">
              <i class="pi pi-info-circle" style="font-size: 2rem"></i>
              <p>暂无阅卷结束的考试</p>
            </div>
            <div v-else class="grid">
              <div
                v-for="exam in examList"
                :key="exam.id"
                class="col-12 md:col-6 lg:col-4 xl:col-3 p-2"
              >
                <Card class="h-full">
                  <template #title>{{ exam.name }}</template>
                  <template #content>
                    <div class="justify-content-end flex">
                      <Button
                        label="查看成绩"
                        icon="pi pi-chart-bar"
                        @click="viewResult(exam.id)"
                        severity="primary"
                      />
                    </div>
                  </template>
                </Card>
              </div>
            </div>
          </div>
        </TabPanel>
      </TabView>
    </div>
    <DoExam v-else :id="currentReleaseId" @back="handleBackFromExam" />
  </div>
</template>

<script setup lang="ts">
import type { ExamListResult } from '@yuri/types';

import { ref, watch } from 'vue';

import { examPageReleaseApi, useExamStore } from '@yuri/common';
import { Button, Card, TabPanel, TabView } from '@yuri/components';
import { storeToRefs } from 'pinia';

import { router } from '#/router';

import DoExam from './doExam.vue';

const examStore = useExamStore();
const { releaseId } = storeToRefs(examStore);

const currentReleaseId = ref<string | undefined>();
const currentMode = ref(0);
const examList = ref<ExamListResult[]>([]);
const loading = ref(false);
const loadingExamId = ref<null | string>(null);

// 监听模式变化，加载对应的考试列表
watch(
  currentMode,
  async () => {
    await checkOngoingExam();
    if (!currentReleaseId.value) {
      await loadExamList();
    }
  },
  { immediate: true },
);

// 检查是否有正在进行的考试
async function checkOngoingExam() {
  if (releaseId?.value) {
    try {
      const res = await examPageReleaseApi.check(releaseId.value);
      currentReleaseId.value = res ? releaseId.value : undefined;
      if (res) {
        return true;
      }
      releaseId.value = undefined;
    } catch (error) {
      console.error('检查考试状态失败:', error);
      releaseId.value = undefined;
      currentReleaseId.value = undefined;
    }
  }
  return false;
}

// 加载考试列表
async function loadExamList() {
  loading.value = true;
  try {
    examList.value = await examPageReleaseApi.examList({
      mode: currentMode.value,
    });
  } catch (error) {
    console.error('加载考试列表失败:', error);
    examList.value = [];
  } finally {
    loading.value = false;
  }
}

// 切换标签页时的处理
function handleTabChange() {
  loadExamList();
}

// 开始考试
async function startExam(id: string) {
  if (currentMode.value !== 0) {
    return;
  }

  loadingExamId.value = id;
  try {
    await examPageReleaseApi.startExam(id);
    currentReleaseId.value = id;
    if (releaseId) {
      releaseId.value = id;
    }
  } catch (error) {
    console.error('开始考试失败:', error);
  } finally {
    loadingExamId.value = null;
  }
}

// 从考试页面返回
function handleBackFromExam() {
  currentReleaseId.value = undefined;
  if (releaseId) {
    releaseId.value = undefined;
  }
  loadExamList();
}

// 查看考试结果
function viewResult(id: string) {
  router.push({
    name: 'viewResult',
    params: {
      id,
    },
  });
}
</script>

<style scoped>
.exam-manager-container {
  padding: 1rem;
}

.card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgb(0 0 0 / 5%);
}

.exam-list-container {
  min-height: 300px;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 200px;
  color: #6c757d;
}
</style>
