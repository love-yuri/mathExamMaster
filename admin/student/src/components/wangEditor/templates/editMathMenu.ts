/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-23 18:58:42
 * @LastEditTime: 2024-10-23 19:09:08
 * @Description:
 */
import { type IDomEditor } from '@wangeditor-next/editor';
import { type ComponentPublicInstance } from 'vue';
import { BaseButtonMenu } from './baseButton';

class EditMathMenu extends BaseButtonMenu {
  exec: (editor: IDomEditor, value: boolean | string) => void = (
    editor: IDomEditor,
    _value: boolean | string,
  ) => {
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    editor.editYuriMath(editor);
  };
  root!: ComponentPublicInstance;
  tag: string;
  title: string;

  constructor() {
    super();
    this.tag = 'button';
    this.title = '编辑数学公式';

    this.title = '编辑'; // 自定义菜单标题
    this.tag = 'button';
    this.iconSvg = `<svg t="1729606053475" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2157" width="64" height="64"><path d="M872.448 461.824l-402.432 397.312c-24.064 23.552-55.808 36.864-89.6 36.352l-125.952-0.512C184.32 894.976 128 838.144 128 768v-120.32c0-33.792 13.824-66.048 37.888-90.112L568.32 161.28s64-70.656 135.168 0 108.544 107.008 168.96 166.912c60.928 59.904 0 133.632 0 133.632z m-615.424 332.8l134.656 0.512c2.048 0 4.096-1.024 5.632-2.048 3.072-3.072 3.072-7.68 0-10.752l-155.136-155.136c-3.072-3.072-7.68-3.072-10.752 0-1.536 1.536-2.048 3.072-2.048 5.12l-0.512 133.632c0 15.872 12.288 28.672 28.16 28.672z m502.272-430.592l-99.328-99.328c-23.04-23.04-47.104-18.944-64.512-1.536l-286.208 286.72 165.376 165.888 286.208-286.72c17.408-17.408 21.504-41.984-1.536-65.024zM838.144 944.64h-274.432c-26.624 0-48.64-22.016-48.64-48.64s22.016-48.64 48.64-48.64h274.432c26.624 0 48.64 22.016 48.64 48.64s-21.504 48.64-48.64 48.64z" p-id="2158"></path></svg>`;
  }
}

export const EditMathMenuConf = {
  factory() {
    return new EditMathMenu(); // 把 `YourMenuClass` 替换为你菜单的 class
  },
  key: 'EditMathMenu', // 定义 menu key ：要保证唯一、不重复（重要）
};
