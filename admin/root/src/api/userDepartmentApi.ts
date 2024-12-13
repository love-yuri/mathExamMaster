/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:37:57
 * @LastEditTime: 2024-12-13 20:37:40
 * @Description: 题目
 */
import { BaseApi } from '#/common/base/baseApi/baseApi';
import { BaseEntity, RequestType } from '#/common/base/baseApi/types';

export interface BatchSaveParam {
  departmentId: string;
  userIds: string[];
}

export class UserDepartment extends BaseEntity {
  departmentId!: string;
  userId!: string;

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.userId = '';
    this.departmentId = '';
  }
}

class Api extends BaseApi<UserDepartment> {
  override baseUrl: string = '/user/department';

  batchSave = async (params: BatchSaveParam) => {
    return this.add<boolean>(RequestType.POST, '/batch/save', params);
  };
}

export const userDepartmentApi = new Api();
