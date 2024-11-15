/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2024-11-15 18:35:16
 * @Description:
 */
import { BaseApi } from '#/common/base/baseApi/baseApi';
import { type BaseEntity, RequestType } from '#/common/base/baseApi/types';
import { ExamPageCreateVO } from '#/views/testPaper/types';

class Api extends BaseApi<BaseEntity> {
  override baseUrl: string = '/exam/page';

  /**
   * 发布试卷
   */
  release = (param: ExamPageCreateVO) => {
    return this.add<boolean>(RequestType.POST, '/release', param);
  };
}

export const examPageApi = new Api();
