<template>
  <div class="flex flex-row p-2">
    <div class="flex flex-col w-[200px] p-2 border-2 rounded-sm">
      <Button
        @click="currentStudent = student.id"
        :severity="currentStudent == student.id ? 'primary' : 'secondary'"
        class="w-full mb-2"
        v-for="student in students"
        :label="student.nickname"  
        :key="student.id" 
      />
    </div>
    <div class="flex-grow">

    </div>
  </div>
</template>
<script setup lang="ts">
import type { UserResult } from '@yuri/types';
import { Button } from '@yuri/components';
import { computed, ref, watchEffect } from 'vue';
import { useRoute } from 'vue-router';
import { examPageReleaseApi } from '@yuri/common';

const route = useRoute();
const currentStudent = ref('');
const releaseId = computed(() => route.params.id as string);
const students = ref<UserResult[]>([]);

watchEffect(async () => {
  students.value = await examPageReleaseApi.studentDetail(releaseId.value);
});

</script>