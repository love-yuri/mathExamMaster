<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-03-15 10:34:46
 * @LastEditTime: 2025-03-15 19:47:14
 * @Description: 
-->

<template>
  <Modal>
    <div class="p-1">
      <WangEditor
        :hide-toolbar-config="true"
        v-model:content="content"
        placeholder="题目描述..."
      />
    </div>
  </Modal>
</template>

<script setup lang="ts">
import { ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { WangEditor } from '@yuri/components';

const content = ref('');

const [Modal, modalApi] = useVbenModal({
  fullscreen: false,
  onConfirm: () => {
    modalApi.close();
  },
  title: '题目描述',
});

function open(desc: string) {
  content.value = desc;
  modalApi.open();
}

defineExpose({
  open,
});
</script>
