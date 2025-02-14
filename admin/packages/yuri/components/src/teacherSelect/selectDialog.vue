<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-13 19:32:27
 * @LastEditTime: 2025-02-14 15:50:06
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
          <Column field="nickname" header="用户名" />
          <Column field="username" header="用户名" />
          <Column>
            <template #body="slotProps: { data: UserResult }">
              <div class="flex items-center">
                <Button
                  :icon="`pi pi-${selectUsers.has(slotProps.data.id!) ? 'minus' : 'plus'}`"
                  :label="`${selectUsers.has(slotProps.data.id!) ? '删除' : '选择'}`"
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
      </template>
    </Card>
  </div>
</template>

<script setup lang="ts">
import { inject, onMounted, ref } from 'vue';
import { Button, Card, Column, DataTable } from '@yuri/components';

import type { DynamicDialogInstance } from 'primevue/dynamicdialogoptions';
import { userApi } from '@yuri/common';
import type { UserResult } from '@yuri/types';

const dialogRef = inject<{ value: DynamicDialogInstance }>('dialogRef');
const users = ref<UserResult[]>([]);
const selectUsers = ref<Map<string, UserResult>>(new Map());

async function loadData() {
  userApi.teachers().then((res) => {
    users.value = res;
    if (dialogRef?.value.data.teacherId !== '') {
      users.value.forEach((user) => {
        if (user.id == dialogRef?.value.data.teacherId) {
          selectUsers.value.set(user.id!, user);
        }
      })
    }
  });
}
// loadData();

async function add(v: UserResult) {
  if (selectUsers.value.size >= 1) {
    selectUsers.value.clear();
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
