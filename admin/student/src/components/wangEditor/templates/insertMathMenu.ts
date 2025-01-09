import { type IDomEditor } from '@wangeditor-next/editor';
import { type ComponentPublicInstance } from 'vue';
import { BaseButtonMenu } from './baseButton';

class InsertMathMenu extends BaseButtonMenu {
  exec: (editor: IDomEditor, value: boolean | string) => void = (
    editor: IDomEditor,
    _value: boolean | string,
  ) => {
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    editor.insertYuriMath(editor);
  };
  root!: ComponentPublicInstance;
  tag: string;
  title: string;

  constructor() {
    super();
    this.tag = 'button';
    this.title = '插入数学公式';

    this.title = '插入公式'; // 自定义菜单标题
    this.tag = 'button';
  }
}

export const InsertMathMenuConf = {
  factory() {
    return new InsertMathMenu(); // 把 `YourMenuClass` 替换为你菜单的 class
  },
  key: 'InsertMathMenu', // 定义 menu key ：要保证唯一、不重复（重要）
};
