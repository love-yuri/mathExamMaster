<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-03-08 21:41:39
 * @LastEditTime: 2025-03-08 23:40:11
 * @Description: 
-->
<template>
  <div>
    <Card class="rounded-lg bg-white p-4 shadow-md">
      <template #content>
        <h2 class="mb-4 text-xl font-semibold text-gray-800">
          📊 每道题目统计信息
        </h2>

        <DataTable :value="data" class="p-datatable-sm">
          <Column field="index" header="题号" />
          <Column header="题目类型" style="min-width: 110px">
            <template #body="slotProps: { data: QuestionStatistic }">
              <Tag
                :severity="QuestionTypeColorMap[slotProps.data.type]"
                :value="QuestionTypeMap[slotProps.data.type]"
              />
            </template>
          </Column>
          <Column field="minScore" header="班级最低分" />
          <Column field="maxScore" header="班级最高分" />
          <Column field="totalScore" header="满分" />
          <Column field="correctCount" header="正确人数" />
          <Column field="scoreRate" header="得分率 (%)">
            <template #body="slotProps: { data: QuestionStatistic }">
              <span> {{ (slotProps.data.scoreRate * 100).toFixed(1) }}% </span>
            </template>
          </Column>
          <Column>
            <template #body="slotProps: { data: QuestionStatistic }">
              <Button
                @click="show(slotProps.data.questionId)"
                label="预览题目"
                icon="pi pi-eye"
              />
            </template>
          </Column>
        </DataTable>
      </template>
    </Card>
    <Preview ref="previewRef" />
  </div>
</template>
<script setup lang="ts">
import type { QuestionStatistic } from '@yuri/types';

import { defineProps, unref, useTemplateRef } from 'vue';

import { questionBankApi } from '@yuri/common';
import { Button, Card, Column, DataTable, Tag } from '@yuri/components';
import { QuestionTypeColorMap, QuestionTypeMap } from '@yuri/types';

import Preview from '#/views/questionManager/questionBank/components/preview.vue';

// 接收父组件传来的数据
defineProps<{
  data: QuestionStatistic[];
}>();

const previewRef = useTemplateRef('previewRef');
function show(id: string) {
  questionBankApi.get(id).then((res) => {
    unref(previewRef)?.open(res);
  });
}
</script>

<style scoped>
/* 适当调整 DataTable 样式 */
:deep(.p-datatable) {
  overflow: hidden;
  border-radius: 10px;
}
</style>
