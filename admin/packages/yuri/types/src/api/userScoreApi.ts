import type { UserAnswer } from './examPageApi';
import type { QuestionTypeEnum } from './questionBankApi';

import { BaseEntity } from './base';

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-24 19:01:29
 * @LastEditTime: 2025-03-09 00:17:41
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
  }
}

/**
 * 每道题目的统计信息
 */
export interface QuestionStatistic {
  /** 正确人数 */
  correctCount: number;

  /** index 从 1 开始 */
  index: number;

  /** 该题班级最高分 */
  maxScore: number;

  /** 该题班级最低分 */
  minScore: number;

  /** 题号 */
  questionId: string; // 因为在 Kotlin 里是 Long，TS 可能用 string 来表示大整数

  /** 该题的得分率 */
  scoreRate: number;

  /** 试卷总分 */
  totalScore: number;

  /** 类型 */
  type: QuestionTypeEnum;
}

/**
 * 试卷成绩详情结果
 */
export interface ScoreDetailResult {
  /** 班级平均分 */
  averageScore: number;

  /** 60分以下的学生人数 */
  below60: number;

  /** 班级最高分 */
  maxScore: number;

  /** 班级中位数 */
  medianScore: number;

  /** 班级最低分 */
  minScore: number;

  /** 60分以上的学生人数 */
  over60: number;

  /** 70分以上的学生人数 */
  over70: number;

  /** 80分以上的学生人数 */
  over80: number;

  /** 90分以上的学生人数 */
  over90: number;

  /** 及格率 */
  passRate: number;

  /** 每道题目的统计信息 */
  questionStatistics: QuestionStatistic[];

  /** 试卷总分 */
  totalScore: number;

  /** 学生总人数 */
  totalStudent: number;
}
