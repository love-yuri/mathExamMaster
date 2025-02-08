<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-19 08:37:51
 * @LastEditTime: 2025-02-08 16:46:48
 * @Description: 
-->
<script lang="ts" setup>
import { AuthenticationLogin } from '@vben/common-ui';

import { useAuthStore } from '@yuri/common';

defineOptions({ name: 'Login' });

const authStore = useAuthStore();
</script>

<template>
  <AuthenticationLogin
    :loading="authStore.loginLoading"
    password-placeholder="123456"
    username-placeholder="vben"
    @submit="authStore.authLogin"
  />
</template>
