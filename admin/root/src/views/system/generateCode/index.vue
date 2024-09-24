<!-- eslint-disable no-console -->
<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:36:21
 * @LastEditTime: 2024-09-24 21:47:55
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
      @custom-paste="customPaste"
      @on-created="handleCreated"
    />
    {{ valueHtml }}
  </div>
</template>
<script setup lang="ts">
import '@wangeditor/editor/dist/css/style.css'; // 引入 css
import { type IDomEditor, type IEditorConfig } from '@wangeditor/editor';
import { onBeforeUnmount, ref, shallowRef } from 'vue';
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';
import { systemApi } from '#/api/systemApi';
import { systemFileApi } from '#/api/systemFileApi';
import { loadHtmlImg } from '#/common/utils/rtfToJpg';

type InsertFnType = (url: string, alt: string, href: string) => void;
const editorRef = shallowRef();
const editorConfig: Partial<IEditorConfig> = {
  MENU_CONF: {
    uploadImage: {
      async customUpload(file: File, insertFn: InsertFnType) {
        const res = await systemApi.upload({
          file,
        });
        // const res = await systemFileApi.getFile('1837387808326205441');
        const url = systemFileApi.getFile(res.id);
        insertFn(url, '', '');
      },
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

const handleCreated = (editor: IDomEditor) => {
  // 记录 editor 实例，重要！
  editorRef.value = editor;
};

function customPaste(editor: IDomEditor, event: ClipboardEvent) {
  const html: string | undefined = event.clipboardData?.getData('text/html');
  const rtf = event.clipboardData?.getData('text/rtf');
  if (html && rtf) {
    loadHtmlImg(html, rtf).then((html) => editor.dangerouslyInsertHtml(html));
    event.preventDefault();
    return false;
  }
  return true;
}
</script>
