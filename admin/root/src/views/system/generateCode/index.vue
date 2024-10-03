<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-29 11:34:19
 * @LastEditTime: 2024-10-03 19:02:59
 * @Description: 
-->
<template>
  <div class="card flex flex-col items-center justify-center p-3">
    <Select
      v-model="selectedDb"
      :options="databases"
      class="w-full md:w-56"
      option-label="name"
      placeholder="请选择一个数据库"
    />
    <Select
      v-model="selectedTable"
      :options="tables"
      class="mt-7 w-full md:w-56"
      option-label="name"
      placeholder="请选择待生成的表"
    />
    <Button class="mt-7" @click="handleGenerate">生成</Button>
  </div>
</template>

<script setup lang="ts">
import message from '#/common/utils/message';
import { systemApi } from '#/api/systemApi';
import { Button, Select } from '#/components';
import { onMounted, ref, watch } from 'vue';

type Option = {
  name: string;
};

const selectedDb = ref();
const selectedTable = ref();
const databases = ref<Option[]>([]);
const tables = ref<Option[]>([]);

const loadDatabases = async () => {
  const res = await systemApi.databases();
  databases.value = res.map((name) => ({
    name,
  }));
};

const loadTables = async (database: string) => {
  const res = await systemApi.tables(database);
  tables.value = res.map((name) => ({
    name,
  }));
};

function handleGenerate() {
  systemApi
    .generate({
      dataBaseName: selectedDb.value.name,
      tableName: selectedTable.value.name,
    })
    .then(() => {
      message.success('生成成功');
    });
}

onMounted(async () => {
  loadDatabases();
});
watch(selectedDb, loadTables);
</script>
