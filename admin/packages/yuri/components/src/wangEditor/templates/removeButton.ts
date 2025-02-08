/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-23 18:58:42
 * @LastEditTime: 2024-10-23 20:01:22
 * @Description:
 */
import {
  DomEditor,
  type IDomEditor,
  SlateTransforms,
} from '@wangeditor-next/editor';
import { type ComponentPublicInstance } from 'vue';
import { BaseButtonMenu } from './baseButton';

class RemoveMathMenu extends BaseButtonMenu {
  exec: (editor: IDomEditor, value: boolean | string) => void = (
    editor: IDomEditor,
    _value: boolean | string,
  ) => {
    // SlateTransforms.delete(editor);
    const node = DomEditor.getSelectedNodeByType(editor, 'yuri-math');
    const path = DomEditor.findPath(editor, node!);
    if (node) {
      SlateTransforms.removeNodes(editor, { at: path });
    }
  };
  root!: ComponentPublicInstance;
  tag: string;
  title: string;

  constructor() {
    super();
    this.tag = 'button';
    this.title = '删除';

    this.title = '删除'; // 自定义菜单标题
    this.tag = 'button';
    this.iconSvg = `<svg t="1729683061135" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3041" width="32" height="32"><path d="M615.258353 800.225882a34.334118 34.334118 0 0 1-34.394353-34.394353V387.192471a34.334118 34.334118 0 1 1 68.848941 0v378.639058a34.334118 34.334118 0 0 1-34.454588 34.394353z m-206.486588 0a34.334118 34.334118 0 0 1-34.454589-34.394353V387.192471a34.334118 34.334118 0 1 1 68.848942 0v378.639058a34.334118 34.334118 0 0 1-34.394353 34.394353z m550.731294-585.125647H787.290353V146.251294a103.002353 103.002353 0 0 0-102.4-103.243294H339.862588c-56.922353 0-103.243294 46.320941-103.243294 103.243294v68.848941H64.466824a34.334118 34.334118 0 1 0 0 68.848941H959.563294a34.334118 34.334118 0 1 0 0-68.848941zM305.408 146.251294c0-18.974118 15.480471-34.394353 34.454588-34.394353h344.96753c18.853647 0 33.671529 15.058824 33.671529 34.394353v68.848941H305.408V146.251294z m447.608471 860.521412h-481.882353c-56.982588 0-103.303529-46.381176-103.30353-103.30353V386.710588a34.454588 34.454588 0 0 1 68.848941 0V903.529412c0 19.034353 15.420235 34.454588 34.394353 34.454588h481.942589a34.454588 34.454588 0 0 0 34.454588-34.454588V388.096a34.394353 34.394353 0 1 1 68.848941 0v515.373176c0 56.982588-46.381176 103.303529-103.303529 103.30353z" fill="#333333" p-id="3042"></path></svg>`;
  }
}

export const RemoveMathMenuConf = {
  factory() {
    return new RemoveMathMenu(); // 把 `YourMenuClass` 替换为你菜单的 class
  },
  key: 'RemoveMathMenu', // 定义 menu key ：要保证唯一、不重复（重要）
};
