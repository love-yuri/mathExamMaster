/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2024-12-23 19:24:51
 * @Description:
 */
import { BaseApi } from '#/common/base/baseApi/baseApi';
import { BaseEntity, RequestType } from '#/common/base/baseApi/types';
import type { ExamPageResult } from './examPageApi';

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

class Api extends BaseApi<BaseEntity> {
  override baseUrl: string = '/exam/page/release';

  /**
   * 根据id获取详情
   */
  detail = (id: string) => {
    return this.add<ExamPageReleaseResult>(RequestType.POST, `/detail/${id}`);
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
