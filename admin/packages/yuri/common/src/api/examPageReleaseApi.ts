/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2025-02-24 15:31:28
 * @Description:
 */

import type {
  BaseEntity,
  ExamInfoResult,
  ExamListParam,
  ExamListResult,
  ExamPageCreateVO,
  ExamPageReleasePageParam,
  ExamPageReleaseParam,
  ExamPageReleaseResult,
  PageResult,
  StartExamResult,
  StudentDetailResult,
} from '@yuri/types';

import { RequestType } from '@yuri/types';

import { BaseApi } from '../base/baseApi/baseApi';

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

  /**
   * 分页获取试卷
   */
  pageSimple = (param: ExamPageReleasePageParam) => {
    return this.add<PageResult<ExamPageReleaseResult>>(
      RequestType.POST,
      '/page/simple',
      param,
    );
  };

  /**
   * 发布试卷
   */
  release = (param: ExamPageReleaseParam) => {
    return this.add<boolean>(RequestType.POST, '/release', param);
  };

  /**
   * 更新发布试卷
   */
  releaseUpdate = (param: ExamPageReleaseParam) => {
    return this.add<boolean>(RequestType.POST, '/release/update', param);
  };

  startExam = (releaseId: string) => {
    return this.add<StartExamResult>(
      RequestType.POST,
      '/start/exam',
      releaseId,
    );
  };

  /**
   * 根据id获取学生详情
   */
  studentDetail = (id: string) => {
    return this.add<StudentDetailResult[]>(
      RequestType.POST,
      `/student/detail/${id}`,
    );
  };

  /**
   * 发布试卷
   */
  updatePage = (param: ExamPageCreateVO) => {
    return this.add<boolean>(RequestType.POST, '/update/page', param);
  };
}

export const examPageReleaseApi = new Api();
