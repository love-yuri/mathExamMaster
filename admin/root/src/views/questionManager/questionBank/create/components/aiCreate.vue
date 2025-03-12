<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-03-11 10:34:46
 * @LastEditTime: 2025-03-12 16:25:06
 * @Description: 
-->

<template>
  <Modal>
    <div class="p-1">
      <Textarea v-model="text" :rows="10" placeholder="请输入问题" />
    </div>
  </Modal>
</template>

<script setup lang="ts">
import type { QuestionBank } from '@yuri/types';

import { ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { Textarea } from '@yuri/components';

type FuncType = <T extends QuestionBank>(q: T) => void;
const text = ref('');
const callback = ref<FuncType>();

const [Modal, modalApi] = useVbenModal({
  fullscreen: false,
  title: 'AI生成题目',
});

function open(func: FuncType) {
  callback.value = func;
  modalApi.open();
}

defineExpose({ open });
</script>
