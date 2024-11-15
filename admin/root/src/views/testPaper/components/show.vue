<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-11-07 18:54:50
 * @LastEditTime: 2024-11-15 19:00:30
 * @Description: 展示选择的题目
-->

<template>
  <div class="testPager-show p-2">
    <Card>
      <template #content>
        <DataTable :value="questions" scrollable table-style="min-width: 50rem">
          <template #header>
            <div class="flex items-center justify-between">
              <span class="text-xl font-bold">题库</span>
              <div class="flex items-center">
                <SpeedDial
                  :model="menus"
                  :tooltip-options="{ position: 'top' }"
                  direction="left"
                  hide-icon="pi pi-th-large"
                  show-icon="pi pi-th-large"
                />
                <Button
                  class="ml-2"
                  icon="pi pi-plus"
                  rounded
                  severity="info"
                  @click="selectQuestionRef.open()"
                />
              </div>
            </div>
          </template>
          <Column header="题目类型" style="min-width: 110px">
            <template #body="slotProps: { data: QuestionAndPoint }">
              <Tag
                :severity="
                  QuestionTypeColorMap[slotProps.data.questionBank.type]
                "
                :value="QuestionTypeMap[slotProps.data.questionBank.type]"
              />
            </template>
          </Column>
          <Column header="题目内容" style="min-width: 300px">
            <template #body="slotProps: { data: QuestionAndPoint }">
              <EllipsisText :max-width="300">
                {{
                  extractPlainTextFromHTML(
                    slotProps.data.questionBank.content.slice(0, 30),
                  )
                }}
              </EllipsisText>
            </template>
          </Column>
          <Column header="知识点">
            <template #body="slotProps: { data: QuestionAndPoint }">
              <EllipsisText :max-width="500">
                <Tag
                  v-for="item in slotProps.data.knowledgePoints"
                  :key="item.id"
                  :severity="
                    QuestionTypeColorMap[slotProps.data.questionBank.type]
                  "
                  :value="item.name"
                  class="mx-1 my-1 flex-shrink-0"
                />
              </EllipsisText>
            </template>
          </Column>
          <Column header="难度">
            <template #body="slotProps: { data: QuestionAndPoint }">
              <Rating
                :model-value="slotProps.data.questionBank.difficulty"
                :readonly="true"
                :stars="9"
              />
            </template>
          </Column>
          <Column header="">
            <template
              #body="slotProps: { data: QuestionAndPoint; index: number }"
            >
              <div class="flex items-center">
                <div class="card flex items-center justify-center">
                  <span class="mr-2 flex-shrink-0 text-[20px]">分数</span>
                  <InputNumber
                    v-model="slotProps.data.score"
                    :max="99"
                    :min="1"
                    :step="1"
                    button-layout="horizontal"
                    fluid
                    input-id="horizontal-buttons"
                    show-buttons
                  >
                    <template #incrementbuttonicon>
                      <span class="pi pi-plus"></span>
                    </template>
                    <template #decrementbuttonicon>
                      <span class="pi pi-minus"></span>
                    </template>
                  </InputNumber>
                </div>
                <SplitButton
                  :model="[
                    {
                      label: '删除',
                      icon: 'pi pi-trash',
                      command: () => remove(slotProps.data.questionBank.id!),
                    },
                  ]"
                  class="ml-2 flex-shrink-0"
                  icon="pi pi-eye"
                  label="预览"
                  raised
                  severity="info"
                  @click="show(slotProps.data.questionBank)"
                />
              </div>
            </template>
          </Column>
        </DataTable>
      </template>
    </Card>
    <Preview ref="previewRef" />
    <SelectQuestion
      ref="selectQuestionRef"
      v-model:questions="questionAndPoints"
    />
  </div>
</template>

<script setup lang="ts">
import { computed, ref, unref } from 'vue';
import {
  Button,
  Card,
  Column,
  DataTable,
  InputNumber,
  Rating,
  SpeedDial,
  SplitButton,
  Tag,
} from '#/components';
import {
  QuestionBank,
  QuestionTypeEnum,
  QuestionTypeMap,
} from '#/api/questionBankApi';
import { EllipsisText } from '@vben/common-ui';
import Preview from '#/views/questionManager/questionBank/components/preview.vue';
import type {
  ExamPageCreateVO,
  QuestionAndPoint,
} from '#/views/testPaper/types';
import SelectQuestion from '#/views/testPaper/components/select.vue';

const props = defineProps<{
  createVo: ExamPageCreateVO;
}>();

defineEmits(['remove']);

const selectQuestionRef = ref();

const questionAndPoints = defineModel<Map<string, QuestionAndPoint>>(
  'questions',
  {
    default: new Map(),
    type: Map<string, QuestionAndPoint>,
  },
);

const questions = computed(() => [...questionAndPoints.value.values()]);

type Menu = {
  command: () => void;
  icon: string;
  label: string;
};

const menus: Menu[] = [
  {
    command: () => {
      const totalScore = props.createVo.totalScore;
      const size = questions.value.length;
      questions.value.forEach((v) => {
        v.score = Math.floor(totalScore / size);
      });
    },
    icon: 'pi pi-equals',
    label: '平均题目分数',
  },
];

const QuestionTypeColorMap = {
  [QuestionTypeEnum.GAP_FILLING]: 'primary',
  [QuestionTypeEnum.JUDGE]: 'secondary',
  [QuestionTypeEnum.MULTIPLE_CHOICE]: 'success',
  [QuestionTypeEnum.SINGLE_CHOICE]: 'info',
  [QuestionTypeEnum.SUBJECTIVE]: 'warn',
};

/**
 * 获取文本数据
 * @param htmlContent HTML内容
 */
function extractPlainTextFromHTML(htmlContent: string) {
  const tempDiv = document.createElement('div');
  tempDiv.innerHTML = htmlContent;
  return tempDiv.textContent || '';
}

/**
 * 处理预览
 */
const previewRef = ref();
async function show(questionBank: QuestionBank) {
  unref(previewRef).open(questionBank);
}

function remove(id: string) {
  questionAndPoints.value.delete(id);
}
</script>
<style lang="scss">
.testPager-show {
  .p-inputnumber-input {
    width: 50px !important;
  }
}
</style>
