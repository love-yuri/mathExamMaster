<template>
  <div>
    {{ examInfo }}
  </div>
</template>
<script lang="ts" setup>
import type { StartExamResult } from '#/api/examPageReleaseApi';

defineProps<{
  examInfo: StartExamResult;
}>();
</script>
