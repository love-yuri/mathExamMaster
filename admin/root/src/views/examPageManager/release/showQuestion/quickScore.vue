<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-28 14:24:42
 * @LastEditTime: 2025-03-03 16:11:16
 * @Description: 
-->
<template>
  <div>
    <Model :overlay-blur="10">
      <div class="flex flex-wrap justify-center gap-3 p-4">
        <h3 class="mb-4 w-full text-center text-lg font-semibold">
          请选择分数
        </h3>
        <Button :key="0" label="0 分" @click="selectScore(0)" outlined />
        <template
          v-if="
            detail?.type === QuestionTypeEnum.JUDGE ||
            detail?.type === QuestionTypeEnum.MULTIPLE_CHOICE ||
            detail?.type === QuestionTypeEnum.SINGLE_CHOICE
          "
        >
          <Button
            :key="0"
            :label="`满分 ${detail?.totalScore} 分`"
            @click="selectScore(detail?.totalScore)"
            outlined
          />
        </template>
        <template v-else>
          <Button
            v-for="score in detail?.totalScore"
            :key="score"
            :label="`${score} 分`"
            @click="selectScore(score)"
            outlined
          />
        </template>
      </div>
    </Model>
  </div>
</template>

<script setup lang="ts">
import type { UserScoreDetail } from '@yuri/types';

import { ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { Button } from '@yuri/components';
import { QuestionTypeEnum } from '@yuri/types';

const emits = defineEmits(['select']);

/* 处理评分弹窗 */
const [Model, modelApi] = useVbenModal({
  onConfirm() {
    modelApi.close();
  },
  title: '快速评分',
});

const detail = ref<UserScoreDetail>();

const selectScore = (score: number) => {
  emits('select', score);
  modelApi.close();
};

const open = (data: UserScoreDetail) => {
  detail.value = data;
  modelApi.open();
};

defineExpose({ open });
</script>
