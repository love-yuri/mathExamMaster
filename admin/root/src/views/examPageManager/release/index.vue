<template>
  <div class="p-2">
    <Card>
      <template #content>
        <DataTable :value="pages" scrollable table-style="min-width: 50rem">
          <template #header>
            <div class="flex flex-wrap items-center justify-between gap-2">
              <span class="text-xl font-bold">试卷列表</span>
              <div class="flex items-center">
                <SelectButton
                  class="mr-2"
                  @value-change="loadData"
                  v-model="flag"
                  :options="flagOptions"
                  data-key="value"
                  option-label="name"
                  option-value="value"
                />
                <Button
                  icon="pi pi-plus"
                  raised
                  rounded
                  @click="
                    router.push({
                      name: 'examPage',
                    })
                  "
                />
              </div>
            </div>
          </template>
          <Column header="试卷标题" style="min-width: 110px">
            <template #body="slotProps: { data: ExamPageReleaseResult }">
              <EllipsisText :max-width="500">
                {{ slotProps.data.examPage?.title }}
              </EllipsisText>
            </template>
          </Column>
          <Column field="createTime" header="发布时间" />
          <Column field="startTime" header="开始时间" />
          <Column field="endTime" header="结束时间" />
          <Column header="发布班级">
            <template #body="slotProps: { data: ExamPageReleaseResult }">
              <EllipsisText :max-width="500">
                <Tag
                  :value="slotProps.data.department.name"
                  class="mx-1 my-1 flex-shrink-0"
                />
              </EllipsisText>
            </template>
          </Column>
          <Column field="action" header="" style="min-width: 110px">
            <template #body="slotProps: { data: ExamPageReleaseResult }">
              <SplitButton
                :model="[
                  {
                    visible: flag === 1,
                    label: '阅卷',
                    icon: 'pi pi-sparkles',
                    command: () => {
                      router.push({
                        name: 'examPageReleaseReviewing',
                        params: {
                          id: slotProps.data.id,
                        },
                      });
                    },
                  },
                  {
                    visible: flag === 3,
                    label: '编辑',
                    icon: 'pi pi-pencil',
                    command: () => {
                      router.push({
                        name: 'examPageReleasePageUpdate',
                        params: {
                          id: slotProps.data.id,
                        },
                      });
                    },
                  },
                  {
                    visible: flag === 3,
                    label: '删除',
                    icon: 'pi pi-trash',
                    command: () => remove(slotProps.data.id!),
                  },
                ]"
                icon="pi pi-eye"
                label="预览试卷"
                raised
                severity="info"
                @click="
                  router.push({
                    name: 'examPageUpdate',
                    params: { id: slotProps.data.examPage?.id!! },
                  })
                "
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
    <DefaultConfirmDialog group="headless" />
  </div>
</template>
<script setup lang="ts">
import type {
  ExamPageReleasePageParam,
  ExamPageReleaseResult,
} from '@yuri/types';
import type { PageState } from 'primevue/paginator';

import { onMounted, ref } from 'vue';

import { EllipsisText } from '@vben/common-ui';
import { useTabs } from '@vben/hooks';

import { examPageReleaseApi, message } from '@yuri/common';
import {
  Button,
  Card,
  Column,
  DataTable,
  DefaultConfirmDialog,
  Paginator,
  SelectButton,
  SplitButton,
  Tag,
} from '@yuri/components';
import { useConfirm } from 'primevue/useconfirm';

import { router } from '#/router';

const { closeTabByKey } = useTabs();

const flagOptions = [
  { name: '全部', value: 0 },
  { name: '已结束', value: 1 },
  { name: '未结束', value: 2 },
  { name: '未开始', value: 3 },
];
const confirm = useConfirm();
const flag = ref(0);
const pageParam = ref<ExamPageReleasePageParam>({
  current: 1,
  flag: flag.value,
  size: 10,
});

const pages = ref<ExamPageReleaseResult[]>([]);

/**
 * 页码改变
 */
function onPage(p: PageState) {
  pageParam.value.current = p.page + 1;
  pageParam.value.size = p.rows;
  loadData();
}

async function loadData() {
  pageParam.value.flag = flag.value;
  examPageReleaseApi.pageSimple(pageParam.value).then((res) => {
    pages.value = res.records;
    pageParam.value.total = res.total;
  });
}

onMounted(loadData);

/**
 * 删除
 * @param id 题目id
 */
function remove(id: string) {
  confirm.require({
    accept: () => {
      examPageReleaseApi.delete(id).then(() => {
        message.default.success('删除成功');
        closeTabByKey(`/exam/page/release/page/update/${id}`);
        loadData();
      });
    },
    group: 'headless',
    message: '是否要删除该试卷?',
  });
}
</script>
