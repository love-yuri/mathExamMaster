<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-13 19:30:28
 * @LastEditTime: 2025-02-12 19:35:10
 * @Description: 
-->

<template>
  <div class="">
    <DynamicDialog />
  </div>
</template>

<script setup lang="ts">
import { DynamicDialog } from '@yuri/components';
import { defineAsyncComponent, provide, ref } from 'vue';
import { useDialog } from 'primevue/usedialog';
import type { DynamicDialogInstance } from 'primevue/dynamicdialogoptions';
import type { UserResult } from '@yuri/types';

const DialogView = defineAsyncComponent(() => import('./selectDialog.vue'));
const dialog = useDialog();

const dialogRef = ref<DynamicDialogInstance>();
provide('dialogRef', dialogRef);

type CallBackFunc = (result: UserResult[]) => void;

const show = (onConfirm: CallBackFunc) => {
  dialogRef.value = dialog.open(DialogView, {
    data: {
      onConfirm,
    },
    props: {
      header: '选择教师',
      modal: true,
      style: {
        width: '80vw'
      },
    },
  });
};

defineExpose<{
  show: ( callBack: CallBackFunc) => void;
}>({
  show,
});
</script>
