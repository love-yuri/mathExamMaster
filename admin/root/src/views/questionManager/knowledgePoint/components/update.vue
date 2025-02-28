<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-13 15:04:45
 * @LastEditTime: 2025-02-27 19:13:17
 * @Description: 预览题目
-->
<template>
  <Model width="800">
    <div class="p-2">
      <h1 class="mt-3 text-center text-[24px]">修改知识点...</h1>
      <InputText
        v-model="knowledgePoint.name"
        class="mt-2 w-full"
        placeholder="知识点名称"
      />
      <Textarea
        v-model="knowledgePoint.description"
        :rows="5"
        class="mt-4 w-full"
        placeholder="请输入知识点描述"
      />
    </div>
  </Model>
</template>
<script setup lang="ts">
import { ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { checkEmpty, checkSuccess, knowledgePointApi } from '@yuri/common';
import { InputText, Textarea } from '@yuri/components';
import { KnowledgePoint } from '@yuri/types';

const emits = defineEmits(['update']);

/* 处理预览弹窗 */
const [Model, modelApi] = useVbenModal({
  confirmText: '修改知识点',
  onConfirm: updateKnowledgePoint,
  title: '修改知识点',
});

const knowledgePoint = ref<KnowledgePoint>(new KnowledgePoint());

async function updateKnowledgePoint() {
  checkEmpty(knowledgePoint.value.name, '知识点名称不能为空!');
  checkEmpty(knowledgePoint.value.description, '知识点描述不能为空!');
  await checkSuccess(
    knowledgePointApi.update(knowledgePoint.value),
    false,
    '知识点',
    () => {
      modelApi.close();
      emits('update');
    },
  );
  // knowledgePoint.value.reset();
}

/**
 * 导出打开预览
 * 需要传入题目数据
 */
function open(value: KnowledgePoint) {
  modelApi.open();
  knowledgePoint.value.copy(value);
}

defineExpose({ open });
</script>
