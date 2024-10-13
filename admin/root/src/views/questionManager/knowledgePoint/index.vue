<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-03 19:00:46
 * @LastEditTime: 2024-10-13 16:42:44
 * @Description: 知识点管理
-->
<template>
  <div class="p-2">
    <Card>
      <template #content>
        <DataTable :value="knowledgePoints" table-style="min-width: 50rem">
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
          <Column field="updateTime" header="创建时间" />
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
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { Button, Card, Column, DataTable, Paginator } from '#/components';
import { type PageParam } from '#/common/base/baseApi/baseApi';
import type { PageState } from 'primevue/paginator';
import {
  type KnowledgePoint,
  knowledgePointApi,
} from '#/api/knowledgePointApi';
import { router } from '#/router';

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
</script>
