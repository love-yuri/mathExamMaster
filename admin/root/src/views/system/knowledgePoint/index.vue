<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-03 19:00:46
 * @LastEditTime: 2024-10-03 22:54:30
 * @Description: 知识点管理
-->
<template>
  <div>
    <CreateKnowledgePoint />
    <Select :options="options" option-label="name" />
    {{ kp }}
  </div>
</template>
<script setup lang="ts">
import CreateKnowledgePoint from './create/index.vue';
import { onMounted, ref } from 'vue';
import { Select } from '#/components';
import { KnowledgePoint, knowledgePointApi } from '#/api/knowledgePointApi';

const options = ref<KnowledgePoint[]>([]);
const kp = ref<KnowledgePoint>(new KnowledgePoint());

onMounted(() => {
  knowledgePointApi
    .page({
      current: 1,
      size: 20,
    })
    .then((res) => {
      options.value = res.records;
      const op = options.value[0];
      kp.value.copy(op!);
    });
});
</script>
