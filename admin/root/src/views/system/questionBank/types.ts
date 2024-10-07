export interface SingleChoiceAnswer {
  /**
   * 正确答案
   */
  answer: number;

  /**
   * 选项列表
   */
  keys: { value: string }[];
}
