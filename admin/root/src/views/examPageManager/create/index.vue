<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-28 17:47:25
 * @LastEditTime: 2025-02-08 15:25:44
 * @Description: 创建试卷
-->
<template>
  <div class="main p-2 pl-4">
    <div class="flex items-center">
      <span class="title required-text">科目: </span>
      <Select
        v-model="createVo.subject"
        :disabled="isReadOnly"
        :options="subjectOptions"
        class="w-64"
        option-label="label"
        option-value="value"
        placeholder="请选择科目"
      />
    </div>
    <div class="mt-2 flex items-center">
      <span class="title required-text">试卷类型: </span>
      <Select
        v-model="createVo.type"
        :disabled="isReadOnly"
        :options="typeOptions"
        class="w-64"
        option-label="label"
        option-value="value"
        placeholder="请选择试卷类型"
      />
    </div>
    <div class="mt-2 flex items-center">
      <span class="title required-text"> 试卷标题: </span>
      <InputText
        v-model="createVo.title"
        :maxlength="70"
        :readonly="isReadOnly"
        class="w-full"
        placeholder="请输入试卷标题"
      />
    </div>
    <div class="mt-2 flex items-center">
      <span class="title required-text"> 试卷难度: </span>
      <Rating v-model="createVo.difficulty" :readonly="isReadOnly" :stars="9" />
    </div>
    <!-- <div class="mt-2 flex items-center">
      <span class="title"> 截止时间: </span>
      <DatePicker
        v-model="createVo.deadline"
        :min-date="new Date()"
        :readonly="isReadOnly"
        :show-on-focus="false"
        date-format="yy-mm-dd"
        fluid
        hour-format="24"
        placeholder="请选择截止时间"
        show-icon
        show-time
      />
    </div> -->
    <div class="mt-2 flex items-center">
      <div class="flex flex-col items-center justify-center">
        <Knob
          v-model="createVo.limitedTime"
          :max="20000"
          :min="60"
          :readonly="isReadOnly"
          :size="150"
          :step="60"
          :value-template="formatLimetedTime"
        />
        <Tag
          class="mb-2 h-12 px-2 text-center !text-[24px] font-black"
          severity="info"
        >
          考试时长
        </Tag>
        <div class="flex gap-2">
          <Button
            :disabled="createVo.limitedTime >= 20000 || isReadOnly"
            icon="pi pi-plus"
            @click="createVo.limitedTime += 600"
          />
          <Button
            :disabled="createVo.limitedTime <= 600 || isReadOnly"
            icon="pi pi-minus"
            severity="danger"
            @click="createVo.limitedTime -= 600"
          />
        </div>
      </div>
      <div class="ml-3 mt-2 flex flex-col items-center justify-center">
        <Knob
          v-model="createVo.totalScore"
          :max="200"
          :min="1"
          :readonly="isReadOnly"
          :size="150"
          :step="10"
          value-template="{value}分"
        />
        <Tag
          class="mb-2 h-12 px-2 text-center !text-[24px] font-black"
          severity="info"
        >
          试卷总分
        </Tag>
        <div class="flex gap-2">
          <Button
            :disabled="createVo.totalScore >= 200 || isReadOnly"
            icon="pi pi-plus"
            @click="createVo.totalScore += 10"
          />
          <Button
            :disabled="createVo.totalScore <= 10 || isReadOnly"
            icon="pi pi-minus"
            severity="danger"
            @click="createVo.totalScore -= 10"
          />
        </div>
      </div>
    </div>
    <!-- <div class="flex justify-center p-2">
      <MultiSelect
        v-model="createVo.users"
        :disabled="isReadOnly"
        :options="users"
        class="w-full"
        filter
        option-label="username"
        option-value="id"
        placeholder="请选择学生"
      />
    </div> -->
    <div class="flex items-center justify-center p-2">
      <Button
        v-if="!isReadOnly"
        :label="`${createVo.id ? '更新' : '发布'}试卷`"
        class="h-[50px] w-[120px] text-[22px]"
        @click="releasePage"
      />
    </div>
    <Show
      v-model:questions="questionMap"
      :create-vo="createVo"
      :is-read-only
      @edit="isReadOnly = false"
    />
  </div>
</template>
<script setup lang="ts">
import { Button, InputText, Knob, Rating, Select, Tag } from '#/components';
import { computed, onMounted, ref } from 'vue';
import Show from '#/views/examPageManager/components/show.vue';
import { useRoute } from '#/router';
import { checkEmpty, checkEqual, userApi, examPageApi, checkSuccess } from '@yuri/common';
import { ExamPageCreateVO, type QuestionAndPoint, type Student, typeOptions, subjectOptions } from '@yuri/types';

const route = useRoute();

const createVo = ref<ExamPageCreateVO>(new ExamPageCreateVO());
const questionMap = ref(new Map<string, QuestionAndPoint>());

const isReadOnly = ref(false);
const users = ref<Student[]>([]);

function formatTime(seconds: number) {
  const hours = Math.floor(seconds / 3600);
  const minutes = Math.floor((seconds % 3600) / 60);
  return hours > 0 ? `${hours}小时 ${minutes}分` : `${minutes}分`;
}

const formatLimetedTime = computed(() =>
  formatTime(createVo.value.limitedTime),
);

function valueCheck(): boolean {
  let totalScore = 0;
  for (const v of questionMap.value.values()) {
    totalScore += v.score;
  }

  checkEmpty(createVo.value.title, '试卷标题不能为空!');
  checkEmpty(createVo.value.limitedTime, '考试时长不能为空!');
  checkEmpty(questionMap.value, '题目不能为空!');
  checkEqual(totalScore, createVo.value.totalScore, '总分与题目总分不一致!');
  return true;
}

onMounted(() => {
  userApi.students().then((res) => {
    users.value = res;
  });
  if (route.params.id) {
    isReadOnly.value = true;
    examPageApi.detail(route.params.id as string).then((res) => {
      createVo.value.copy(res);
      res.questions.forEach((item) =>
        questionMap.value.set(item.questionBankId, {
          ...item.fullQuestionBank!,
          score: item.score,
        }),
      );
    });
  }
});

function releasePage() {
  if (!valueCheck()) {
    return;
  }
  createVo.value.questions = [...questionMap.value.values()].map((item) => ({
    questionBankId: item.questionBank.id!,
    score: item.score,
  }));
  // if (createVo.value.deadline) {
  //   createVo.value.deadline = new Date(
  //     createVo.value.deadline,
  //   ).toLocaleString();
  // }
  const isUpdate = !!createVo.value.id;
  const fun = isUpdate ? examPageApi.updatePage : examPageApi.release;
  checkSuccess(fun(createVo.value), !isUpdate, '试卷', (v) => {
    if (v && !createVo.value.id) {
      createVo.value.reset();
      questionMap.value.clear();
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
