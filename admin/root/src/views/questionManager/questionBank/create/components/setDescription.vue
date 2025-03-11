<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-03-11 10:34:46
 * @LastEditTime: 2025-03-11 11:04:16
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
import { watchEffect } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { WangEditor } from '@yuri/components';

const content = defineModel<string>('content', {
  default: '',
  required: false,
});
const show = defineModel('show', { required: true, type: Boolean });

watchEffect(() => {
  if (show.value) {
    modalApi.open();
  }
});

const [Modal, modalApi] = useVbenModal({
  fullscreen: true,
  onCancel: () => {
    content.value = '';
    modalApi.close();
  },
  onConfirm: () => modalApi.close(),
  title: '选择题目',
});
</script>
