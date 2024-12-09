/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2024-12-09 18:54:48
 * @Description:
 */
import {
  BaseApi,
  type PageParam,
  type PageResult,
} from '#/common/base/baseApi/baseApi';
import { type BaseEntity, RequestType } from '#/common/base/baseApi/types';
import {
  ExamPageCreateVO,
  ExamPageReleaseParam,
  type ExamPageReleaseResult,
} from '#/views/examPageManager/types';

class Api extends BaseApi<BaseEntity> {
  override baseUrl: string = '/exam/page/release';

  /**
   * 根据id获取详情
   */
  detail = (id: string) => {
    return this.add<ExamPageReleaseResult>(RequestType.POST, `/detail/${id}`);
  };

  /**
   * 分页获取试卷
   */
  pageSimple = (param: PageParam) => {
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

  /**
   * 发布试卷
   */
  updatePage = (param: ExamPageCreateVO) => {
    return this.add<boolean>(RequestType.POST, '/update/page', param);
  };
}

export const examPageReleaseApi = new Api();
