<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-03 19:25:03
 * @LastEditTime: 2024-10-06 19:56:00
 * @Description: 创建知识点的页面
-->
<template>
  <div class="p-2">
    <h1 class="mt-3 text-center text-[24px]">创建一个知识点...</h1>
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
    <Button
      class="mt-4"
      label="创建知识点"
      security="success"
      @click="createKnowledgePoint"
    />
  </div>
</template>
<script setup lang="ts">
import { Button, InputText, Textarea } from '#/components';
import { KnowledgePoint, knowledgePointApi } from '#/api/knowledgePointApi';
import { ref } from 'vue';
import { checkEmpty, checkSuccess } from '#/common/utils/valueCheck';

const knowledgePoint = ref<KnowledgePoint>(new KnowledgePoint());

async function createKnowledgePoint() {
  checkEmpty(knowledgePoint.value.name, '知识点名称不能为空!');
  checkEmpty(knowledgePoint.value.description, '知识点描述不能为空!');
  await checkSuccess(
    knowledgePointApi.create(knowledgePoint.value),
    true,
    '知识点',
  );
  // knowledgePoint.value.reset();
}
</script>
