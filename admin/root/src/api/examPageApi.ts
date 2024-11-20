/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2024-11-20 18:52:19
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
  ExamPageResult,
} from '#/views/examPageManager/types';

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
}

export const examPageApi = new Api();
