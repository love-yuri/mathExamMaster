<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-29 11:34:19
 * @LastEditTime: 2025-02-08 14:53:09
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
