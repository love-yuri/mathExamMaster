import type { DepartmentDetail, TreeResult } from '@yuri/types';

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:37:57
 * @LastEditTime: 2025-02-12 20:43:21
 * @Description: 题目
 */
import { BaseApi } from '@yuri/common';
import { Department, RequestType } from '@yuri/types';

class Api extends BaseApi<Department> {
  override baseUrl: string = '/department';

  detail = (id: string) => {
    return this.add<DepartmentDetail>(RequestType.POST, '/detail', id);
  };

  ownerDepartments = () => {
    return this.add<Department[]>(RequestType.POST, '/owner/departments');
  };

  tree = () => {
    return this.add<TreeResult>(RequestType.GET, '/tree');
  };
}

export const departmentApi = new Api();
