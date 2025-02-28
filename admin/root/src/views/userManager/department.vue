<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-12 17:47:35
 * @LastEditTime: 2025-02-27 19:12:24
 * @Description: 组织管理
-->

<template>
  <div class="flex h-full p-2">
    <OrganizationChart
      v-if="treeData"
      v-model:selection-keys="selectKey"
      :value="treeData"
      class="flex-grow"
      collapsible
      selection-mode="single"
      @node-select="handleNodeSelect"
    >
      <template #default="slotProps">
        <!-- @click="(e) => onSelect(e, slotProps.node)" -->
        <span class="cursor-pointer">{{ slotProps.node.label }} </span>
      </template>
    </OrganizationChart>
    <div
      class="min-h-full w-[400px] flex-shrink-0 rounded-[12px] bg-[#DFE3E9] p-2"
    >
      <div v-if="currentDep">
        <div class="my-1 flex justify-between">
          <Button icon="pi pi-plus" label="添加用户" @click="addUser" />
          <Button icon="pi pi-plus" label="添加下级组织" @click="onSelect" />
          <Button
            icon="pi pi-trash"
            label="删除组织"
            severity="danger"
            @click="removeDep"
          />
        </div>
        <div class="my-1 flex justify-between">
          <Button icon="pi pi-plus" label="添加老师" @click="setTeacher" />
        </div>
        <div class="">
          <div class="ml-4 text-[15px] text-[#84888F]">组织名称</div>
          <div class="flex items-center">
            <InputText
              v-model="currentDep.name"
              class="mr-1 w-full rounded-[4px] bg-white"
            />
            <Button
              class="w-16 flex-shrink-0"
              label="修改"
              @click="updateDep"
            />
          </div>
        </div>
        <div class="mt-3">
          <div class="ml-4 text-[15px] text-[#84888F]">组织创建时间</div>
          <div class="mt-1 rounded-[4px] bg-white p-2">
            {{ currentDep.createTime }}
          </div>
        </div>
        <div class="mt-3">
          <div
            class="ml-4 text-[15px] text-[#84888F]"
            v-if="currentDep.teacherInfo"
          >
            本班级教师
          </div>
          <div class="mt-1 rounded-[4px] bg-white p-2">
            {{ currentDep.teacherInfo?.nickname }}
          </div>
        </div>
        <div class="mt-3">
          <div class="ml-4 text-[15px] text-[#84888F]">用户列表</div>
          <div
            v-for="user in currentDep.users"
            :key="user.id"
            class="mt-1 rounded-[4px] bg-white p-2"
          >
            {{ user.username }}
          </div>
        </div>
      </div>
      <div v-else class="flex h-full items-center justify-center">
        <span class="text-[23px] font-semibold">请选择一个组织</span>
      </div>
    </div>
    <UserSelect ref="userSelectRef" />
    <TeacherSelect ref="teacherSelectRef" />
    <Popover ref="popoverRef">
      <div class="flex flex-col items-center">
        <span class="mb-2 text-[20px] font-semibold">创建组织</span>
        <InputText v-model="createParam.name" placeholder="请输入组织名称" />
        <Button class="mt-2 h-6 w-24" label="确认" @click="createDepartment" />
      </div>
    </Popover>
    <DefaultConfirmDialog group="headless" />
  </div>
</template>

<script setup lang="ts">
import type { DepartmentDetail, TreeResult } from '@yuri/types';
import type { OrganizationChartNode } from 'primevue';

import { onMounted, ref, useTemplateRef } from 'vue';

import {
  checkEmpty,
  checkSuccess,
  departmentApi,
  message,
  userApi,
  userDepartmentApi,
} from '@yuri/common';
import {
  Button,
  DefaultConfirmDialog,
  InputText,
  OrganizationChart,
  Popover,
  TeacherSelect,
  UserSelect,
} from '@yuri/components';
import { Department } from '@yuri/types';
import { useConfirm } from 'primevue/useconfirm';

const confirm = useConfirm();

const popoverRef = useTemplateRef('popoverRef');
const treeData = ref<TreeResult>();
const selectKey = ref<TreeResult>();
const currentDep = ref<DepartmentDetail>();
const createParam = ref<Department>(new Department());
const userSelectRef = useTemplateRef('userSelectRef');
const teacherSelectRef = useTemplateRef('teacherSelectRef');

function loadData() {
  departmentApi.tree().then((res) => (treeData.value = res));
}

function onSelect(event: Event) {
  popoverRef.value?.toggle(event);
}

function createDepartment() {
  createParam.value.parentId = currentDep.value!!.id;
  checkEmpty(createParam.value.name, '组织名称不能为空!!!');
  checkEmpty(createParam.value.parentId, '父级组织id不能为空!!!');
  checkSuccess(departmentApi.create(createParam.value), true, '组织', (v) => {
    if (v) {
      popoverRef.value?.hide();
      createParam.value.reset();
      loadData();
    }
  });
}

function loadNodeData(id: string) {
  departmentApi.detail(id).then((res) => {
    currentDep.value = res;
  });
}

function handleNodeSelect(node: OrganizationChartNode) {
  loadNodeData(node.key);
}

function setTeacher() {
  teacherSelectRef.value?.show(
    currentDep.value?.teacherInfo?.id ?? '',
    (res) => {
      if (res.length === 0) {
        return;
      }
      userApi
        .setTeacher({
          departmentId: currentDep.value?.id!!,
          teacherId: res[0]!!.id,
        })
        .then((v) => {
          if (v) {
            setTimeout(() => {
              loadNodeData(currentDep.value!!.id);
            }, 400);
            message.default.success('设置老师成功!!');
            loadData();
          } else {
            message.default.error('设置老师失败!!');
          }
        });
    },
  );
}

function addUser() {
  userSelectRef.value?.show(currentDep.value!!.users, (res) => {
    if (res.length === 0) {
      return;
    }
    userDepartmentApi
      .batchSave({
        departmentId: currentDep.value?.id!!,
        userIds: res.map((v) => v.id),
      })
      .then((v) => {
        if (v) {
          setTimeout(() => {
            loadNodeData(currentDep.value!!.id);
          }, 400);
          message.default.success('添加成功');
        } else {
          message.default.error('添加失败!!');
        }
      });
  });
}

/**
 * 删除
 */
function removeDep() {
  confirm.require({
    accept: () => {
      departmentApi.delete(currentDep.value?.id!!).then(() => {
        message.default.success('删除成功');
        currentDep.value = undefined;
        loadData();
      });
    },
    group: 'headless',
    message: '是否要删除该组织?',
  });
}

function updateDep() {
  checkEmpty(currentDep.value?.name, '组织名称不能为空!!!');
  checkSuccess(
    departmentApi.update({
      id: currentDep.value!!.id!!,
      name: currentDep.value!!.name,
      parentId: currentDep.value!!.parentId,
    } as Department),
    false,
    '组织信息',
    loadData,
  );
}

onMounted(loadData);
</script>
