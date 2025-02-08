/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2025-02-08 16:37:19
 * @Description:
 */
import { BaseEntity} from './base';
import type { FullQuestionBank, QuestionTypeEnum } from './questionBankApi';

export interface ExamPageQuestionRelation {
  fullQuestionBank?: FullQuestionBank;
  questionBankId: string;
  score: number;
}

export interface QuestionInfo {
  /**
   * 答案
   */
  answer: string[];
  /**
   * 题目内容
   */
  content: string;
  /**
   * 是否已作答
   */
  hasAnswer: boolean;
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
   * 题目选项
   * 由于是任意类型，可根据具体实现进一步细化
   */
  options: string[];
  /**
   * 题目类型
   */
  type: QuestionTypeEnum;
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

export type QuestionAndPoint = {
  score: number;
} & FullQuestionBank;

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
    this.difficulty = 5;
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

