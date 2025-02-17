/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:37:57
 * @LastEditTime: 2025-02-17 14:44:19
 * @Description: 题目
 */
import { BaseEntity} from './base';
import type { UserResult } from './userApi';

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

export interface DepartmentDetail {
  createTime: string;
  id: string;
  name: string;
  parentId: string;
  users: UserResult[];
  teacherInfo?: UserResult;
}
