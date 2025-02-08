/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:37:57
 * @LastEditTime: 2025-02-08 14:21:28
 * @Description: 题目
 */
import { BaseEntity} from './base';

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