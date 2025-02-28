<template>
  <div class="p-2">
    <Card>
      <template #content>
        <DataTable :value="pages" scrollable table-style="min-width: 50rem">
          <template #header>
            <div class="flex flex-wrap items-center justify-between gap-2">
              <span class="text-xl font-bold">题库</span>
              <Button
                icon="pi pi-plus"
                raised
                rounded
                @click="
                  router.push({
                    name: 'examPageCreate',
                  })
                "
              />
            </div>
          </template>
          <Column header="试卷标题" style="min-width: 110px">
            <template #body="slotProps: { data: ExamPageResult }">
              <EllipsisText :max-width="500">
                {{ slotProps.data.title }}
              </EllipsisText>
            </template>
          </Column>
          <Column header="试卷类型" style="min-width: 110px">
            <template #body="slotProps: { data: ExamPageResult }">
              <Tag
                :severity="ExamPageTypeColorMap[slotProps.data.type]"
                :value="ExamPageMap[slotProps.data.type]"
              />
            </template>
          </Column>
          <Column header="所属科目" style="min-width: 110px">
            <template #body="slotProps: { data: ExamPageResult }">
              <Tag
                :severity="SubjectTypeColorMap[slotProps.data.subject]"
                :value="SubjectTypeMap[slotProps.data.subject]"
              />
            </template>
          </Column>
          <Column field="releaseTime" header="发布时间" />
          <Column field="action" header="" style="min-width: 110px">
            <template #body="slotProps: { data: ExamPageResult }">
              <SplitButton
                :model="[
                  {
                    label: '编辑',
                    icon: 'pi pi-pencil',
                    command: () => {
                      router.push({
                        name: 'examPageUpdate',
                        params: { id: slotProps.data.id },
                      });
                    },
                  },
                  {
                    label: '删除',
                    icon: 'pi pi-trash',
                    command: () => remove(slotProps.data.id!),
                  },
                  {
                    label: '发布试卷',
                    icon: 'pi pi-trash',
                    command: () =>
                      router.push({
                        name: 'examPageReleasePage',
                        params: { id: slotProps.data.id },
                      }),
                  },
                ]"
                icon="pi pi-eye"
                label="预览"
                raised
                severity="info"
                @click="
                  router.push({
                    name: 'examPageUpdate',
                    params: { id: slotProps.data.id },
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
import type { PageParam } from '@yuri/types';
import type { PageState } from 'primevue/paginator';

import { onMounted, ref } from 'vue';

import { EllipsisText } from '@vben/common-ui';

import { examPageApi, message } from '@yuri/common';
import {
  Button,
  Card,
  Column,
  DataTable,
  DefaultConfirmDialog,
  Paginator,
  SplitButton,
  Tag,
} from '@yuri/components';
import {
  ExamPageMap,
  ExamPageResult,
  ExamPageType,
  SubjectType,
  SubjectTypeMap,
} from '@yuri/types';
import { useConfirm } from 'primevue/useconfirm';

import { router } from '#/router';

const confirm = useConfirm();
const pageParam = ref<PageParam>({
  current: 1,
  size: 10,
  total: 0,
});

const pages = ref<ExamPageResult[]>([]);

const ExamPageTypeColorMap = {
  [ExamPageType.DEFULT]: 'primary',
};

const SubjectTypeColorMap = {
  [SubjectType.HighMath]: 'success',
};

/**
 * 页码改变
 */
function onPage(p: PageState) {
  pageParam.value.current = p.page + 1;
  pageParam.value.size = p.rows;
  loadData();
}

async function loadData() {
  examPageApi.pageSimple(pageParam.value).then((res) => {
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
      examPageApi.delete(id).then(() => {
        message.default.success('删除成功');
        loadData();
      });
    },
    group: 'headless',
    message: '是否要删除该试卷?',
  });
}
</script>
