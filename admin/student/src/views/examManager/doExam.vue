<template>
  <div class="flex h-full w-full items-center justify-center bg-gray-100 p-6">
    <Card class="rounded-lg bg-white p-6 shadow-lg">
      <template #content>
        <div class="min-w-[800px] space-y-6">
          <!-- 标题 -->
          <div class="text-center text-[32px] font-bold text-gray-800">
            {{ currentPage.title }}
          </div>

          <div class="grid grid-cols-2 gap-4">
            <div>
              <div class="info">
                <span class="title">考试科目: </span>
                <span class="content">{{ examPageSubject }}</span>
              </div>
              <div class="info">
                <span class="title">考试难度: </span>
                <Rating :model-value="currentPage.difficulty" :stars="9" />
              </div>
              <div v-show="false" class="info">
                <span class="title">试卷类型: </span>
                <span class="content">{{ examPageType }}</span>
              </div>
              <div class="info">
                <span class="title">结束时间: </span>
                <span class="content">{{ currentRelease.endTime }}</span>
              </div>
            </div>

            <div>
              <div class="info">
                <span class="title">试卷总分: </span>
                <span class="content">{{ currentPage.totalScore }} 分</span>
              </div>
              <div class="info">
                <span class="title">考试时长: </span>
                <span class="content">
                  {{ currentPage.limitedTime / 60 }} 分钟
                </span>
              </div>
            </div>
          </div>
          <div class="flex justify-center">
            <FameraButton @click="startExam">
              {{
                currentRelation.status ===
                ExamPageUserRelationStatusType.NOT_START
                  ? '开始考试'
                  : '继续考试'
              }}
            </FameraButton>
          </div>
        </div>
      </template>
    </Card>
  </div>
</template>
<script setup lang="ts">
import {
  examPageApi,
  ExamPageMap,
  ExamPageResult,
  SubjectTypeMap,
} from '#/api/examPageApi';
import {
  examPageReleaseApi,
  ExamPageReleaseResult,
} from '#/api/examPageReleaseApi';
import { useRoute } from '#/router';
import { computed, onMounted, ref } from 'vue';
import { Card, FameraButton, Rating } from '#/components';
import {
  ExamPageUserRelation,
  examPageUserRelationApi,
  ExamPageUserRelationStatusType,
} from '#/api/examPageUserRelationApi';

const route = useRoute();

const currentRelation = ref<ExamPageUserRelation>(new ExamPageUserRelation());
const currentRelease = ref<ExamPageReleaseResult>(new ExamPageReleaseResult());
const currentPage = ref<ExamPageResult>(new ExamPageResult());
const examPageType = computed(() => ExamPageMap[currentPage.value.type]);
const examPageSubject = computed(
  () => SubjectTypeMap[currentPage.value.subject],
);

async function loadData() {
  const id = route.params.id as string;
  if (!id) {
    return;
  }
  const release = await examPageReleaseApi.detail(id);
  currentRelation.value.copy(
    await examPageUserRelationApi.relation(release.id!!),
  );
  const page = await examPageApi.detail(release.examPage!!.id!!);
  currentPage.value.copy(page);
  currentRelease.value.copy(release);
}

function startExam() {
  if (
    currentRelation.value.status === ExamPageUserRelationStatusType.NOT_START
  ) {
    examPageApi.startExam(currentRelease.value.id!!);
  }
}

onMounted(loadData);
</script>
<style lang="scss" scoped>
.info {
  display: flex;
  align-items: center;
  .title {
    font-weight: 500;
    --tw-text-opacity: 1;
    color: rgb(55 65 81 / var(--tw-text-opacity));
    font-size: 19px;
    line-height: 29px;
    margin-right: 5px;
  }
  .content {
    --tw-text-opacity: 1;
    color: rgb(17 24 39 / var(--tw-text-opacity));
    font-weight: 700;
    font-size: 22px;
    line-height: 30px;
  }
}
</style>
