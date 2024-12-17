import {
  Boot,
  DomEditor,
  type IDomEditor,
  type IModuleConf,
} from '@wangeditor/editor';
import { yuriMathConf } from './yuriMath';
import { InsertMathMenuConf } from './insertMathMenu';
import { EditMathMenuConf } from './editMathMenu';
import { RemoveMathMenuConf } from './removeButton';

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-22 20:16:25
 * @LastEditTime: 2024-10-23 19:31:41
 * @Description: 注册，导入，到处 yuri 组件
 */
export function withYuri<T extends IDomEditor>(editor: T) {
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

const BootModules: Partial<IModuleConf> = {
  // TS 语法
  editorPlugin: withYuri,
  elemsToHtml: [yuriMathConf.elemToHtmlConf],
  menus: [InsertMathMenuConf, EditMathMenuConf, RemoveMathMenuConf],
  parseElemsHtml: [yuriMathConf.parseHtmlConf],
  renderElems: [yuriMathConf.renderConf],
};
Boot.registerModule(BootModules);
