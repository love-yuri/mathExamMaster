<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-08 19:49:21
 * @LastEditTime: 2025-02-08 15:20:18
 * @Description: 题库管理
-->

<template>
  <Modal>
    <div class="p-2">
      <Card>
        <template #content>
          <DataTable
            :value="questionBanks"
            scrollable
            table-style="min-width: 50rem"
          >
            <template #header>
              <div class="flex flex-wrap items-center justify-between gap-2">
                <span class="text-xl font-bold">题库</span>
                <Button
                  icon="pi pi-plus"
                  raised
                  rounded
                  @click="router.push('/question/bank/create')"
                />
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
                  {{  slotProps.data.questionBank.content }}
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
            <Column field="action" header="" style="min-width: 110px">
              <template #body="slotProps: { data: QuestionAndPoint }">
                <div class="flex items-center">
                  <Button
                    :icon="`pi pi-${questionAndPoints.has(slotProps.data.questionBank.id!) ? 'minus' : 'plus'}`"
                    :label="`${questionAndPoints.has(slotProps.data.questionBank.id!) ? '删除' : '添加'}`"
                    :severity="`${questionAndPoints.has(slotProps.data.questionBank.id!) ? 'danger' : 'info'}`"
                    raised
                    @click="add(slotProps.data)"
                  />
                  <Button
                    class="ml-2"
                    icon="pi pi-eye"
                    label="预览"
                    raised
                    severity="info"
                    @click="show(slotProps.data)"
                  />
                </div>
              </template>
            </Column>
          </DataTable>
          <Paginator
            :rows="pageParam.size"
            :rows-per-page-options="[10, 20, 30]"
            :total-records="pageParam.total"
            class="mt-2"
            @page="onPage"
          />
        </template>
      </Card>
      <Preview ref="previewRef" />
    </div>
  </Modal>
</template>

<script setup lang="ts">
import { ref, unref, useTemplateRef } from 'vue';
import {
  Button,
  Card,
  Column,
  DataTable,
  Paginator,
  Rating,
  Tag,
} from '@yuri/components';
import type { PageState } from 'primevue/paginator';
import Preview from '#/views/questionManager/questionBank/components/preview.vue';
import { router } from '#/router';
import { EllipsisText, useVbenModal } from '@vben/common-ui';
import { questionBankApi } from '@yuri/common';
import {
  type QuestionAndPoint,
  QuestionTypeEnum,
  type PageParam,
  QuestionTypeMap,
} from '@yuri/types';

const questionBanks = ref<QuestionAndPoint[]>([]);
const questionAndPoints = defineModel<Map<string, QuestionAndPoint>>(
  'questions',
  {
    default: new Map(),
    type: Map<string, QuestionAndPoint>,
  },
);

const QuestionTypeColorMap = {
  [QuestionTypeEnum.GAP_FILLING]: 'primary',
  [QuestionTypeEnum.JUDGE]: 'secondary',
  [QuestionTypeEnum.MULTIPLE_CHOICE]: 'success',
  [QuestionTypeEnum.SINGLE_CHOICE]: 'info',
  [QuestionTypeEnum.SUBJECTIVE]: 'warn',
};

const pageParam = ref<PageParam>({
  current: 1,
  size: 10,
  total: 0,
});

const [Modal, modalApi] = useVbenModal({
  fullscreen: true,
  onBeforeClose: () => {
    questionBanks.value = [];
    pageParam.value.current = 1;
    return true;
  },
  onConfirm: () => modalApi.close(),
  title: '选择题目',
});

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
 * 页码改变
 */
function onPage(p: PageState) {
  pageParam.value.current = p.page + 1;
  pageParam.value.size = p.rows;
  loadData();
}

async function loadData() {
  questionBankApi.pageSimple(pageParam.value).then((res) => {
    pageParam.value.total = res.total;
    questionBanks.value = res.records.map((item) => ({
      ...item,
      score: 1,
    }));
  });
}
// loadData();

/**
 * 处理预览
 */
const previewRef = useTemplateRef('previewRef');
async function show(v: QuestionAndPoint) {
  unref(previewRef)?.open(v.questionBank);
}

async function add(v: QuestionAndPoint) {
  const has = questionAndPoints.value.has(v.questionBank.id!);
  if (has) {
    questionAndPoints.value.delete(v.questionBank.id!);
  } else {
    questionAndPoints.value.set(v.questionBank.id!, v);
  }
}

function open() {
  modalApi.open();
  loadData();
}

defineExpose({ open });
</script>
