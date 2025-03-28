<template>
  <Modal>
    <div class="min-h-[500px] p-3">
      {{ selectedCategoryIds }}
      <DataTable
        v-model:selection="selectedCategories"
        :loading="loading"
        :value="categories"
        class="mb-3"
        data-key="description"
        scrollable
        selection-mode="multiple"
        show-gridlines
        striped-rows
      >
        <Column selection-mode="multiple" style="width: 3rem" />
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
        <template #empty>
          <div class="flex flex-col items-center justify-center py-6">
            <i
              class="pi pi-folder-open text-gray-300"
              style="font-size: 3rem"
            ></i>
            <p class="mt-3 text-gray-500">暂无分类数据</p>
          </div>
        </template>
      </DataTable>

      <Paginator
        :rows="pageParam.size"
        :rows-per-page-options="[10, 20, 30]"
        :total-records="pageParam.total"
        @page="onPage"
      />
    </div>
  </Modal>
</template>

<script setup lang="ts">
import type { PageParam, QuestionCategory } from '@yuri/types';
import type { PageState } from 'primevue/paginator';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { message, questionCategoryApi } from '@yuri/common';
import {
  Badge,
  Column,
  DataTable,
  EllipsisText,
  Paginator,
} from '@yuri/components';

// 数据加载状态
const loading = ref(false);
const searchText = ref('');

// 分类数据
const categories = ref<QuestionCategory[]>([]);
const selectedCategories = ref<QuestionCategory[]>([]);

const selectedCategoryIds = computed({
  get: () => selectedCategories.value.map((item) => item.id!!),
  set: (ids) => {
    selectedCategories.value = categories.value.filter((item) =>
      ids.includes(item.id!!),
    );
  },
});

// 回调函数
let callbackFn: ((result: string[]) => void) | null = null;

// 分页参数
const pageParam = ref<PageParam>({
  current: 1,
  size: 10,
  total: 0,
});

const [Modal, modalApi] = useVbenModal({
  fullscreen: false,
  onConfirm: confirm,
  title: '选择题目分类',
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
    const params = {
      ...pageParam.value,
      name: searchText.value,
    };
    const res = await questionCategoryApi.page(params);
    categories.value = res.records;
    pageParam.value.total = res.total;
  } catch {
    message.default.error('加载分类数据失败');
  } finally {
    loading.value = false;
  }
}

/**
 * 确认选择
 */
function confirm() {
  if (callbackFn) {
    callbackFn(selectedCategoryIds.value);
  }
  modalApi.close();
}

/**
 * 打开分类选择对话框
 * @param currentCategoryIds 当前已选分类ID数组
 * @param callback 选择完成后的回调函数
 */
function open(
  currentCategoryIds: string[],
  callback: (result: string[]) => void,
) {
  selectedCategoryIds.value = [...currentCategoryIds];
  callbackFn = callback;
  modalApi.open();
  loadData();
}

defineExpose({
  open,
});
</script>
