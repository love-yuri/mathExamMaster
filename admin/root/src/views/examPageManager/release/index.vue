<template>
  <div class="p-2">
    <Card>
      <template #content>
        <DataTable :value="pages" scrollable table-style="min-width: 50rem">
          <template #header>
            <div class="flex flex-wrap items-center justify-between gap-2">
              <span class="text-xl font-bold">试卷列表</span>
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
          <Column header="发布学生">
            <template #body="slotProps: { data: ExamPageReleaseResult }">
              <EllipsisText :max-width="500">
                <Tag
                  v-for="item in slotProps.data.users"
                  :key="item.id"
                  :value="item.username"
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
import { onMounted, ref } from 'vue';
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
import type { PageState } from 'primevue/paginator';
import { useConfirm } from 'primevue/useconfirm';
import { router } from '#/router';
import { EllipsisText } from '@vben/common-ui';
import { useTabs } from '@vben/hooks';
import { examPageReleaseApi } from '@yuri/common';
import type { PageParam, ExamPageReleaseResult } from '@yuri/types';
import { message } from '@yuri/common';

const { closeTabByKey } = useTabs();

const confirm = useConfirm();
const pageParam = ref<PageParam>({
  current: 1,
  size: 10,
  total: 0,
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
