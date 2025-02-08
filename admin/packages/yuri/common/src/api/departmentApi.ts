/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:37:57
 * @LastEditTime: 2025-02-08 14:37:37
 * @Description: 题目
 */
import { BaseApi } from '@yuri/common';
import { RequestType, type TreeResult, Department, type DepartmentDetail } from '@yuri/types';

class Api extends BaseApi<Department> {
  override baseUrl: string = '/department';

  detail = (id: string) => {
    return this.add<DepartmentDetail>(RequestType.POST, '/detail', id);
  };

  tree = () => {
    return this.add<TreeResult>(RequestType.GET, '/tree');
  };
}

export const departmentApi = new Api();
