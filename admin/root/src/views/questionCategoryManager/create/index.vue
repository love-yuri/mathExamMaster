<template>
  <div class="p-4">
    <Card class="category-card">
      <template #header>
        <div class="flex items-center justify-between bg-blue-50 p-4">
          <div class="flex items-center gap-3">
            <i
              :class="
                isEdit
                  ? 'pi pi-pencil text-blue-600'
                  : 'pi pi-plus-circle text-green-600'
              "
              style="font-size: 2rem"
            ></i>
            <div>
              <h2 class="m-0 text-xl font-medium text-blue-800">
                {{ isEdit ? '编辑分类' : '创建分类' }}
              </h2>
              <p class="m-0 mt-1 text-sm text-blue-600">
                {{ isEdit ? '修改现有分类信息' : '创建一个新的题目分类' }}
              </p>
            </div>
          </div>
          <Button
            icon="pi pi-arrow-left"
            label="返回列表"
            outlined
            severity="info"
            class="transition-colors duration-200 hover:bg-blue-50"
            @click="router.back()"
          />
        </div>
      </template>
      <template #content>
        <form @submit.prevent="submitForm" class="mt-4 px-2">
          <div class="mb-6">
            <h3 class="mb-2 text-lg font-medium text-gray-700">基本信息</h3>
            <div class="rounded-lg bg-gray-50 p-4">
              <!-- 分类名称 -->
              <div class="mb-4">
                <label for="name" class="mb-2 block font-medium text-gray-700">
                  分类名称 <span class="text-red-500">*</span>
                </label>
                <InputText
                  id="name"
                  v-model="form.name"
                  :class="{ 'p-invalid': submitted && !form.name }"
                  class="w-full shadow-sm"
                  placeholder="请输入分类名称"
                />
                <small
                  v-if="submitted && !form.name"
                  class="p-error mt-1 block"
                >
                  分类名称不能为空
                </small>
              </div>

              <!-- 描述 -->
              <div>
                <label
                  for="description"
                  class="mb-2 block font-medium text-gray-700"
                >
                  分类描述
                </label>
                <Textarea
                  id="description"
                  v-model="form.description"
                  auto-resize
                  class="w-full shadow-sm"
                  placeholder="请输入分类描述（选填）"
                  rows="4"
                />
              </div>
            </div>
          </div>

          <!-- 提交按钮 -->
          <div class="flex justify-end gap-3 border-t pt-4">
            <Button
              icon="pi pi-times"
              label="取消"
              outlined
              severity="secondary"
              type="button"
              class="px-4"
              @click="router.back()"
            />
            <Button
              :icon="isEdit ? 'pi pi-check' : 'pi pi-plus'"
              :label="isEdit ? '保存修改' : '创建分类'"
              :loading="loading"
              severity="primary"
              type="submit"
              class="px-4"
              raised
            />
          </div>
        </form>
      </template>
    </Card>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { checkSuccess, message, questionCategoryApi } from '@yuri/common';
import { Button, Card, InputText, Textarea } from '@yuri/components';
import { QuestionCategory } from '@yuri/types';

const route = useRoute();
const router = useRouter();
const loading = ref(false);
const submitted = ref(false);

// 判断是否为编辑模式
const isEdit = computed(() => !!route.params.id);

// 表单数据
const form = ref<QuestionCategory>(new QuestionCategory());

// 加载分类数据
async function loadCategoryData() {
  if (isEdit.value) {
    loading.value = true;
    try {
      const id = route.params.id as string;
      const res = await questionCategoryApi.get(id);
      form.value.copy(res);
    } catch {
      message.default.error('加载分类数据失败');
      router.back();
    } finally {
      loading.value = false;
    }
  }
}

// 提交表单
async function submitForm() {
  submitted.value = true;

  // 简单验证
  if (!form.value.name) {
    return;
  }

  loading.value = true;
  try {
    if (isEdit.value) {
      // 更新分类
      form.value.id = route.params.id as string;
      await checkSuccess(
        questionCategoryApi.update(form.value),
        true,
        '分类更新',
      );
    } else {
      // 创建分类
      await checkSuccess(
        questionCategoryApi.create(form.value),
        true,
        '分类创建',
      );
    }
    router.push('/question/category');
  } catch {
    message.default.error(isEdit.value ? '更新分类失败' : '创建分类失败');
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  loadCategoryData();
});
</script>

<style scoped>
.category-card {
  max-width: 800px;
  margin: 0 auto;
  overflow: hidden;
  border: none;
  border-radius: 12px;
  box-shadow:
    0 10px 15px -3px rgb(0 0 0 / 10%),
    0 4px 6px -2px rgb(0 0 0 / 5%);
}

:deep(.p-card-content) {
  padding: 1.5rem;
}

:deep(.p-card-header) {
  padding: 0;
  background-color: #f0f7ff;
}

:deep(.p-inputtext) {
  padding: 0.75rem 1rem;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  transition: all 0.3s ease;
}

:deep(.p-inputtext:enabled:focus) {
  border-color: #3b82f6;
  box-shadow: 0 0 0 2px rgb(59 130 246 / 25%);
}

:deep(.p-inputtext:hover:not(.p-invalid)) {
  border-color: #93c5fd;
}

:deep(.p-button) {
  border-radius: 8px;
  transition: all 0.3s ease;
}

:deep(.p-button.p-button-raised:not(.p-disabled):hover) {
  box-shadow:
    0 4px 6px -1px rgb(0 0 0 / 10%),
    0 2px 4px -1px rgb(0 0 0 / 6%);
  transform: translateY(-2px);
}
</style>
