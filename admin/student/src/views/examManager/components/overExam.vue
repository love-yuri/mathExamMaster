<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-01-07 19:07:18
 * @LastEditTime: 2025-02-27 19:19:14
 * @Description: 考试结束页面
-->
<!-- ExamEnd.vue -->
<template>
  <div class="exam-end-page">
    <div class="message-box">
      <h1 class="title">考试结束</h1>
      <p class="subtitle">感谢您的参与，请有序离开考场，{{ leftTime }}s后自动退出登录</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';

import { useAuthStore } from '@yuri/common';

const leftTime = ref(10);

onMounted(() => {
  // 在考试结束页面停留10秒后自动退出登录
  const interval = setInterval(() => {
    leftTime.value--;
    if (leftTime.value <= 0) {
      clearInterval(interval);
      const authStore = useAuthStore();
      authStore.logout(false);
    }
  }, 1000);
});
</script>

<style scoped>
.exam-end-page {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #fff;
  background: linear-gradient(135deg, #918d99, #a5879b);
  animation: fade-in 1.5s ease-in-out;
}

@keyframes fade-in {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}

.message-box {
  padding: 2rem;
  text-align: center;
  background: rgb(255 255 255 / 10%);
  backdrop-filter: blur(10px);
  border: 1px solid rgb(255 255 255 / 20%);
  border-radius: 15px;
  box-shadow: 0 4px 30px rgb(0 0 0 / 10%);
  animation: pop-in 0.8s ease-in-out;
}

@keyframes pop-in {
  0% {
    opacity: 0;
    transform: scale(0.8);
  }

  100% {
    opacity: 1;
    transform: scale(1);
  }
}

.title {
  margin-bottom: 1rem;
  font-size: 2.5rem;
  font-weight: bold;
}

.subtitle {
  margin-bottom: 2rem;
  font-size: 1.55rem;
}
</style>
