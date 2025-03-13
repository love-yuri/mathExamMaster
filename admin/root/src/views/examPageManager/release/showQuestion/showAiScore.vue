<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-03-11 10:34:46
 * @LastEditTime: 2025-03-13 19:21:08
 * @Description: 
-->

<template>
  <Modal>
    <div class="p-1">
      <div class="flex items-center">
        用户得分:
        <span class="ml-1 text-[24px] font-bold text-blue-500">{{
          score
        }}</span>
      </div>
      <WangEditor
        :hide-toolbar-config="true"
        v-model:content="content"
        placeholder="ai评分详情..."
      />
    </div>
  </Modal>
</template>

<script setup lang="ts">
import type { AiCreateScoreResult } from '@yuri/types';

import { ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { WangEditor } from '@yuri/components';

const score = ref<number>(0);
const content = ref<string>('');

const [Modal, modalApi] = useVbenModal({
  fullscreen: false,
  onConfirm: () => {
    modalApi.close();
  },
  title: 'Ai评分详情',
});

function open(res: AiCreateScoreResult) {
  score.value = res.score;
  content.value = res.detail;
  modalApi.open();
}

defineExpose({ open });
</script>
