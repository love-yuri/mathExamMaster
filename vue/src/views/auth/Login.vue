<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-22 20:07:57
 * @LastEditTime: 2024-08-23 00:07:44
 * @Description: 登陆界面
-->

<template>
  <!-- <FloatingConfigurator /> -->
  <div class="bg-surface-50 dark:bg-surface-950 flex items-center justify-center min-h-screen min-w-[100vw] overflow-hidden">
    <div class="flex flex-col items-center justify-center">
      <div
        style="
          border-radius: 56px;
          padding: 0.3rem;
          background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%);
        "
      >
        <div
          class="w-full bg-surface-0 dark:bg-surface-900 py-20 px-8 sm:px-20 border border-gray-600 shadow-blue-400"
          style="border-radius: 53px"
        >
          <div class="text-center mb-8">
            <div class="text-surface-900 dark:text-surface-0 text-3xl font-medium mb-4">登陆</div>
          </div>

          <div>
            <label for="email1" class="block text-surface-900 dark:text-surface-0 text-xl font-medium mb-2">账号</label>
            <InputText id="email1" v-model="userInfo.id" type="text" placeholder="请输入账号" class="w-full md:w-[30rem] mb-8" />

            <label for="password1" class="block text-surface-900 dark:text-surface-0 font-medium text-xl mb-2">密码</label>
            <Password
              id="password1"
              v-model="userInfo.password"
              placeholder="请输入密码"
              :toggleMask="true"
              class="mb-4"
              fluid
              :feedback="false"
            />

            <div class="flex items-center justify-between mt-2 mb-8 gap-8">
              <div class="flex items-center">
                <Checkbox id="rememberme1" v-model="userInfo.checked" binary class="mr-2" />
                <label for="rememberme1">记住密码</label>
              </div>
              <span class="font-medium no-underline ml-2 text-right cursor-pointer text-primary">忘记密码?</span>
            </div>
            <Button label="登陆" class="w-full" @click="login" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue';
import { Checkbox, InputText, Password, Button } from '@/components';
import { userApi } from '@/api/userApi';
import type { LocalUserInfo } from '@/common/utils/localStorageUtils';
import { useUserStore } from '@/stores/user';

const userStore = useUserStore();

const userInfo = reactive({
  id: '',
  password: '',
  checked: false,
});

async function login() {
  const res = await userApi.login(userInfo);
  const info: LocalUserInfo = {
    id: res.user.id,
    username: '',
    token: res.token,
  };
  userStore.setInfo(info);
}
</script>

<style scoped>
.pi-eye {
  transform: scale(1.6);
  margin-right: 1rem;
}

.pi-eye-slash {
  transform: scale(1.6);
  margin-right: 1rem;
}
</style>
