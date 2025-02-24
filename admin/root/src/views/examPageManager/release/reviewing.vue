<template>
  <div class="flex flex-row p-2">
    <div class="flex w-[200px] flex-col rounded-sm border-2 p-2">
      <Button
        @click="currentStudent = student.userId"
        :severity="currentStudent == student.userId ? 'primary' : 'secondary'"
        class="mb-2 w-full"
        v-for="student in students"
        :label="student.nickname"
        :key="student.userId"
      />
    </div>
    <div class="flex-grow"></div>
  </div>
</template>
<script setup lang="ts">
import type { StudentDetailResult } from '@yuri/types';
import { Button } from '@yuri/components';
import { computed, ref, watchEffect } from 'vue';
import { useRoute } from 'vue-router';
import { examPageReleaseApi } from '@yuri/common';

const route = useRoute();
const currentStudent = ref('');
const releaseId = computed(() => route.params.id as string);
const students = ref<StudentDetailResult[]>([]);

watchEffect(async () => {
  students.value = await examPageReleaseApi.studentDetail(releaseId.value);
});
</script>
