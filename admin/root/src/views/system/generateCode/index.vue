<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-29 11:34:19
 * @LastEditTime: 2025-02-27 19:13:09
 * @Description: 
-->
<template>
  <div class="card flex flex-col items-center justify-center p-3">
    <Select
      v-model="selectedDb"
      :options="databases"
      class="w-full md:w-56"
      option-label="name"
      option-value="name"
      placeholder="请选择一个数据库"
    />
    <Select
      v-model="selectedTable"
      :options="tables"
      class="mt-7 w-full md:w-56"
      option-label="name"
      placeholder="请选择待生成的表"
    />
    <div class="mt-7 flex items-center">
      <span class="mx-2 text-[20px]">是否覆盖原有数据</span>
      <ToggleSwitch v-model="override" />
    </div>
    <Button class="mt-7" @click="handleGenerate">生成</Button>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue';

import { message, systemApi } from '@yuri/common';
import { Button, Select, ToggleSwitch } from '@yuri/components';

type Option = {
  name: string;
};

const selectedDb = ref();
const selectedTable = ref();
const databases = ref<Option[]>([]);
const tables = ref<Option[]>([]);
const override = ref(false);

const loadDatabases = async () => {
  const res = await systemApi.databases();
  databases.value = res.map((name) => ({
    name,
  }));
};

const loadTables = async () => {
  const res = await systemApi.tables(selectedDb.value);
  tables.value = res.map((name) => ({
    name,
  }));
};

function handleGenerate() {
  systemApi
    .generate({
      dataBaseName: selectedDb.value.name,
      override: override.value,
      tableName: selectedTable.value.name,
    })
    .then(() => {
      message.default.success('生成成功');
    });
}

onMounted(async () => {
  loadDatabases();
});
watch(selectedDb, loadTables);
</script>
