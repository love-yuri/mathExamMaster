/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2024-12-18 18:52:24
 * @Description:
 */
import { BaseApi } from '#/common/base/baseApi/baseApi';
import { type BaseEntity, RequestType } from '#/common/base/baseApi/types';

export interface ExamListParam {
  mode: number;
}

export interface ExamListResult {
  createTime: string;
  id: string;
  name: string;
}

class Api extends BaseApi<BaseEntity> {
  override baseUrl: string = '/exam/page/release';

  /**
   * 获取练习列表
   */
  examList = (param: ExamListParam) => {
    return this.add<ExamListResult[]>(RequestType.POST, '/exam/list', param);
  };
}

export const examPageReleaseApi = new Api();
