<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-28 17:47:25
 * @LastEditTime: 2024-11-06 21:31:25
 * @Description: 创建试卷
-->
<template>
  <div class="main p-2 pl-4">
    <div class="flex items-center">
      <span class="title required-text">科目: </span>
      <Select
        v-model="createVo.subject"
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
        class="w-full"
        placeholder="请输入试卷标题"
      />
    </div>
    <div class="mt-2 flex items-center">
      <span class="title required-text"> 试卷难度: </span>
      <Rating v-model="createVo.difficulty" :stars="9" />
    </div>
    <div class="mt-2 flex items-center">
      <span class="title"> 截止时间: </span>
      <DatePicker
        v-model="createVo.deadline"
        :show-on-focus="false"
        fluid
        placeholder="请选择截止时间"
        show-icon
      />
    </div>
    <div class="mt-2 flex items-center">
      <div class="flex flex-col items-center justify-center">
        <Knob
          v-model="createVo.limited_time"
          :max="20000"
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
            :disabled="createVo.limited_time >= 20000"
            icon="pi pi-plus"
            @click="createVo.limited_time += 600"
          />
          <Button
            :disabled="createVo.limited_time <= 600"
            icon="pi pi-minus"
            severity="danger"
            @click="createVo.limited_time -= 600"
          />
        </div>
      </div>
      <div class="ml-3 mt-2 flex flex-col items-center justify-center">
        <Knob
          v-model="createVo.total_score"
          :max="200"
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
            :disabled="createVo.total_score >= 200"
            icon="pi pi-plus"
            @click="createVo.total_score += 10"
          />
          <Button
            :disabled="createVo.total_score <= 10"
            icon="pi pi-minus"
            severity="danger"
            @click="createVo.total_score -= 10"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import {
  Button,
  DatePicker,
  InputText,
  Knob,
  Rating,
  Select,
  Tag,
} from '#/components';
import { computed, ref } from 'vue';
import { ExamPageCreateVO, subjectOptions, typeOptions } from '../types';

const createVo = ref<ExamPageCreateVO>(new ExamPageCreateVO());

function formatTime(seconds: number) {
  const hours = Math.floor(seconds / 3600);
  const minutes = Math.floor((seconds % 3600) / 60);
  return hours > 0 ? `${hours}小时 ${minutes}分` : `${minutes}分`;
}

const formatLimetedTime = computed(() =>
  formatTime(createVo.value.limited_time),
);
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
