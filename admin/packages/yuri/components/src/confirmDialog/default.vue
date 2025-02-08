<template>
  <ConfirmDialog :group="group">
    <template #container="{ message, acceptCallback, rejectCallback }">
      <div
        class="bg-surface-0 dark:bg-surface-900 flex flex-col items-center rounded p-8"
      >
        <p class="mb-0">{{ message.message }}</p>
        <div class="mt-6 flex items-center gap-2">
          <Button
            class="w-32"
            icon="pi pi-times"
            label="取消"
            outlined
            severity="secondary"
            @click="rejectCallback"
          />
          <Button
            class="w-32"
            icon="pi pi-trash"
            label="删除"
            severity="danger"
            @click="acceptCallback"
          />
        </div>
      </div>
    </template>
  </ConfirmDialog>
</template>
<script setup lang="ts">
import { Button, ConfirmDialog } from '@yuri/components';

defineProps<{
  group: string;
}>();
</script>
