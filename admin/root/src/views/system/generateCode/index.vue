<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:36:21
 * @LastEditTime: 2024-09-20 22:51:47
 * @Description: 
-->
<template>
  <div class="p-2">
    <Toolbar
      :default-config="toolbarConfig"
      :editor="editorRef"
      style="border-bottom: 1px solid #ccc"
    />
    <Editor
      v-model="valueHtml"
      :default-config="editorConfig"
      style="height: 500px; overflow-y: hidden"
      @on-created="handleCreated"
    />
  </div>
</template>
<script setup lang="ts">
import '@wangeditor/editor/dist/css/style.css'; // 引入 css
import { type IEditorConfig } from '@wangeditor/editor';

import { onBeforeUnmount, ref, shallowRef } from 'vue';
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';
import { systemApi } from '#/api/systemApi';

type InsertFnType = (url: string, alt: string, href: string) => void;
const editorRef = shallowRef();
const editorConfig: Partial<IEditorConfig> = {
  MENU_CONF: {
    uploadImage: {
      // 自定义插入图片
      customInsert(res: any, insertFn: InsertFnType) {
        console.log('yuri: ', );
      },
      // 自定义上传图片
      async customUpload(file: File, insertFn: InsertFnType) {
        systemApi.upload({
          file,
        });
      },
      fieldName: 'custom-field-name',
      server: '/api/upload-image',
    },
  },
};

// 内容 HTML
const valueHtml = ref('<p>hello</p>');

const toolbarConfig = {};

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor === null) return;
  editor.destroy();
});

const handleCreated = (editor: any) => {
  // 记录 editor 实例，重要！
  editorRef.value = editor;
};
</script>
