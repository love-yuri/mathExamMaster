<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-13 19:32:27
 * @LastEditTime: 2024-12-13 21:12:41
 * @Description: 用户选择组件
-->
<template>
  <div class="p-2">
    <Card>
      <template #content>
        <DataTable :value="users" scrollable table-style="min-width: 50rem">
          <!-- <template #header>
            <div class="flex flex-wrap items-center justify-between gap-2">
              <span class="text-xl font-bold">题库</span>
              <Button
                icon="pi pi-plus"
                raised
                rounded
                @click="router.push('/question/bank/create')"
              />
            </div>
          </template> -->
          <Column field="username" header="用户名" />
          <Column field="id" header="用户id" />
          <Column>
            <template #body="slotProps: { data: UserResult }">
              <div class="flex items-center">
                <Button
                  :icon="`pi pi-${selectUsers.has(slotProps.data.id!) ? 'minus' : 'plus'}`"
                  :label="`${selectUsers.has(slotProps.data.id!) ? '删除' : '添加'}`"
                  :severity="`${selectUsers.has(slotProps.data.id!) ? 'danger' : 'info'}`"
                  raised
                  @click="add(slotProps.data)"
                />
              </div>
            </template>
          </Column>
        </DataTable>
        <div class="mt-2 flex justify-between">
          <Button label="取消" severity="secondary" @click="cancel" />
          <Button label="确认" @click="confirm" />
        </div>
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
import { inject, onMounted, ref } from 'vue';
import { Button, Card, Column, DataTable, Paginator } from '#/components';

import type { PageState } from 'primevue/paginator';
import { userApi, type UserPageParam, type UserResult } from '#/api/userApi';
import type { DynamicDialogInstance } from 'primevue/dynamicdialogoptions';

const dialogRef = inject<{ value: DynamicDialogInstance }>('dialogRef');
const users = ref<UserResult[]>([]);
const selectUsers = ref<Map<string, UserResult>>(new Map());
const pageParam = ref<UserPageParam>({
  current: 1,
  size: 10,
  total: 0,
  studentFlag: 2,
});

/**
 * 页码改变
 */
function onPage(p: PageState) {
  pageParam.value.current = p.page + 1;
  pageParam.value.size = p.rows;
  loadData();
}

async function loadData() {
  dialogRef?.value.data.currentUsers.forEach((v: UserResult) => {
    selectUsers.value.set(v.id!, v);
  });
  userApi.resultPage(pageParam.value).then((res) => {
    users.value = res.records;
    pageParam.value.total = res.total;
  });
}
// loadData();

async function add(v: UserResult) {
  const has = selectUsers.value.has(v.id!);
  if (has) {
    selectUsers.value.delete(v.id!);
  } else {
    selectUsers.value.set(v.id!, v);
  }
}

function cancel() {
  dialogRef?.value.close();
}

function confirm() {
  dialogRef?.value.data?.onConfirm([...selectUsers.value.values()]);
  dialogRef?.value.close();
}

onMounted(loadData);
</script>
