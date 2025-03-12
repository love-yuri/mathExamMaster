<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-03-11 10:34:46
 * @LastEditTime: 2025-03-12 21:01:03
 * @Description: 
-->

<template>
  <Modal>
    <div class="p-1" v-loading="loading">
      <Textarea
        v-model="text"
        class="w-full"
        :rows="10"
        placeholder="请输入问题描述..."
      />
      <div class="mt-3 flex items-center justify-center">
        <Button
          label="立即生成"
          @click="generate"
          class="h-12 w-64 !text-[24px]"
        />
      </div>
    </div>
  </Modal>
</template>

<script setup lang="ts">
import type { QuestionBank } from '@yuri/types';

import { ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { message, systemApi } from '@yuri/common';
import { Button, Textarea } from '@yuri/components';
import { QuestionTypeEnum } from '@yuri/types';

const { type } = defineProps<{
  type: QuestionTypeEnum;
}>();

type FuncType = <T extends QuestionBank>(q: T) => void;
const text = ref('');
const loading = ref(false);
const callback = ref<FuncType>();

const [Modal, modalApi] = useVbenModal({
  closable: false,
  fullscreen: false,
  onCancel: () => {
    if (loading.value) {
      message.default.error('请等待生成完成!!');
    } else {
      modalApi.close();
    }
  },
  showConfirmButton: false,
  title: 'AI生成题目',
});

function open(func: FuncType) {
  callback.value = func;
  modalApi.open();
}

async function generate() {
  if (!text.value) {
    message.default.error('请输入问题描述!!');
  }
  loading.value = true;
  const res = await systemApi.aiCreateQuestion({
    description: text.value,
    type,
  });
  loading.value = false;
  if (!res) {
    message.default.error('生成失败, 请稍后再试!!');
    return;
  }
  message.default.success('生成成功!!');
  modalApi.close();
  if (callback.value) {
    callback.value(res);
  }
}

defineExpose({ open });
</script>
