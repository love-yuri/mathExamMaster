import type { QuestionTypeEnum } from './questionBankApi';

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2025-03-13 19:21:36
 * @Description:
 */
export interface GenerateParams {
  dataBaseName: string;
  override: boolean;
  tableName: string;
}

export interface AiCreateQuestionParam {
  description: string;
  type: QuestionTypeEnum;
}

export interface AiCreateScoreResult {
  detail: string;
  score: number;
}
