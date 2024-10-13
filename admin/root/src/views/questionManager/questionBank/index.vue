<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-08 19:49:21
 * @LastEditTime: 2024-10-13 16:44:31
 * @Description: 题库管理
-->

<template>
  <div class="p-2">
    <Card>
      <template #content>
        <DataTable :value="questionBanks" table-style="min-width: 50rem">
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
          <Column field="type" header="题目类型">
            <template #body="slotProps: { data: QuestionBank }">
              <Tag
                :severity="QuestionTypeColorMap[slotProps.data.type]"
                :value="QuestionTypeMap[slotProps.data.type]"
              />
            </template>
          </Column>
          <Column field="updateTime" header="创建时间" />
          <Column field="action" header="">
            <template #body="slotProps: { data: QuestionBank }">
              <Button
                icon="pi pi-eye"
                label="预览"
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
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, unref } from 'vue';
import { Button, Card, Column, DataTable, Paginator, Tag } from '#/components';
import {
  QuestionBank,
  questionBankApi,
  QuestionTypeEnum,
  QuestionTypeMap,
} from '#/api/questionBankApi';
import { type PageParam } from '#/common/base/baseApi/baseApi';
import type { PageState } from 'primevue/paginator';
import Preview from './components/preview.vue';
import { router } from '#/router';

/**
 * 处理数据分页
 */
const questionBanks = ref<QuestionBank[]>([]);
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
 * 页码改变
 */
function onPage(p: PageState) {
  pageParam.value.current = p.page + 1;
  pageParam.value.size = p.rows;
  loadData();
}

/**
 * 加载数据
 */
async function loadData() {
  const res = await questionBankApi.page(pageParam.value);
  questionBanks.value = res.records;
  pageParam.value.total = res.total;
}

onMounted(loadData);

/**
 * 处理预览
 */
const previewRef = ref();
function show(questionBank: QuestionBank) {
  unref(previewRef).open(questionBank);
}
</script>
