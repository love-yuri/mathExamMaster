<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-12 17:47:35
 * @LastEditTime: 2024-12-12 20:54:09
 * @Description: 组织管理
-->

<template>
  <div class="card overflow-x-auto">
    {{ selectKey }}
    <OrganizationChart @node-select="handleNodeSelect" v-model:selection-keys="selectKey" v-if="treeData" :value="treeData" collapsible selection-mode="single">
      <!-- <template #person="slotProps">
        <div class="flex flex-col">
          <div class="flex flex-col items-center">
            <img
              :alt="slotProps.node.data.name"
              :src="slotProps.node.data.image"
              class="mb-4 h-12 w-12"
            />
            <span class="mb-2 font-bold">{{ slotProps.node.data.name }}</span>
            <span>{{ slotProps.node.data.title }}</span>
          </div>
        </div>
      </template> -->
      <template #default="slotProps">
        <!-- @click="(e) => onSelect(e, slotProps.node)" -->
        <span class="cursor-pointer" >{{ slotProps.node.label }}
        </span>
      </template>
    </OrganizationChart>
    <Popover ref="popoverRef">
      <div>
        <div class="flex flex-col items-center">
          <span class="mb-2 text-[20px] font-semibold">创建组织</span>
          <InputText v-model="createParam.name" placeholder="请输入组织名称" />
          <Button
            class="mt-2 h-6 w-24"
            label="确认"
            @click="createDepartment"
          />
        </div>
      </div>
    </Popover>
  </div>
</template>

<script setup lang="ts">
import {
  Department,
  departmentApi,
  type TreeResult,
} from '#/api/departmentApi';
import { checkEmpty, checkSuccess } from '#/common/utils/valueCheck';
import { Button, InputText, OrganizationChart, Popover } from '#/components';
import { onMounted, ref, useTemplateRef } from 'vue';

const treeData = ref<TreeResult>();
const selectKey = ref<TreeResult>();
const createParam = ref<Department>(new Department());
const popoverRef = useTemplateRef('popoverRef');

function loadData() {
  departmentApi.tree().then((res) => (treeData.value = res));
}

function onSelect(event: Event, node: TreeResult) {
  createParam.value.parentId = node.key;
  popoverRef.value?.toggle(event);
}

function createDepartment() {
  checkEmpty(createParam.value.name, '组织名称不能为空!!!');
  checkSuccess(departmentApi.create(createParam.value), true, '组织', (v) => {
    if (v) {
      popoverRef.value?.hide();
      loadData();
    }
  });
}

function handleNodeSelect(node) {
  console.log('yuri: ', node.label);
}

onMounted(loadData);
</script>
