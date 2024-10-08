/**
 * 单选题json数据
 */
export interface SingleChoiceAnswer {
  /**
   * 正确答案
   * 选项的顺序 从0开始
   */
  answer?: number;

  /**
   * 选项列表
   */
  keys: {
    // 选项的值
    value: string;
  }[];
}

/**
 * 多选题json数据
 */
export interface MultipleChoiceAnswer {
  /**
   * 正确答案
   * 选项的顺序 从0开始
   */
  answer: number[];

  /**
   * 选项列表
   */
  keys: {
    // 选项的值
    value: string;
  }[];
}
