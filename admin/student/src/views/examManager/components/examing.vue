<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-23 18:55:25
 * @LastEditTime: 2025-01-06 20:43:53
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
              <template v-for="item in questions" :key="item.type">
                <div
                  class="mt-4 rounded-sm bg-gray-200 px-[5px] py-[4px] text-[22px] font-bold"
                >
                  {{ QuestionTypeMap[item.type] }}
                </div>
                <div class="mt-2 grid grid-cols-5 gap-2">
                  <Button
                    v-for="question in item.infos"
                    :key="question.id"
                    :label="`${question.index}`"
                    :severity="`${question.id === currentQuestionInfo?.id ? 'info' : question?.hasAnswer ? 'success' : 'secondary'} `"
                    raised
                    rounded
                    @click="selectQuestion(question)"
                  />
                </div>
              </template>
            </div>
          </template>
        </Card>
      </div>
    </div>

    <!-- 主答题区域 -->
    <div class="h-full flex-grow p-6">
      <div v-if="currentQuestionInfo">
        <PreviewEditor :content="currentQuestionInfo.content" />
        <Card class="mt-4">
          <template #content>
            <template
              v-if="currentQuestionInfo.type === QuestionTypeEnum.SINGLE_CHOICE"
            >
              <div class="flex flex-col p-2">
                <Button
                  v-for="(item, index) in currentQuestionInfo.options"
                  :key="index"
                  :label="`${String.fromCharCode(65 + index)}: ${item}`"
                  :severity="
                    currentQuestionInfo.answer[0]?.includes(index.toString())
                      ? 'success'
                      : 'secondary'
                  "
                  class="my-2"
                  @click="chooseAnswer(index)"
                />
              </div>
            </template>
            <template
              v-else-if="
                currentQuestionInfo.type === QuestionTypeEnum.MULTIPLE_CHOICE
              "
            >
              <div class="flex flex-col p-2">
                <Button
                  v-for="(item, index) in currentQuestionInfo.options"
                  :key="index"
                  :label="`${String.fromCharCode(65 + index)}: ${item}`"
                  :severity="
                    currentQuestionInfo.answer.includes(index.toString())
                      ? 'success'
                      : 'secondary'
                  "
                  class="my-2"
                  @click="chooseAnswer(index)"
                />
              </div>
            </template>
            <template
              v-else-if="
                currentQuestionInfo.type === QuestionTypeEnum.GAP_FILLING
              "
            >
              <div class="flex flex-col p-2">
                <div
                  v-for="(_, index) in currentQuestionInfo.options"
                  :key="index"
                  class="my-2 flex text-[20px]"
                >
                  <span class="mr-2 flex-shrink-0">第{{ index + 1 }}空:</span>
                  <InputText
                    v-model="currentQuestionInfo.answer[index]"
                    class="w-full"
                    @change="updateAnswer"
                  />
                </div>
              </div>
            </template>
            <template
              v-else-if="currentQuestionInfo.type === QuestionTypeEnum.JUDGE"
            >
              <div class="mt-2 flex items-center">
                <Button
                  :severity="
                    currentQuestionInfo.answer[0] === '0'
                      ? 'success'
                      : 'secondary'
                  "
                  icon="pi pi-check"
                  rounded
                  @click="chooseAnswer(0)"
                />
                <Button
                  :severity="
                    currentQuestionInfo.answer[0] === '1'
                      ? 'success'
                      : 'secondary'
                  "
                  class="ml-2"
                  icon="pi pi-times"
                  rounded
                  @click="chooseAnswer(1)"
                />
              </div>
            </template>
            <template
              v-else-if="
                currentQuestionInfo.type === QuestionTypeEnum.SUBJECTIVE
              "
            >
              <div v-if="showEditor" class="mt-2 flex items-center">
                <WangEditor
                  v-model:content="currentQuestionInfo.answer[0]!!"
                  placeholder="请输入答案..."
                  @change="updateAnswer"
                />
              </div>
            </template>
          </template>
        </Card>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import {
  Button,
  Card,
  InputText,
  PreviewEditor,
  WangEditor,
} from '#/components';
import type { ExamInfoResult } from '#/api/examPageReleaseApi';
import { computed, nextTick, onUnmounted, ref, watchEffect } from 'vue';
import { QuestionTypeEnum, QuestionTypeMap } from '#/api/questionBankApi';
import {
  examPageApi,
  type QuestionInfo,
  type QuestionInfoResult,
} from '#/api/examPageApi';
import { debounce } from '#/common/utils/commonUtils';
import message from '#/common/utils/message';

const { examInfo } = defineProps<{
  examInfo: ExamInfoResult;
}>();

/**
 * 处理题目显示和答案
 */
const questions = ref<QuestionInfoResult[]>([]);
const currentQuestionInfo = ref<QuestionInfo>();
const showEditor = ref(false);
async function selectQuestion(question: QuestionInfo) {
  showEditor.value = false;
  await nextTick(); // 等待dom渲染完成
  showEditor.value = true;
  await nextTick(); // 等待dom渲染完成
  currentQuestionInfo.value = question;
  if (!currentQuestionInfo.value.hasAnswer) {
    currentQuestionInfo.value.hasAnswer = false;
    currentQuestionInfo.value.answer = [];
  }
}

/**
 * 更新用户答案
 */
const updateAnswer = debounce(() => {
  currentQuestionInfo.value!!.hasAnswer =
    currentQuestionInfo.value!!.answer.length > 0;
  if (currentQuestionInfo.value?.type === QuestionTypeEnum.GAP_FILLING) {
    let hasAnswer = false;
    currentQuestionInfo.value.answer.forEach((k) => {
      if (k !== '') {
        hasAnswer = true;
      }
    });
    currentQuestionInfo.value.hasAnswer = hasAnswer;
  }
  const param: any = {};
  questions.value.forEach((k, _) => {
    k.infos.forEach((q) => {
      param[q.id!!] = Array.isArray(q.answer) ? q.answer : [];
    });
  });
  examPageApi
    .updateUserAnswer({
      answer: param,
      relationId: examInfo.relationId,
    })
    .then(() => {
      message.success('保存成功');
    });
}, 1000);

function chooseAnswer(index_: number) {
  if (!currentQuestionInfo.value) {
    return;
  }
  const index = index_.toString();
  switch (currentQuestionInfo.value?.type) {
    // 单选
    case QuestionTypeEnum.SINGLE_CHOICE: {
      currentQuestionInfo.value.answer = [index];
      break;
    }
    // 多选
    case QuestionTypeEnum.MULTIPLE_CHOICE: {
      if (currentQuestionInfo.value.answer.includes(index)) {
        currentQuestionInfo.value.answer =
          currentQuestionInfo.value.answer.filter((item) => item !== index);
      } else {
        currentQuestionInfo.value.answer.push(index);
      }
      break;
    }
    // 判断
    case QuestionTypeEnum.JUDGE: {
      currentQuestionInfo.value.answer = [index];
      break;
    }
  }
  updateAnswer();
}

/**
 * 处理倒计时
 */
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

  examPageApi
    .questionInfo({
      examPageId: examInfo.examPageId,
      relationId: examInfo.relationId,
    })
    .then((res) => {
      questions.value = res;
      if (res.length > 0 && res[0]!!.infos.length > 0) {
        currentQuestionInfo.value = res[0]?.infos[0]!!;
      }
    });

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
