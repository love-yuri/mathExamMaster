import type { KnowledgePoint } from './knowledgePointApi';
import type { QuestionCategory } from './questionCategoryApi';

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:37:57
 * @LastEditTime: 2025-03-24 19:47:41
 * @Description: 题目
 */
import { BaseEntity } from './base';

export enum QuestionTypeEnum {
  GAP_FILLING = 'GAP_FILLING', // 填空题
  JUDGE = 'JUDGE', // 判断题
  MULTIPLE_CHOICE = 'MULTIPLE_CHOICE', // 多选题
  SINGLE_CHOICE = 'SINGLE_CHOICE', // 单选题
  SUBJECTIVE = 'SUBJECTIVE', // 主观题
}

/**
 * 题目对应中文标题
 */
export const QuestionTypeMap = {
  [QuestionTypeEnum.GAP_FILLING]: '填空题',
  [QuestionTypeEnum.JUDGE]: '判断题',
  [QuestionTypeEnum.MULTIPLE_CHOICE]: '多选题',
  [QuestionTypeEnum.SINGLE_CHOICE]: '单选题',
  [QuestionTypeEnum.SUBJECTIVE]: '主观题',
};

export const QuestionTypeColorMap = {
  [QuestionTypeEnum.GAP_FILLING]: 'primary',
  [QuestionTypeEnum.JUDGE]: 'secondary',
  [QuestionTypeEnum.MULTIPLE_CHOICE]: 'success',
  [QuestionTypeEnum.SINGLE_CHOICE]: 'info',
  [QuestionTypeEnum.SUBJECTIVE]: 'warn',
};

export abstract class QuestionAnswer extends BaseEntity {
  /**
   * 答案
   */
  abstract answer: unknown;

  /**
   * 题目
   */
  content!: string;

  /**
   * 题目描述
   */
  description!: string;

  /**
   * 难度
   */
  difficulty!: number;

  /**
   * 题目类型
   */
  abstract type: QuestionTypeEnum;

  override reset(): void {
    this.description = '';
    this.content = '';
    this.difficulty = 6;
  }
}

export class SingleChoiceAnswer extends QuestionAnswer {
  answer!: {
    answer?: number;
    options: string[];
    type: QuestionTypeEnum.SINGLE_CHOICE;
  };
  override type = QuestionTypeEnum.SINGLE_CHOICE;

  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    super.reset();
    this.answer = {
      answer: undefined,
      options: [],
      type: QuestionTypeEnum.SINGLE_CHOICE,
    };
  }
}

export class JudgeAnswer extends QuestionAnswer {
  answer!: {
    answer: boolean;
    type: QuestionTypeEnum.JUDGE;
  };
  override type = QuestionTypeEnum.JUDGE;

  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    super.reset();
    this.answer = {
      answer: false,
      type: QuestionTypeEnum.JUDGE,
    };
  }
}

export class SubjectiveAnswer extends QuestionAnswer {
  answer!: {
    answer: string;
    type: QuestionTypeEnum.SUBJECTIVE;
  };
  override type = QuestionTypeEnum.SUBJECTIVE;

  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    super.reset();
    this.answer = {
      answer: '',
      type: QuestionTypeEnum.SUBJECTIVE,
    };
  }
}

export class GapFillingAnswer extends QuestionAnswer {
  answer!: {
    answer: string[];
    type: QuestionTypeEnum.GAP_FILLING;
  };
  override type = QuestionTypeEnum.GAP_FILLING;

  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    super.reset();
    this.answer = {
      answer: [],
      type: QuestionTypeEnum.GAP_FILLING,
    };
  }
}
export type QuestionBank = QuestionAnswer;
export class MultipleChoiceAnswer extends QuestionAnswer {
  answer!: {
    answer: number[];
    options: string[];
    type: QuestionTypeEnum.MULTIPLE_CHOICE;
  };
  override type = QuestionTypeEnum.MULTIPLE_CHOICE;

  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    super.reset();
    this.answer = {
      answer: [],
      options: [],
      type: QuestionTypeEnum.MULTIPLE_CHOICE,
    };
  }
}

export interface SaveQuestionAnswerParam {
  knowledgePointIds: string[];
  questionBank: QuestionAnswer;
  questionCategoryIds: string[];
}

export interface FullQuestionBank {
  categories: QuestionCategory[];
  knowledgePoints: KnowledgePoint[];
  questionBank: QuestionAnswer;
}

export type SingleChoiceAw = SingleChoiceAnswer['answer'];
export type GapFillingAw = GapFillingAnswer['answer'];
export type MultipleChoiceAw = MultipleChoiceAnswer['answer'];
export type JudgeAw = JudgeAnswer['answer'];
export type SubjectiveAw = SubjectiveAnswer['answer'];
