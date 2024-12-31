/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2024-12-31 18:12:55
 * @Description:
 */
import { BaseApi } from '#/common/base/baseApi/baseApi';
import { BaseEntity, RequestType } from '#/common/base/baseApi/types';
import type { ExamPageResult, ExamPageType, SubjectType } from './examPageApi';
import type { ExamPageUserRelationStatusType } from './examPageUserRelationApi';

export interface ExamListParam {
  mode: number;
}

export interface ExamListResult {
  createTime: string;
  id: string;
  name: string;
}

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
  users: {
    id: string;
    username: string;
  }[] = [];

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

class Api extends BaseApi<BaseEntity> {
  override baseUrl: string = '/exam/page/release';

  /**
   * 根据id检查是否正常
   */
  check = (id: string) => {
    return this.add<boolean>(RequestType.POST, `/check`, id);
  };

  /**
   * 根据id获取详情
   */
  detail = (id: string) => {
    return this.add<ExamPageReleaseResult>(RequestType.POST, `/detail/${id}`);
  };

  /**
   * 根据id获取练习信息
   */
  examInfo = (id: string) => {
    return this.add<ExamInfoResult>(RequestType.POST, `/exam/info`, id);
  };

  /**
   * 获取练习列表
   */
  examList = (param: ExamListParam) => {
    return this.add<ExamListResult[]>(RequestType.POST, '/exam/list', param);
  };

  startExam = (releaseId: string) => {
    return this.add<StartExamResult>(
      RequestType.POST,
      '/start/exam',
      releaseId,
    );
  };
}

export const examPageReleaseApi = new Api();
