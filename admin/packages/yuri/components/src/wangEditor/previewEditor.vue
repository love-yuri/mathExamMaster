<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:11:39
 * @LastEditTime: 2025-01-09 18:42:42
 * @Description: 封装富文本编辑器
-->
<template>
  <div class="overflow-hidden rounded-[12px] shadow-md">
    <Editor
      v-model="content"
      :default-config="editorConfig"
      style=" height: 500px;min-height: 300px"
      @on-created="handleCreated"
    />
  </div>
</template>
<script setup lang="ts">
import '@wangeditor-next/editor/dist/css/style.css'; // 引入 css
import { type IDomEditor, type IEditorConfig } from '@wangeditor-next/editor';
import { onBeforeUnmount, shallowRef } from 'vue';
import { Editor } from '@wangeditor-next/editor-for-vue';

const content = defineModel<string>('content');

const editorRef = shallowRef();
const editorConfig: Partial<IEditorConfig> = {
  readOnly: true,
};

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor === null) return;
  editor.destroy();
});

const handleCreated = (editor: IDomEditor) => {
  // 记录 editor 实例，重要！
  editorRef.value = editor;
};
</script>
