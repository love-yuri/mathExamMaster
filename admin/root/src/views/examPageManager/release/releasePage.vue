<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-28 17:47:25
 * @LastEditTime: 2024-12-05 19:41:43
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
          <span class="title"> 试卷难度: </span>
          <Rating
            v-model="examPageResult.difficulty"
            :readonly="true"
            :stars="9"
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
          v-model="releaseParam.startTime"
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
          v-model="releaseParam.endTime"
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
      <MultiSelect
        v-model="releaseParam.userIds"
        :options="users"
        class="w-full"
        filter
        option-label="username"
        option-value="id"
        placeholder="请选择学生"
      />
    </div>
    <div class="flex items-center justify-center">
      <Button class="w-24" label="发布" severity="info" @click="release" />
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
import {
  Button,
  Card,
  DatePicker,
  InputText,
  MultiSelect,
  Rating,
  Tag,
} from '#/components';
import { computed, onMounted, ref } from 'vue';
import {
  ExamPageReleaseParam,
  ExamPageResult,
  type QuestionAndPoint,
  subjectOptions,
  typeOptions,
} from '#/views/examPageManager/types';
import Show from '#/views/examPageManager/components/show.vue';

import { examPageApi } from '#/api/examPageApi';
import { type Student, userApi } from '#/api/userApi';
import { useRoute } from '#/router';
import { checkEmpty } from '#/common/utils/valueCheck';
import { formatPrimeVueTime } from '#/common/utils/timeUtils';
import { examPageReleaseApi } from '#/api/examPageReleaseApi';
import message from '#/common/utils/message';

const route = useRoute();

const releaseParam = ref<ExamPageReleaseParam>(new ExamPageReleaseParam());
const examPageResult = ref<ExamPageResult>(new ExamPageResult());
const questionMap = ref(new Map<string, QuestionAndPoint>());

const users = ref<Student[]>([]);

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

onMounted(() => {
  userApi.students().then((res) => {
    users.value = res;
  });
  if (route.params.id) {
    releaseParam.value.examPageId = route.params.id as string;
    examPageApi.detail(route.params.id as string).then((res) => {
      examPageResult.value.copy(res);
      res.questions.forEach((item) =>
        questionMap.value.set(item.questionBankId, {
          ...item.fullQuestionBank!,
          score: item.score,
        }),
      );
    });
  }
});

function release() {
  checkEmpty(releaseParam.value.startTime, '开始时间不能为空');
  checkEmpty(releaseParam.value.endTime, '结束时间不能为空');
  checkEmpty(releaseParam.value.userIds, '请选择学生');
  releaseParam.value.startTime = formatPrimeVueTime(
    releaseParam.value.startTime,
  );
  releaseParam.value.endTime = formatPrimeVueTime(releaseParam.value.endTime);
  examPageReleaseApi.release(releaseParam.value).then((res) => {
    if (res) {
      message.success('试卷发布成功');
    } else {
      message.error('试卷发布失败');
    }
  });
}
</script>
<style lang="less" scoped>
.required-text {
  position: relative; /* 设定相对定位，为了让伪元素绝对定位 */
  display: inline-block;
  &::before {
    content: '*'; /* 设置伪元素内容 */
    color: red; /* 设定星号颜色 */
    position: absolute; /* 绝对定位 */
    top: 0.1em; /* 调整到左上角 */
    left: -0.5em;
    font-size: 1em; /* 设置星号大小，可以根据需要调整 */
  }
}

.main {
  .title {
    font-size: 16px;
    margin-right: 6px;
    font-weight: 600;
    flex-shrink: 0;
  }
}
</style>
