/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2024-12-20 18:59:12
 * @Description:
 */
import { BaseApi } from '#/common/base/baseApi/baseApi';
import { BaseEntity, RequestType } from '#/common/base/baseApi/types';

export enum ExamPageUserRelationStatusType {
  // 进行中
  DOING = 1,

  // 已结束
  FINISHED = 2,

  NOT_START = 0,
}

export class ExamPageUserRelation extends BaseEntity {
  status!: ExamPageUserRelationStatusType;

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.status = ExamPageUserRelationStatusType.NOT_START;
  }
}

class Api extends BaseApi<ExamPageUserRelation> {
  override baseUrl: string = '/exam/page/user/relation';

  /**
   * 发布试卷
   */
  relation = (param: string) => {
    return this.add<ExamPageUserRelation>(RequestType.POST, '/relation', param);
  };
}

export const examPageUserRelationApi = new Api();
