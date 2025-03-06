import type { UserAnswer } from './examPageApi';
import type { QuestionTypeEnum } from './questionBankApi';

import { BaseEntity } from './base';

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-24 19:01:29
 * @LastEditTime: 2025-03-03 16:59:16
 * @Description:
 */
export interface UserScoreDetail {
  hasSetScore: boolean;
  questionAnswer: unknown;
  questionId: string;
  score: number;
  totalScore: number;
  type: QuestionTypeEnum;
  userAnswer: UserAnswer;
}

export class UserScore extends BaseEntity {
  detail!: UserScoreDetail[];
  hasGrading!: boolean;
  pageReleaseId!: string;
  score!: number;
  totalScore!: number;
  userId!: string;

  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.userId = '';
    this.pageReleaseId = '';
    this.score = 0;
    this.totalScore = 0;
    this.detail = [];
    this.hasGrading = false;
  }
}
