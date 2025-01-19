<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:11:39
 * @LastEditTime: 2025-01-19 18:55:10
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
        v-model="content"
        :default-config="editorConfig"
        style="min-height: 400px; height: 500px"
        @custom-paste="customPaste"
        @on-created="handleCreated"
      />
      <MathModal ref="mathModalRef" />
    </div>
  </div>
</template>
<script setup lang="ts">
// import Mathjax from 'mathjax';
import '@wangeditor-next/editor/dist/css/style.css'; // 引入 css
import {
  type IDomEditor,
  type IEditorConfig,
  type IToolbarConfig,
} from '@wangeditor-next/editor';
import { onBeforeUnmount, ref, shallowRef, watch } from 'vue';
import { Editor, Toolbar } from '@wangeditor-next/editor-for-vue';
import { systemApi } from '#/api/systemApi';
import { systemFileApi } from '#/api/systemFileApi';
import { invoke } from '@tauri-apps/api/core';
import { type WangEditorProps } from './types';
import ToolbarKeys from './toolbarKeys.json';
import MathModal from '../math/mathModal.vue';
import '#/components/wangEditor/templates';

const props = defineProps<WangEditorProps>();
const emits = defineEmits(['change']);

const content = defineModel('content');
const mathModalRef = ref();

type InsertFnType = (url: string, alt: string, href: string) => void;
const editorRef = shallowRef();
const editorConfig: Partial<IEditorConfig> = {
  hoverbarKeys: {
    'yuri-math': {
      menuKeys: ['EditMathMenu', 'RemoveMathMenu'],
    },
  },
  MENU_CONF: {
    uploadImage: {
      base64LimitSize: 0,
      async customUpload(file: File, insertFn: InsertFnType) {
        const res = await systemApi.upload({
          file,
        });
        // const res = await systemFileApi.getFile('1837387808326205441');
        const url = systemFileApi.getFile(res.id);
        insertFn(url, '', '');
      },
      metaWithUrl: false,
      onError: () => {},
      onFailed: () => {},
      onSuccess: () => {},
      server: '',
    },
  },
  placeholder: props.placeholder,
};

const toolbarConfig: Partial<IToolbarConfig> = {
  toolbarKeys: ToolbarKeys,
};

watch(content, () => {
  emits('change');
});

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor === null) return;
  editor.destroy();
});

const handleCreated = (editor: IDomEditor) => {
  // 记录 editor 实例，重要！
  editorRef.value = editor;
  // @ts-ignore
  editor.editYuriMath = (m) => {
    mathModalRef.value?.open(m, false);
  };
  // @ts-ignore
  editor.insertYuriMath = (m) => {
    mathModalRef.value?.open(m, true);
  };
};

async function customPaste(editor: IDomEditor, event: ClipboardEvent) {
  const html: string | undefined = event.clipboardData?.getData('text/html');
  event.preventDefault();
  const res = await invoke('parse_html', {
    cookie: document.cookie,
    str: html,
  });
  editor.dangerouslyInsertHtml(res as string);
  return false;
}
</script>
