/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:37:57
 * @LastEditTime: 2024-12-28 15:40:36
 * @Description: 题目
 */
import {
  BaseApi,
  type PageParam,
  type PageResult,
} from '#/common/base/baseApi/baseApi';
import { BaseEntity, RequestType } from '#/common/base/baseApi/types';
import type { KnowledgePoint } from './knowledgePointApi';

export interface QuestionAnswer {
  answer?: any;
  hasAnswer: boolean;
  index: number;
}

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
    this.difficulty = 5;
  }
}

export interface SaveQuestionBankParam {
  knowledgePointIds: string[];
  questionBank: QuestionBank;
}

export interface FullQuestionBank {
  knowledgePoints: KnowledgePoint[];
  questionBank: QuestionBank;
}

class Api extends BaseApi<QuestionBank> {
  override baseUrl: string = '/question/bank';

  detail = (id: string) => {
    return this.add<FullQuestionBank>(RequestType.POST, `/detail/${id}`);
  };

  pageSimple = (param: PageParam) => {
    return this.add<PageResult<FullQuestionBank>>(
      RequestType.POST,
      '/page/simple',
      param,
    );
  };

  saveSimple = (param: SaveQuestionBankParam) => {
    return this.add<boolean>(RequestType.POST, '/save/simple', param);
  };

  updateSimple = (param: SaveQuestionBankParam) => {
    return this.add<boolean>(RequestType.POST, '/update/simple', param);
  };
}

export const questionBankApi = new Api();
