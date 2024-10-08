/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:37:57
 * @LastEditTime: 2024-10-08 21:02:03
 * @Description: 题目
 */
import { BaseApi } from '#/common/base/baseApi/baseApi';
import { BaseEntity } from '#/common/base/baseApi/types';

export enum QuestionTypeEnum {
  GAP_FILLING = 'GAP_FILLING', // 填空题
  JUDGE = 'JUDGE', // 判断题
  MULTIPLE_CHOICE = 'MULTIPLE_CHOICE', // 多选题
  SINGLE_CHOICE = 'SINGLE_CHOICE', // 单选题
  SUBJECTIVE = 'SUBJECTIVE', // 主观题
}

export class QuestionBank extends BaseEntity {
  /**
   * 答案
   */
  answer!: string;

  /**
   * 题目
   */
  content!: string;

  /**
   * 难度
   */
  difficulty!: number;

  /**
   * 题目类型
   */
  type!: QuestionTypeEnum;

  /**
   * 手动调用reset
   */
  constructor(type?: QuestionTypeEnum) {
    super();
    this.reset();
    if (type) {
      this.type = type;
    }
  }

  override reset(): void {
    this.content = '';
    this.answer = '';
    this.difficulty = 0;
  }
}

class Api extends BaseApi<QuestionBank> {
  override baseUrl: string = '/question/bank';
}

export const questionBankApi = new Api();
