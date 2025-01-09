<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-22 18:41:52
 * @LastEditTime: 2025-01-09 18:43:31
 * @Description: 修改公式
-->
<template>
  <div>
    <Model
      :confirm-text="`${isCreate ? '创建' : '修改'}公式'`"
      height="600"
      width="800"
    >
      <div class="p-2">
        <div ref="mathfield" class="h-16 p-2 text-[42px]"></div>
        <div id="Keyboard" class="mt-8 h-[420px] w-full"></div>
      </div>
    </Model>
  </div>
</template>
<script setup lang="ts">
import { useVbenModal } from '@vben/common-ui';
import { nextTick, ref } from 'vue';
// import { InputText } from '#/components/';
import {
  DomEditor,
  type IDomEditor,
  SlateTransforms,
} from '@wangeditor-next/editor';
import type { YuriMathNode } from '../wangEditor/templates/yuriMath';

import { MathfieldElement } from 'mathlive';
import { checkEmpty } from '#/common/utils/valueCheck';

const isCreate = ref(false);
const math = ref('');
const node = ref<YuriMathNode>();
const dom = ref<IDomEditor>();
const mathfield = ref<HTMLDivElement>(); // 用于引用 Mathfield 的 DOM 节点

// function getCursorPath(editorElem) {
//   const selection = DomEditor.getWindow(editorElem).getSelection();

//   if (!selection.rangeCount) return null;

//   // 获取当前 range 对象
//   const range = selection.getRangeAt(0);
//   const { commonAncestorContainer } = range;
//   SlateTransforms.insertNodes(this.editor, node, { at: [1] })

//   // 初始化 path 数组，从光标位置向上遍历 DOM
//   let node = commonAncestorContainer;
//   const path = [];

//   while (node && node !== editorElem) {
//     const parent = node.parentNode;

//     if (parent) {
//       // 获取 node 在父节点中的索引
//       const index = Array.prototype.indexOf.call(parent.childNodes, node);
//       path.push(index);
//     }

//     node = parent;
//   }

//   return path.reverse(); // 从根节点到光标节点的顺序
// }

/* 处理预览弹窗 */
const [Model, modalApi] = useVbenModal({
  onConfirm: () => {
    checkEmpty(math.value, '请输入公式!');
    if (isCreate.value) {
      const n = {
        math: String.raw`${math.value}`,
        type: 'yuri-math',
        children: [{ text: '' }],
      };
      dom.value?.restoreSelection();
      const selection = dom.value?.selection;
      if (selection && selection.focus.path) {
        const focusPath = selection.focus.path;

        // 在最后一个索引上加一，获取后一个位置
        const newPath = [...focusPath];
        newPath[newPath.length - 1]! += 1;

        // 插入节点 n 在元素后面
        SlateTransforms.insertNodes(dom.value!, n, {
          at: newPath,
        });
      } else {
        dom.value?.insertNode(n);
      }
      modalApi.close();
      return;
    }
    const path = DomEditor.findPath(dom.value!, node.value!);
    const props: Partial<YuriMathNode> = {
      math: String.raw`${math.value}`,
    };
    SlateTransforms.setNodes(dom.value!, props, { at: path });
    modalApi.close();
  },
  title: '数学公式',
});

function init() {
  nextTick(() => {
    const mf = new MathfieldElement();
    mf.value = String.raw`${math.value}`; // 设置初始表达式
    mf.className = 'w-full  border-2 border-[#5898ff]'; // 设置 Mathfield 的样式类名
    mf.addEventListener('input', () => {
      math.value = mf.getValue('latex');
    });

    // 将 Mathfield 添加到 DOM 中
    mathfield.value!.append(mf);
    window.mathVirtualKeyboard.container = document.querySelector('#Keyboard');
  });
}

function open(editor: IDomEditor, create: boolean) {
  dom.value = editor;
  isCreate.value = create;
  if (create) {
    math.value = '';
  } else {
    node.value = DomEditor.getSelectedNodeByType(
      editor,
      'yuri-math',
    ) as YuriMathNode;
    math.value = node.value.math;
  }

  modalApi.open();
  init();
}

defineExpose({ open });
</script>
