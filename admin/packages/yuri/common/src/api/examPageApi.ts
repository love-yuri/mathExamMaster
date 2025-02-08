/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2025-02-08 16:36:43
 * @Description:
 */

import { type BaseEntity, type ExamPageResult, RequestType, type PageParam, type PageResult, type ExamPageCreateVO, type QuestionInfoResult } from "@yuri/types";
import { BaseApi } from "../base/baseApi/baseApi";

class Api extends BaseApi<BaseEntity> {
  override baseUrl: string = '/exam/page';

  /**
   * 根据id获取详情
   */
  detail = (id: string) => {
    return this.add<ExamPageResult>(RequestType.POST, '/detail', id);
  };

  /**
   * 分页获取试卷
   */
  pageSimple = (param: PageParam) => {
    return this.add<PageResult<ExamPageResult>>(
      RequestType.POST,
      '/page/simple',
      param,
    );
  };

  /**
   * 发布试卷
   */
  release = (param: ExamPageCreateVO) => {
    return this.add<boolean>(RequestType.POST, '/release', param);
  };

  /**
   * 发布试卷
   */
  updatePage = (param: ExamPageCreateVO) => {
    return this.add<boolean>(RequestType.POST, '/update/page', param);
  };

  /**
   * 交卷
   */
  overExam = (id: string) => {
    return this.add<boolean>(RequestType.POST, '/over/exam', id);
  };

  /**
   * 根据id获取题目信息
   */
  questionInfo = (param: { examPageId: string; relationId: string }) => {
    return this.add<QuestionInfoResult[]>(
      RequestType.POST,
      '/question/info',
      param,
    );
  };

  /**
   * 更新用户答案
   */
  updateUserAnswer = (param: { answer: any; relationId: string }) => {
    return this.add<boolean>(RequestType.POST, '/update/user/answer', param);
  };
}

export const examPageApi = new Api();
