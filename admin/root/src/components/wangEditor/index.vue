<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:11:39
 * @LastEditTime: 2024-10-17 20:42:04
 * @Description: 封装富文本编辑器
-->
<template>
  <div class="">
    <div class="rounded-sm bg-white p-2">
      <Toolbar
        :default-config="toolbarConfig"
        :editor="editorRef"
        style="border-bottom: 1px solid #ccc"
      />
      <Editor
        v-model="valueHtml"
        :default-config="editorConfig"
        style="min-height: 300px; height: 300px"
        @custom-paste="customPaste"
        @on-created="handleCreated"
      />
    </div>
  </div>
</template>
<script setup lang="ts">
import '@wangeditor/editor/dist/css/style.css'; // 引入 css
import { type IDomEditor, type IEditorConfig } from '@wangeditor/editor';
import { onBeforeUnmount, ref, shallowRef, watch } from 'vue';
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';
import { systemApi } from '#/api/systemApi';
import { systemFileApi } from '#/api/systemFileApi';
import { loadHtmlImg } from '#/common/utils/rtfToJpg';
import type { WangEditorProps } from './types';

const props = defineProps<WangEditorProps>();

const emit = defineEmits(['update:content']);

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
  placeholder: props.placeholder,
};

// 内容 HTML
const valueHtml = ref(props.content);
const isUpdating = ref(false); // 添加一个标志位来控制更新

// 监听 props.content 的变化，并同步更新 valueHtml
watch(
  () => props.content,
  (newContent) => {
    if (!isUpdating.value) {
      // 仅在非更新状态下同步
      valueHtml.value = newContent;
    }
  },
);

// 监听 valueHtml 的变化，并 emit 出去
watch(valueHtml, (newVal: string) => {
  isUpdating.value = true; // 标记为更新状态
  emit('update:content', newVal);
  isUpdating.value = false; // 重置标志位
});

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
