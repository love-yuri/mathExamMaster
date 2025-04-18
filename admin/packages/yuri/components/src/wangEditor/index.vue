<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:11:39
 * @LastEditTime: 2025-03-12 16:05:10
 * @Description: 封装富文本编辑器
-->
<template>
  <div class="">
    <div class="rounded-sm bg-white p-2">
      <Toolbar
        v-if="!hideToolbarConfig"
        :default-config="toolbarConfig"
        :editor="editorRef"
        style="border-bottom: 1px solid #ccc"
      />
      <Editor
        v-model="content"
        :default-config="editorConfig"
        style="height: 410px; min-height: 410px"
        @custom-paste="customPaste"
        @on-created="handleCreated"
      />
      <MathModal ref="mathModalRef" />
    </div>
  </div>
</template>
<script setup lang="ts">
import type {
  IDomEditor,
  IEditorConfig,
  IToolbarConfig,
} from '@wangeditor-next/editor';

import type { WangEditorProps } from './types';

import { onBeforeUnmount, ref, shallowRef, watch } from 'vue';

import { useAppConfig } from '@vben/hooks';
import { useAccessStore } from '@vben/stores';

import { invoke } from '@tauri-apps/api/core';
import { Editor, Toolbar } from '@wangeditor-next/editor-for-vue';
import { systemApi, systemFileApi } from '@yuri/common';

import MathModal from '../math/mathModal.vue';
import ToolbarKeys from './toolbarKeys.json';

import '@wangeditor-next/editor/dist/css/style.css'; // 引入 css

const props = defineProps<WangEditorProps>();
const emits = defineEmits(['change']);

const content = defineModel<string | undefined>('content', { required: true });
const mathModalRef = ref();

type InsertFnType = (url: string, alt: string, href: string) => void;
const editorRef = shallowRef();
const editorConfig: Partial<IEditorConfig> = {
  autoFocus: true,
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
  readOnly: props.readonly,
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

/**
 * 检查是否包含图片
 * @param html html内容
 */
function containsImage(html: string): boolean {
  const tempDiv = document.createElement('div');
  tempDiv.innerHTML = html;
  return tempDiv.querySelector('img') !== null;
}

const { apiURL } = useAppConfig(import.meta.env, import.meta.env.PROD);
async function customPaste(editor: IDomEditor, event: ClipboardEvent) {
  const html: string | undefined = event.clipboardData?.getData('text/html');
  if (html && containsImage(html)) {
    event.preventDefault();
    const accessStore = useAccessStore();
    const res = await invoke('parse_html', {
      baseUrl: apiURL,
      str: html,
      token: accessStore.accessToken,
    });
    editor.dangerouslyInsertHtml(res as string);
    return false;
  }
  return true;
}
</script>
