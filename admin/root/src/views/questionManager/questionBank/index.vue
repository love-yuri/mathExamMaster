<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-08 19:49:21
 * @LastEditTime: 2024-10-19 20:31:49
 * @Description: 题库管理
-->

<template>
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
          <Column field="type" header="题目类型" style="min-width: 110px">
            <template #body="slotProps: { data: QuestionBankType }">
              <Tag
                :severity="QuestionTypeColorMap[slotProps.data.type]"
                :value="QuestionTypeMap[slotProps.data.type]"
              />
            </template>
          </Column>
          <Column field="content" header="题目内容" style="min-width: 300px">
            <template #body="slotProps: { data: QuestionBankType }">
              <EllipsisText :max-width="300">
                {{
                  extractPlainTextFromHTML(slotProps.data.content.slice(0, 30))
                }}
              </EllipsisText>
            </template>
          </Column>
          <Column field="updateTime" header="修改时间" />
          <Column field="knowledgePoint" header="知识点">
            <template #body="slotProps: { data: QuestionBankType }">
              <EllipsisText :max-width="500">
                <Tag
                  v-for="item in slotProps.data.knowledgePoints"
                  :key="item.id"
                  :severity="QuestionTypeColorMap[slotProps.data.type]"
                  :value="item.name"
                  class="mx-1 my-1 flex-shrink-0"
                />
              </EllipsisText>
            </template>
          </Column>
          <Column field="action" header="" style="min-width: 110px">
            <template #body="slotProps: { data: QuestionBankType }">
              <SplitButton
                :model="[
                  {
                    label: '编辑',
                    icon: 'pi pi-pencil',
                    command: () => edit(slotProps.data),
                  },
                  {
                    label: '删除',
                    icon: 'pi pi-trash',
                    command: () => remove(slotProps.data.id!),
                  },
                ]"
                icon="pi pi-eye"
                label="预览"
                raised
                severity="info"
                @click="show(slotProps.data)"
              />
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
    <DefaultConfirmDialog group="headless" />
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, unref } from 'vue';
import {
  Button,
  Card,
  Column,
  DataTable,
  DefaultConfirmDialog,
  Paginator,
  SplitButton,
  Tag,
} from '#/components';
import {
  type FullQuestionBank,
  QuestionBank,
  questionBankApi,
  QuestionTypeEnum,
  QuestionTypeMap,
} from '#/api/questionBankApi';
import { type PageParam } from '#/common/base/baseApi/baseApi';
import type { PageState } from 'primevue/paginator';
import Preview from './components/preview.vue';
import { router } from '#/router';
import { EllipsisText } from '@vben/common-ui';
import type { KnowledgePoint } from '#/api/knowledgePointApi';
import message from '#/common/utils/message';
import { useConfirm } from 'primevue/useconfirm';

const confirm = useConfirm();

/**
 * 处理数据分页
 */
const fullQuestionBanks = ref<FullQuestionBank[]>([]);

type QuestionBankType = {
  knowledgePoints: KnowledgePoint[];
} & QuestionBank;

const questionBanks = computed((): QuestionBankType[] =>
  fullQuestionBanks.value.map((item) => ({
    ...item.questionBank,
    knowledgePoints: item.knowledgePoints,
  })),
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
  const res = await questionBankApi.pageSimple(pageParam.value);
  fullQuestionBanks.value = res.records;
  pageParam.value.total = res.total;
}

onMounted(loadData);

/**
 * 删除
 * @param id 题目id
 */
function remove(id: string) {
  confirm.require({
    accept: () => {
      questionBankApi.delete(id).then(() => {
        message.success('删除成功');
        loadData();
      });
    },
    group: 'headless',
    message: '是否要删除该题目?',
  });
}

/**
 * 处理预览
 */
const previewRef = ref();
async function show(questionBank: QuestionBankType) {
  unref(previewRef).open(questionBank);
}
async function edit(questionBank: QuestionBankType) {
  router.push({ name: 'questionBankUpdate', params: { id: questionBank.id } });
}
</script>
