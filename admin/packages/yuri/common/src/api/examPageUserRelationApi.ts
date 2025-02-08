import { BaseEntity, ExamPageUserRelationStatusType, RequestType } from "@yuri/types";
import { BaseApi } from "../base/baseApi/baseApi";

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2025-02-08 16:34:17
 * @Description:
 */
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
