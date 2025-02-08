/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:37:57
 * @LastEditTime: 2025-02-08 14:35:35
 * @Description: 题目
 */
import { RequestType, UserDepartment, type BatchSaveParam } from '@yuri/types';
import { BaseApi } from '../base/baseApi/baseApi';

class Api extends BaseApi<UserDepartment> {
  override baseUrl: string = '/user/department';

  batchSave = async (params: BatchSaveParam) => {
    return this.add<boolean>(RequestType.POST, '/batch/save', params);
  };
}

export const userDepartmentApi = new Api();
