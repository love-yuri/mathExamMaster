<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-23 21:32:03
 * @LastEditTime: 2025-03-24 16:33:22
 * @Description: 题目分类管理
-->
<template>
  <div class="p-3">
    <Card>
      <template #content>
        <DataTable
          :value="categories"
          scrollable
          table-style="min-width: 50rem"
          striped-rows
          show-gridlines
          :loading="loading"
        >
          <template #header>
            <div class="flex flex-wrap items-center justify-between gap-2">
              <div class="flex items-center gap-2">
                <span class="text-xl font-bold text-blue-700">
                  题目分类管理
                </span>
                <Tag severity="info" value="管理题目的分类信息" />
              </div>
              <div class="flex gap-2">
                <Button
                  icon="pi pi-plus"
                  label="新建分类"
                  raised
                  severity="success"
                  @click="router.push('/question/category/create')"
                />
              </div>
            </div>
          </template>
          <Column field="name" header="分类名称">
            <template #body="{ data }">
              <div class="flex items-center gap-2">
                <EllipsisText :max-width="300">
                  <span class="font-medium">{{ data.name }}</span>
                </EllipsisText>
              </div>
            </template>
          </Column>
          <Column field="description" header="分类描述">
            <template #body="{ data }">
              <EllipsisText :max-width="300">
                {{ data.description || '暂无描述' }}
              </EllipsisText>
            </template>
          </Column>
          <Column field="questionCount" header="题目数量">
            <template #body="{ data }">
              <Badge :value="data.questionCount || 0" severity="info" />
            </template>
          </Column>
          <Column field="updateTime" header="更新时间">
            <template #body="{ data }">
              <div class="flex items-center gap-1">
                <i class="pi pi-calendar text-gray-500"></i>
                <span>{{ data.updateTime }}</span>
              </div>
            </template>
          </Column>
          <Column field="action" header="操作" style="min-width: 150px">
            <template #body="slotProps: { data: QuestionCategory }">
              <div class="flex gap-2">
                <Button
                  icon="pi pi-pencil"
                  rounded
                  severity="info"
                  text
                  tooltip="编辑分类"
                  tooltip-options="{ position: 'top' }"
                  @click="
                    router.push({
                      name: 'questionCategoryUpdate',
                      params: { id: slotProps.data.id },
                    })
                  "
                />
                <Button
                  icon="pi pi-trash"
                  rounded
                  severity="danger"
                  text
                  tooltip="删除分类"
                  tooltip-options="{ position: 'top' }"
                  @click="remove(slotProps.data.id!)"
                />
              </div>
            </template>
          </Column>
          <template #empty>
            <div class="flex flex-col items-center justify-center py-6">
              <i
                class="pi pi-folder-open text-gray-300"
                style="font-size: 3rem"
              ></i>
              <p class="mt-3 text-gray-500">暂无分类数据</p>
              <Button
                class="mt-3"
                icon="pi pi-plus"
                label="创建第一个分类"
                outlined
                severity="info"
                size="small"
                @click="router.push('/question/category/create')"
              />
            </div>
          </template>
        </DataTable>
        <Paginator
          :rows="pageParam.size"
          :rows-per-page-options="[10, 20, 30]"
          :total-records="pageParam.total"
          class="mt-3"
          @page="onPage"
        />
      </template>
    </Card>
    <DefaultConfirmDialog group="headless" />
  </div>
</template>

<script setup lang="ts">
import type { PageParam, QuestionCategory } from '@yuri/types';
import type { PageState } from 'primevue/paginator';

import { onMounted, ref } from 'vue';

import { message, questionCategoryApi } from '@yuri/common';
import {
  Badge,
  Button,
  Card,
  Column,
  DataTable,
  DefaultConfirmDialog,
  EllipsisText,
  Paginator,
  Tag,
} from '@yuri/components';
import { useConfirm } from 'primevue/useconfirm';

import { router } from '#/router';

// 使用确认对话框
const confirm = useConfirm();

// 数据加载状态
const loading = ref(false);

// 分类数据
const categories = ref<QuestionCategory[]>([]);

// 分页参数
const pageParam = ref<PageParam>({
  current: 1,
  size: 10,
  total: 0,
});

/**
 * 页码改变处理
 */
function onPage(p: PageState) {
  pageParam.value.current = p.page + 1;
  pageParam.value.size = p.rows;
  loadData();
}

/**
 * 加载分类数据
 */
async function loadData() {
  loading.value = true;
  try {
    const res = await questionCategoryApi.page(pageParam.value);
    categories.value = res.records;
    pageParam.value.total = res.total;
  } catch {
    message.default.error('加载分类数据失败');
  } finally {
    loading.value = false;
  }
}

// 页面加载时获取数据
onMounted(loadData);

/**
 * 删除分类
 * @param id 分类ID
 */
function remove(id: string) {
  confirm.require({
    accept: () => {
      questionCategoryApi.delete(id).then(() => {
        message.default.success('删除成功');
        loadData();
      });
    },
    acceptClass: 'p-button-danger',
    acceptLabel: '确认删除',
    group: 'headless',
    header: '删除确认',
    icon: 'pi pi-exclamation-triangle',
    message: '确定要删除该分类吗？删除后将无法恢复。',
    rejectLabel: '取消',
  });
}
</script>

<style scoped>
:deep(.p-datatable-header) {
  background-color: #f8fafc;
  border-top-left-radius: 6px;
  border-top-right-radius: 6px;
}

:deep(.p-datatable) {
  border-radius: 6px;
  box-shadow: 0 1px 3px rgb(0 0 0 / 10%);
}
</style>
