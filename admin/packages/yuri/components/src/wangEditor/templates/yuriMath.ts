import {
  DomEditor,
  type IDomEditor,
  type SlateDescendant,
  SlateElement,
} from '@wangeditor-next/editor';
import { h, type VNode } from 'snabbdom';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import katex from 'katex';
import katexStyleContent from 'katex/dist/katex.css?raw';

/**
 * yuri-math 节点
 */
export type YuriMathNode = {
  children: [{ text: string }];
  math: string;
  type: 'yuri-math';
};

/**
 * 渲染数学符号
 */
export class YuriMathElement extends HTMLElement {
  private span: HTMLElement;

  constructor() {
    super();
    const shadow = this.attachShadow({ mode: 'open' });
    const document = shadow.ownerDocument;

    const style = document.createElement('style');
    style.innerHTML = katexStyleContent; // 加载 css 文本
    shadow.append(style);

    const span = document.createElement('span');
    span.style.display = 'inline-block';
    shadow.append(span);
    this.span = span;
  }

  private render(value: string) {
    katex.render(value, this.span, {
      throwOnError: false,
    });
  }

  attributeChangedCallback(
    _name: string,
    oldValue: null | string,
    newValue: null | string,
  ) {
    if (newValue !== oldValue) {
      this.render(this.dataset.math!); // 重新渲染
    }
  }

  // 当元素被插入 DOM 时触发
  connectedCallback() {
    this.render(String.raw`${this.dataset.math}`);
  }

  static get observedAttributes() {
    return ['data-math']; // 监控 'math' 属性的变化
  }
}

// 注册自定义元素
customElements.define('yuri-math', YuriMathElement);

// 渲染自定义元素
export function renderYuriMath(
  elem: SlateElement,
  _children: null | VNode[],
  editor: IDomEditor,
): VNode {
  const { math } = elem as YuriMathNode;

  // 当前节点是否选中
  const selected = DomEditor.isNodeSelected(editor, elem);

  const mathNode = h(
    'yuri-math',
    {
      dataset: {
        math: String.raw`${math}`,
      },
    },
    null,
  );

  // 构建容器 vnode
  const containerVnode = h(
    'div',
    {
      props: {
        contentEditable: false, // 不可编辑
      },
      style: {
        border: selected ? '2px solid #FF99CC' : '2px solid transparent',
        borderRadius: '4px',
        display: 'inline-block', // inline
        margin: '0 8px',
        padding: selected ? '5px 10px' : '3px',
      },
    },
    [mathNode],
  );

  return containerVnode;
}

/**
 * 将自定义元素转换为 HTML
 */
function elementToHtml(elem: SlateElement, _children: string): string {
  return `<yuri-math math="${(elem as YuriMathNode).math}"></yuri-math>`;
}

/**
 * 将 HTML 转换为自定义元素
 */
export function parseHtml(
  domElem: Element,
  _: SlateDescendant[],
  __: IDomEditor,
): SlateElement {
  return {
    math: domElem.getAttribute('math') || '',
    type: 'yuri-math',
    children: [{ text: '' }],
  } as SlateElement;
}

/**
 * 导出配置
 */
export const yuriMathConf = {
  elemToHtmlConf: {
    elemToHtml: elementToHtml,
    type: 'yuri-math', // 新元素的 type ，重要！！！
  },
  parseHtmlConf: {
    parseElemHtml: parseHtml,
    selector: 'yuri-math', // CSS 选择器，匹配特定的 HTML 标签
  },
  renderConf: {
    renderElem: renderYuriMath,
    type: 'yuri-math', // 新增的组件类型
  },
};
