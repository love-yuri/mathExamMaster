import type { FullQuestionBank } from '#/api/questionBankApi';
import type { User } from '#/api/userApi';
import { BaseEntity } from '#/common/base/baseApi/types';

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-11-06 19:42:04
 * @LastEditTime: 2024-12-05 19:57:31
 * @Description: 创建试卷常用接口
 */

export interface ExamPageQuestionRelation {
  fullQuestionBank?: FullQuestionBank;
  questionBankId: string;
  score: number;
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

export class ExamPageReleaseParam extends BaseEntity {
  endTime!: string;
  examPageId!: string;
  startTime!: string;
  userIds!: string[];

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.startTime = '';
    this.endTime = '';
    this.userIds = [];
    this.examPageId = '';
  }
}

/**
 * 试卷发布结果
 */
export class ExamPageReleaseResult extends ExamPageReleaseParam {
  examPage?: ExamPageResult;
  users: User[] = [];

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    super.reset();
    this.users = [];
    this.examPage = undefined;
  }
}
