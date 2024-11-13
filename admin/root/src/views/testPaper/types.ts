import type { FullQuestionBank } from '#/api/questionBankApi';
import { BaseEntity } from '#/common/base/baseApi/types';

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-11-06 19:42:04
 * @LastEditTime: 2024-11-13 21:29:26
 * @Description: 创建试卷常用接口
 */

export interface ExamPageQuestionRelation {
  exam_page_id: string;
  question_bank_id: string;
  score: number;
}

export interface ExamPageUserRelation {
  exam_page_id: string;
  score: number;
  user_id: string;
}

export type QuestionAndPoint = {
  score: number;
} & FullQuestionBank;

export class ExamPageCreateVO extends BaseEntity {
  deadline?: Date;
  difficulty!: number;
  limited_time!: number;
  questions!: ExamPageQuestionRelation[];
  subject!: number;
  title!: string;
  total_score!: number;
  type!: number;
  users!: ExamPageUserRelation[];

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.difficulty = 5;
    this.limited_time = 7200;
    this.total_score = 100;
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
