import type { PageParam } from './base';
import type { ExamPageUserRelationStatusType } from './examPageUserRelationApi';

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2025-03-05 14:49:56
 * @Description:
 */
import { BaseEntity } from './base';
import { Department } from './departmentApi';
import { ExamPageResult, ExamPageType, SubjectType } from './examPageApi';

export interface ExamListParam {
  mode: number;
}

export interface ExamPageReleasePageParam extends PageParam {
  flag: number;
}

export interface ExamListResult {
  createTime: string;
  id: string;
  name: string;
}
/**
 * 试卷发布结果
 */

export interface StartExamResult {
  endTime: string;
  examPageId: string;
  name: string;
  startTime?: string;
}

export interface ExamInfoResult {
  difficulty: number;

  /** 考试结束时间 */
  endTime: string;

  /** 试卷id */
  examPageId: string;

  /** 开始考试时间 */
  examStartTime?: null | string;

  /** 试卷限时 */
  limitedTime: number;

  /** 详情id */
  relationId: string;

  /** 发布id */
  releaseId: string;

  /** 考试开始时间 */
  startTime: string;

  /** 考试状态 */
  status: ExamPageUserRelationStatusType;

  /** 考试科目 */
  subject: SubjectType;

  /** 试卷标题 */
  title: string;

  /** 总分 */
  totalScore: number;

  /** 试卷类型 */
  type: ExamPageType;
}

export class ExamPageReleaseParam extends BaseEntity {
  departmentId!: string;
  endTime?: string;
  examPageId!: string;
  startTime?: string;

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.startTime = undefined;
    this.endTime = undefined;
    this.departmentId = '';
    this.examPageId = '';
  }
}

export interface StudentDetailResult {
  hasGrading: boolean;
  nickname: string;
  relationId: string;
  userId: string;
  username: string;
}

/**
 * 试卷发布结果
 */
export class ExamPageReleaseResult extends ExamPageReleaseParam {
  department!: Department;
  examPage?: ExamPageResult;

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    super.reset();
    this.department = new Department();
    this.examPage = undefined;
  }
}
