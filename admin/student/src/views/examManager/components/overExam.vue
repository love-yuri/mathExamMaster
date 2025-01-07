<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-01-07 19:07:18
 * @LastEditTime: 2025-01-07 19:13:22
 * @Description: 考试结束页面
-->
<!-- ExamEnd.vue -->
<template>
  <div class="exam-end-page">
    <div class="message-box">
      <h1 class="title">考试结束</h1>
      <p class="subtitle">感谢您的参与，请有序离开考场</p>
    </div>
  </div>
</template>

<script setup lang="ts">
//
</script>

<style scoped>
.exam-end-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background: linear-gradient(135deg, #918d99, #a5879b);
  color: #fff;
  animation: fadeIn 1.5s ease-in-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.message-box {
  text-align: center;
  background: rgba(255, 255, 255, 0.1);
  padding: 2rem;
  border-radius: 15px;
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  animation: popIn 0.8s ease-in-out;
}

@keyframes popIn {
  0% {
    transform: scale(0.8);
    opacity: 0;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}

.title {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  font-weight: bold;
}

.subtitle {
  font-size: 1.55rem;
  margin-bottom: 2rem;
}
</style>
