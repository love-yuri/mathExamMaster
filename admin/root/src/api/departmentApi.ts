/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:37:57
 * @LastEditTime: 2024-12-12 20:36:17
 * @Description: 题目
 */
import { BaseApi } from '#/common/base/baseApi/baseApi';
import { BaseEntity, RequestType } from '#/common/base/baseApi/types';

export interface TreeResult {
  children?: TreeResult[];
  key: string;
  label: string;
}

export class Department extends BaseEntity {
  /**
   * 组织名称
   */
  name!: string;
  /**
   * 父级id
   */
  parentId!: string;

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.name = '';
    this.parentId = '';
  }
}

class Api extends BaseApi<Department> {
  override baseUrl: string = '/department';

  tree = () => {
    return this.add<TreeResult>(RequestType.GET, '/tree');
  };
}

export const departmentApi = new Api();
