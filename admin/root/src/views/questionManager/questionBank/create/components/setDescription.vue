<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-03-11 10:34:46
 * @LastEditTime: 2025-03-11 19:47:14
 * @Description: 
-->

<template>
  <Modal>
    <div class="p-1">
      <WangEditor v-model:content="content" placeholder="题目描述..." />
    </div>
  </Modal>
</template>

<script setup lang="ts">
import { watch } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { WangEditor } from '@yuri/components';

const content = defineModel<string | undefined>('content', { required: true });
const show = defineModel<boolean>('show', { required: true });

watch(show, () => {
  if (show.value) {
    modalApi.open();
  }
});

const [Modal, modalApi] = useVbenModal({
  fullscreen: false,
  onCancel: () => {
    modalApi.close();
  },
  onClosed: () => {
    show.value = false;
  },
  onConfirm: () => {
    modalApi.close();
  },
  title: '添加题目描述',
});
</script>
