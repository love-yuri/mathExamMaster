import type { IDomEditor, IModuleConf } from '@wangeditor-next/editor';

import { Boot, DomEditor } from '@wangeditor-next/editor';

import { EditMathMenuConf } from './editMathMenu';
import { InsertMathMenuConf } from './insertMathMenu';
import { RemoveMathMenuConf } from './removeButton';
import { yuriMathConf } from './yuriMath';

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-22 20:16:25
 * @LastEditTime: 2025-03-11 16:29:28
 * @Description: 注册，导入，到处 yuri 组件
 */
function withYuri<T extends IDomEditor>(editor: T) {
  const { isInline, isVoid } = editor;
  const newEditor = editor;

  // 扩展 isInline 方法，识别 yuri 类型组件为 inline
  newEditor.isInline = (elem) => {
    const type = DomEditor.getNodeType(elem);
    if (type === 'yuri-math') return true; // 设置 yuri 类型为 inline 元素
    return isInline(elem); // 保持原始行为
  };

  // 扩展 isVoid 方法，识别 yuri 类型组件为 void
  newEditor.isVoid = (elem) => {
    const type = DomEditor.getNodeType(elem);
    if (type === 'yuri-math') return true; // 设置 yuri 类型为 void 元素
    return isVoid(elem); // 保持原始行为
  };

  return newEditor; // 返回扩展后的编辑器
}

/**
 * 注册wangeditor菜单
 * 全局只能注册一次
 */
export function initBootModules() {
  const BootModules: Partial<IModuleConf> = {
    // TS 语法
    editorPlugin: withYuri,
    elemsToHtml: [yuriMathConf.elemToHtmlConf],
    menus: [InsertMathMenuConf, EditMathMenuConf, RemoveMathMenuConf],
    parseElemsHtml: [yuriMathConf.parseHtmlConf],
    renderElems: [yuriMathConf.renderConf],
  };
  Boot.registerModule(BootModules);
}
