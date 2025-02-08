/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2025-02-08 14:23:13
 * @Description:
 */
import { BaseEntity} from './base';
import { ExamPageResult } from './examPageApi';
import { User } from './userApi';

export class ExamPageReleaseParam extends BaseEntity {
  endTime!: string;
  examPageId!: string;
  startTime!: string;
  userIds!: string[];

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.startTime = '';
    this.endTime = '';
    this.userIds = [];
    this.examPageId = '';
  }
}

/**
 * 试卷发布结果
 */
export class ExamPageReleaseResult extends ExamPageReleaseParam {
  examPage?: ExamPageResult;
  users: User[] = [];

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    super.reset();
    this.users = [];
    this.examPage = undefined;
  }
}
