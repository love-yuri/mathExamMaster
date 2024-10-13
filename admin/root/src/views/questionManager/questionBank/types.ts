/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-08 19:49:21
 * @LastEditTime: 2024-10-09 20:26:51
 * @Description: 类型
 */
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

/**
 * 判断题json数据
 */
export interface JudgeAnswer {
  answer: boolean;
}

/**
 * 填空题json数据
 */
export interface GapFillingAnswer {
  answer: {
    // 答案
    value: string;
  }[];
}
