import type { FullQuestionBank, QuestionTypeEnum } from './questionBankApi';

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2025-03-24 19:47:54
 * @Description:
 */
import { BaseEntity } from './base';

export interface ExamPageQuestionRelation {
  fullQuestionBank?: FullQuestionBank;
  questionBankId: string;
  score: number;
}

export interface UserAnswer {
  hasAnswer: boolean;
  questionAnswer: unknown;
  questionId: string;
}

export interface QuestionInfo {
  /**
   * 题目内容
   */
  content: string;
  /**
   * 主键id
   * 类型为字符串，表示可能是长整型的序列化形式
   */
  id?: string;

  /**
   * 题目序号
   */
  index: number;

  /**
   * 题目类型
   */
  type: QuestionTypeEnum;

  /**
   * 答案
   */
  userAnswer: UserAnswer;
}

/**
 * 问题信息结果
 */
export interface QuestionInfoResult {
  infos: QuestionInfo[];
  /**
   * 题目类型
   */
  type: QuestionTypeEnum;
}

export type QuestionAndPoint = FullQuestionBank & {
  score: number;
};

export enum ExamPageType {
  DEFULT = 0,
}

export const ExamPageMap = {
  [ExamPageType.DEFULT]: '默认类型',
};

export enum SubjectType {
  HighMath = 0, // 高数
}

export const SubjectTypeMap = {
  [SubjectType.HighMath]: '高等数学',
};
export class ExamPageCreateVO extends BaseEntity {
  difficulty!: number;
  limitedTime!: number;
  questions!: ExamPageQuestionRelation[];
  subject!: SubjectType;
  title!: string;
  totalScore!: number;
  type!: ExamPageType;

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.difficulty = 6;
    this.limitedTime = 7200;
    this.totalScore = 100;
    this.title = '';
    this.type = ExamPageType.DEFULT;
    this.subject = SubjectType.HighMath;
    this.questions = [];
  }
}

/**
 * 试卷结果
 */
export class ExamPageResult extends ExamPageCreateVO {
  releaseTime!: string;

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    super.reset();
    this.releaseTime = '';
  }
}

type BaseOption = {
  label: string;
  value: number;
};

export const subjectOptions: BaseOption[] = [
  {
    label: '高等数学',
    value: SubjectType.HighMath,
  },
];

export const typeOptions: BaseOption[] = [
  {
    label: '普通试卷',
    value: ExamPageType.DEFULT,
  },
];
