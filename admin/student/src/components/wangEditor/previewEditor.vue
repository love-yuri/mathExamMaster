<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:11:39
 * @LastEditTime: 2024-10-13 15:30:53
 * @Description: 封装富文本编辑器
-->
<template>
  <div class="overflow-hidden rounded-[12px] shadow-md">
    <Editor
      v-model="htmlText"
      :default-config="editorConfig"
      style="min-height: 300px; height: 500px"
      @on-created="handleCreated"
    />
  </div>
</template>
<script setup lang="ts">
import '@wangeditor/editor/dist/css/style.css'; // 引入 css
import { type IDomEditor, type IEditorConfig } from '@wangeditor/editor';
import { onBeforeUnmount, ref, shallowRef, watch } from 'vue';
import { Editor } from '@wangeditor/editor-for-vue';

const props = defineProps<{
  content: string;
}>();

const htmlText = ref('');
const editorRef = shallowRef();
const editorConfig: Partial<IEditorConfig> = {
  readOnly: true,
};

watch(
  () => props.content,
  (newVal) => {
    htmlText.value = newVal;
  },
  { immediate: true },
);

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
