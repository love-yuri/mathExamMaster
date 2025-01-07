<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-17 18:08:12
 * @LastEditTime: 2025-01-07 18:41:31
 * @Description: 默认确认框
-->
<template>
  <ConfirmDialog :group="group">
    <template #container="{ message, acceptCallback, rejectCallback }">
      <div
        class="bg-surface-0 dark:bg-surface-900 flex flex-col items-center rounded p-8"
      >
        <p class="mb-0">{{ message.message }}</p>
        <div class="mt-6 flex items-center gap-2">
          <Button
            :label="`${confirmText ? confirmText : '取消'} `"
            class="w-32"
            icon="pi pi-times"
            outlined
            severity="secondary"
            @click="rejectCallback"
          />
          <Button
            :label="`${cancelText ? cancelText : '删除'} `"
            class="w-32"
            icon="pi pi-trash"
            severity="danger"
            @click="acceptCallback"
          />
        </div>
      </div>
    </template>
  </ConfirmDialog>
</template>
<script setup lang="ts">
import { Button, ConfirmDialog } from '#/components';

defineProps<{
  cancelText?: string;
  confirmText?: string;
  group: string;
}>();
</script>
