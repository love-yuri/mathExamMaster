<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-23 18:55:25
 * @LastEditTime: 2024-12-24 19:10:06
 * @Description: 
-->
<template>
  <div>
    {{ examPageInfo }}
  </div>
</template>
<script lang="ts" setup>
import type { StartExamResult } from '#/api/examPageReleaseApi';

defineProps<{
  examPageInfo: StartExamResult;
}>();
</script>
