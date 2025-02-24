/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:37:57
 * @LastEditTime: 2025-02-23 21:05:42
 * @Description: 题目
 */
import { BaseEntity } from './base';
import type { KnowledgePoint } from './knowledgePointApi';

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
   * 难度
   */
  difficulty!: number;

  /**
   * 题目类型
   */
  abstract type: QuestionTypeEnum;

  override reset(): void {
    this.content = '';
    this.difficulty = 5;
  }
}

export class SingleChoiceAnswer extends QuestionAnswer {
  override type = QuestionTypeEnum.SINGLE_CHOICE;
  answer!: {
    type: QuestionTypeEnum.SINGLE_CHOICE;
    answer?: number;
    options: string[];
  };

  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.content = '';
    this.difficulty = 5;
    this.answer = {
      type: QuestionTypeEnum.SINGLE_CHOICE,
      answer: undefined,
      options: [],
    };
  }
}

export class JudgeAnswer extends QuestionAnswer {
  override type = QuestionTypeEnum.JUDGE;
  answer!: {
    type: QuestionTypeEnum.JUDGE;
    answer: boolean;
  };

  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.content = '';
    this.difficulty = 5;
    this.answer = {
      type: QuestionTypeEnum.JUDGE,
      answer: false,
    };
  }
}

export class SubjectiveAnswer extends QuestionAnswer {
  override type = QuestionTypeEnum.SUBJECTIVE;
  answer!: {
    type: QuestionTypeEnum.SUBJECTIVE;
    answer: string;
  };

  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.content = '';
    this.difficulty = 5;
    this.answer = {
      type: QuestionTypeEnum.SUBJECTIVE,
      answer: '',
    };
  }
}

export class GapFillingAnswer extends QuestionAnswer {
  override type = QuestionTypeEnum.GAP_FILLING;
  answer!: {
    type: QuestionTypeEnum.GAP_FILLING;
    answer: string[];
  };

  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.content = '';
    this.difficulty = 5;
    this.answer = {
      type: QuestionTypeEnum.GAP_FILLING,
      answer: [],
    };
  }
}

export class MultipleChoiceAnswer extends QuestionAnswer {
  override type = QuestionTypeEnum.MULTIPLE_CHOICE;
  answer!: {
    type: QuestionTypeEnum.MULTIPLE_CHOICE;
    answer: number[];
    options: string[];
  };

  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.content = '';
    this.difficulty = 5;
    this.answer = {
      type: QuestionTypeEnum.MULTIPLE_CHOICE,
      answer: [],
      options: [],
    };
  }
}

export interface SaveQuestionAnswerParam {
  knowledgePointIds: string[];
  QuestionAnswer: QuestionAnswer;
}

export interface FullQuestionBank {
  knowledgePoints: KnowledgePoint[];
  QuestionAnswer: QuestionAnswer;
}

export type SingleChoiceAw = SingleChoiceAnswer['answer'];
export type GapFillingAw = GapFillingAnswer['answer'];
export type MultipleChoiceAw = MultipleChoiceAnswer['answer'];
export type JudgeAw = JudgeAnswer['answer'];
export type SubjectiveAw = SubjectiveAnswer['answer'];