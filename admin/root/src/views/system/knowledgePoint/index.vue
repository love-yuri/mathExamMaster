<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-03 19:00:46
 * @LastEditTime: 2024-10-04 21:57:09
 * @Description: 知识点管理
-->
<template>
  <div class="p-2">
    <CreateKnowledgePoint />
    <Select
      v-model="knowledgePoint"
      :options="options"
      class="w-full"
      option-label="name"
      placeholder="请选择知识点"
    />
  </div>
</template>
<script setup lang="ts">
import CreateKnowledgePoint from './create/index.vue';
import { onMounted, ref } from 'vue';
import { Select } from '#/components';
import { KnowledgePoint, knowledgePointApi } from '#/api/knowledgePointApi';

const options = ref<KnowledgePoint[]>([]);
const knowledgePoint = ref<KnowledgePoint>();

onMounted(() => {
  knowledgePointApi
    .page({
      current: 1,
      size: 20,
    })
    .then((res) => {
      options.value = res.records;
    });
});
</script>
