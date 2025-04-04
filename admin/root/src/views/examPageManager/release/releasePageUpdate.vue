<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-28 17:47:25
 * @LastEditTime: 2025-03-24 19:54:00
 * @Description: 创建试卷
-->
<template>
  <div class="main p-2 pl-4">
    <Card>
      <template #title>
        <div class="text-[23px] font-semibold">试卷信息</div>
      </template>
      <template #content>
        <div class="flex items-center">
          <span class="title">科目: </span>
          <Tag :value="subjectType" />
        </div>
        <div class="mt-2 flex items-center">
          <span class="title">试卷类型: </span>
          <Tag :value="pageType" />
        </div>
        <div class="mt-2 flex items-center">
          <span class="title"> 试卷标题: </span>
          <InputText
            v-model="examPageResult.title"
            :maxlength="70"
            :readonly="true"
            class="w-full"
            placeholder="请输入试卷标题"
          />
        </div>
        <div class="mt-2 flex items-center">
          <span class="title required-text"> 试卷难度: </span>
          <Select
            v-model="examPageResult.difficulty"
            :options="[
              { label: '易', value: 2 },
              { label: '中', value: 4 },
              { label: '难', value: 6 },
              { label: '极难', value: 8 },
            ]"
            :disabled="true"
            option-label="label"
            option-value="value"
            placeholder="请选择难度"
            class="w-full"
          />
        </div>
        <div class="mt-2 flex items-center">
          <span class="title">考试时长: </span>
          <Tag :value="formatLimetedTime" />
        </div>
        <div class="mt-2 flex items-center">
          <span class="title">试卷总分: </span>
          <Tag :value="`${examPageResult.totalScore} 分`" />
        </div>
      </template>
    </Card>
    <div class="mt-2 flex items-center">
      <div class="mr-2 flex items-center">
        <span class="title"> 开始时间: </span>
        <DatePicker
          v-model="releaseParam.startTime as unknown as Date"
          :manual-input="false"
          :min-date="new Date()"
          :show-on-focus="false"
          date-format="yy-mm-dd"
          fluid
          hour-format="24"
          placeholder="试卷开始时间"
          show-icon
          show-time
        />
      </div>
      <div class="mr-2 flex items-center">
        <span class="title"> 结束时间: </span>
        <DatePicker
          v-model="releaseParam.endTime as unknown as Date"
          :manual-input="false"
          :min-date="new Date()"
          :show-on-focus="false"
          date-format="yy-mm-dd"
          fluid
          hour-format="24"
          placeholder="试卷结束时间"
          show-icon
          show-time
        />
      </div>
    </div>

    <div class="my-2 flex justify-center">
      <Select
        v-model="releaseParam.departmentId"
        :options="departments"
        class="w-full"
        filter
        option-label="name"
        option-value="id"
        placeholder="请选择班级"
      />
    </div>
    <div class="flex items-center justify-center">
      <Button class="w-24" label="更新" severity="info" @click="release" />
    </div>

    <Show
      v-model:questions="questionMap"
      :create-vo="examPageResult"
      :is-read-only="true"
      class="mt-2"
    />
  </div>
</template>
<script setup lang="ts">
import type { QuestionAndPoint } from '@yuri/types';

import { computed, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

import {
  checkEmpty,
  departmentApi,
  examPageApi,
  examPageReleaseApi,
  formatPrimeVueTime,
  message,
} from '@yuri/common';
import {
  Button,
  Card,
  DatePicker,
  InputText,
  Select,
  Tag,
} from '@yuri/components';
import {
  Department,
  ExamPageReleaseParam,
  ExamPageResult,
  subjectOptions,
  typeOptions,
} from '@yuri/types';

import Show from '#/views/examPageManager/components/show.vue';

const route = useRoute();

const releaseParam = ref<ExamPageReleaseParam>(new ExamPageReleaseParam());
const examPageResult = ref<ExamPageResult>(new ExamPageResult());
const questionMap = ref(new Map<string, QuestionAndPoint>());

const departments = ref<Department[]>([]);
function formatTime(seconds: number) {
  const hours = Math.floor(seconds / 3600);
  const minutes = Math.floor((seconds % 3600) / 60);
  return hours > 0 ? `${hours}小时 ${minutes}分` : `${minutes}分`;
}

const formatLimetedTime = computed(() =>
  formatTime(examPageResult.value.limitedTime),
);

const pageType = computed(
  () => typeOptions.find((v) => v.value === examPageResult.value.type)?.label,
);

const subjectType = computed(
  () =>
    subjectOptions.find((v) => v.value === examPageResult.value.subject)?.label,
);

async function loadData() {
  departmentApi.ownerDepartments().then((res) => {
    departments.value = res;
  });
  const releaseId = route.params.id as string;
  examPageReleaseApi.detail(releaseId).then((res) => {
    releaseParam.value.copy(res);
    releaseParam.value.examPageId = res.examPage?.id as string;
    examPageApi.detail(releaseParam.value.examPageId).then((res) => {
      examPageResult.value.copy(res);
      res.questions.forEach((item) =>
        questionMap.value.set(item.questionBankId, {
          ...item.fullQuestionBank!,
          score: item.score,
        }),
      );
    });
  });
}

onMounted(loadData);

function release() {
  checkEmpty(releaseParam.value.startTime, '开始时间不能为空');
  checkEmpty(releaseParam.value.endTime, '结束时间不能为空');
  checkEmpty(releaseParam.value.departmentId, '请选择班级');
  releaseParam.value.startTime = formatPrimeVueTime(
    releaseParam.value.startTime!!,
  );
  releaseParam.value.endTime = formatPrimeVueTime(releaseParam.value.endTime!!);
  examPageReleaseApi.releaseUpdate(releaseParam.value).then((res) => {
    if (res) {
      message.default.success('更新发布成功');
    } else {
      message.default.error('更新发布失败');
    }
  });
}
</script>
<style lang="scss" scoped>
.required-text {
  position: relative; /* 设定相对定位，为了让伪元素绝对定位 */
  display: inline-block;

  &::before {
    position: absolute; /* 绝对定位 */
    top: 0.1em; /* 调整到左上角 */
    left: -0.5em;
    font-size: 1em; /* 设置星号大小，可以根据需要调整 */
    color: red; /* 设定星号颜色 */
    content: '*'; /* 设置伪元素内容 */
  }
}

.main {
  .title {
    flex-shrink: 0;
    margin-right: 6px;
    font-size: 16px;
    font-weight: 600;
  }
}
</style>
