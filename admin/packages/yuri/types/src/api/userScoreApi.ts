import { BaseEntity } from "./base";
import type { UserAnswer } from "./examPageApi";
import type { QuestionAnswer } from "./questionBankApi";

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-24 19:01:29
 * @LastEditTime: 2025-02-24 19:04:57
 * @Description:
 */
export interface UserScoreDetail {
  questionId: string;
  questionAnswer: QuestionAnswer;
  userAnswer: UserAnswer;
  score: number;
  totalScore: number;
}

export class UserScore extends BaseEntity {
  pageReleaseId!: string;
  userId!: string;
  score!: number;
  totalScore!: number;
  detail!: UserScoreDetail[];

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
  }
}