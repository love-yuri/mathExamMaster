import type { FullQuestionBank } from '#/api/questionBankApi';
import { BaseEntity } from '#/common/base/baseApi/types';

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-11-06 19:42:04
 * @LastEditTime: 2024-11-15 19:33:40
 * @Description: 创建试卷常用接口
 */

export interface ExamPageQuestionRelation {
  questionBankId: string;
  score: number;
}

export type QuestionAndPoint = {
  score: number;
} & FullQuestionBank;

export class ExamPageCreateVO extends BaseEntity {
  deadline?: Date;
  difficulty!: number;
  limitedTime!: number;
  questions!: ExamPageQuestionRelation[];
  subject!: number;
  title!: string;
  totalScore!: number;
  type!: number;
  users!: string[];

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.difficulty = 5;
    this.limitedTime = 7200;
    this.totalScore = 100;
    this.title = '';
    this.type = 0;
    this.subject = 0;
    this.deadline = undefined;
    this.questions = [];
    this.users = [];
  }
}

type BaseOption = {
  label: string;
  value: number;
};

export const subjectOptions: BaseOption[] = [
  {
    label: '高等数学',
    value: 0,
  },
];

export const typeOptions: BaseOption[] = [
  {
    label: '普通试卷',
    value: 0,
  },
];
