<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 19:57:12
 * @LastEditTime: 2024-10-18 22:12:01
 * @Description: 创建题目
-->
<template>
  <div v-if="type" class="p-2">
    <component
      :is="Component"
      ref="componetRef"
      @cancel="cancel"
      @update="cancel"
    />
  </div>
</template>
<script setup lang="ts">
import {
  QuestionBank,
  questionBankApi,
  QuestionTypeEnum,
} from '#/api/questionBankApi';
import SingleChoice from '#/views/questionManager/questionBank/create/components/singleChoice.vue';
import MultipleChoice from '#/views/questionManager/questionBank/create/components/multipleChoice.vue';
import Judge from '#/views/questionManager/questionBank/create/components/judge.vue';
import GapFilling from '#/views/questionManager/questionBank/create/components/gapFilling.vue';
import Subjective from '#/views/questionManager/questionBank/create/components/subjective.vue';

import { computed, markRaw, nextTick, onMounted, ref, unref } from 'vue';
import type { KnowledgePoint } from '#/api/knowledgePointApi';
import { router, useRoute } from '#/router';
import { useTabs } from '@vben/hooks';

defineEmits(['update']);
const route = useRoute();

// 定义 ComponentMap，并使用 markRaw 包装组件
const ComponentMap: Record<QuestionTypeEnum, ReturnType<typeof markRaw>> = {
  [QuestionTypeEnum.GAP_FILLING]: markRaw(GapFilling),
  [QuestionTypeEnum.JUDGE]: markRaw(Judge),
  [QuestionTypeEnum.MULTIPLE_CHOICE]: markRaw(MultipleChoice),
  [QuestionTypeEnum.SINGLE_CHOICE]: markRaw(SingleChoice),
  [QuestionTypeEnum.SUBJECTIVE]: markRaw(Subjective),
};

const { closeCurrentTab } = useTabs();
const componetRef = ref<{
  openAsUpdate: (v: QuestionBank, k: KnowledgePoint[]) => void;
}>();
const type = ref<QuestionTypeEnum>();
const Component = computed(() => ComponentMap[type.value!]);

interface OpenProps {
  data?: any;
  knowledgePoints: KnowledgePoint[];
  type: QuestionTypeEnum;
}

async function open(value: OpenProps) {
  type.value = value.type;
  await nextTick();
  unref(componetRef)?.openAsUpdate(value.data, value.knowledgePoints);
}

defineExpose({ open });

onMounted(async () => {
  const id = route.params.id as string;
  const value = await questionBankApi.detail(id);
  type.value = value.questionBank.type;
  await nextTick();
  unref(componetRef)?.openAsUpdate(value.questionBank, value.knowledgePoints);
});

function cancel() {
  closeCurrentTab();
  router.push(`/question/bank/`);
}
</script>
