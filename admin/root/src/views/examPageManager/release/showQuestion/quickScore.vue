<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-28 14:24:42
 * @LastEditTime: 2025-02-28 14:38:58
 * @Description: 
-->
<template>
  <div>
    <Model :overlay-blur="10">
      <div class="flex flex-wrap justify-center gap-3 p-4">
        <h3 class="mb-4 w-full text-center text-lg font-semibold">
          请选择分数
        </h3>
        <Button
          v-for="score in detail?.totalScore"
          :key="score"
          :label="`${score} 分`"
          @click="selectScore(score)"
          outlined
        />
      </div>
    </Model>
  </div>
</template>

<script setup lang="ts">
import type { UserScoreDetail } from '@yuri/types';

import { ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { Button } from '@yuri/components';

/* 处理评分弹窗 */
const [Model, modelApi] = useVbenModal({
  onConfirm() {
    modelApi.close();
  },
  title: '快速评分',
});

const detail = ref<UserScoreDetail>();

const selectScore = (score: number) => {
  modelApi.close();
};

const open = (data: UserScoreDetail) => {
  detail.value = data;
  modelApi.open();
};

defineExpose({ open });
</script>
