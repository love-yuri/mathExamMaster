<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-03 19:00:46
 * @LastEditTime: 2025-02-08 15:04:49
 * @Description: 知识点管理
-->
<template>
  <div class="p-2">
    <Card>
      <template #content>
        <DataTable
          :value="knowledgePoints"
          scrollable
          table-style="min-width: 50rem"
        >
          <template #header>
            <div class="flex flex-wrap items-center justify-between gap-2">
              <span class="text-xl font-bold">知识点</span>
              <Button
                icon="pi pi-plus"
                raised
                rounded
                @click="router.push('/knowledge/point/create')"
              />
            </div>
          </template>
          <Column field="name" header="知识点名称" />
          <Column field="description" header="知识点描述" />
          <Column field="updateTime" header="更新时间" />
          <Column field="action" header="" style="min-width: 110px">
            <template #body="slotProps: { data: KnowledgePoint }">
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
                label="编辑"
                raised
                severity="info"
                @click="edit(slotProps.data)"
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
    <Update ref="updateModelRef" @update="loadData" />
    <DefaultConfirmDialog group="headless" />
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, unref } from 'vue';
import {
  Button,
  Card,
  Column,
  DataTable,
  DefaultConfirmDialog,
  Paginator,
  SplitButton,
} from '@yuri/components';
import type { PageState } from 'primevue/paginator';
import { router } from '#/router';
import Update from './components/update.vue';

import { useConfirm } from 'primevue/useconfirm';
import { knowledgePointApi, message } from '@yuri/common';
import type { KnowledgePoint, PageParam } from '@yuri/types';

const confirm = useConfirm();

/**
 * 处理数据分页
 */
const knowledgePoints = ref<KnowledgePoint[]>([]);

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
  const res = await knowledgePointApi.page(pageParam.value);
  knowledgePoints.value = res.records;
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
      knowledgePointApi.delete(id).then(() => {
        message.default.success('删除成功');
        loadData();
      });
    },
    group: 'headless',
    message: '是否要删除该知识点?',
  });
}

/**
 * 编辑
 */
const updateModelRef = ref();
async function edit(knowledgePoint: KnowledgePoint) {
  unref(updateModelRef).open(knowledgePoint);
}
</script>
