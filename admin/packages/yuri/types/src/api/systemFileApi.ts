/* eslint-disable prettier/prettier */
/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2025-02-08 14:11:50
 * @Description:
 */
import { BaseEntity} from './base';

export class SystemFile extends BaseEntity {
  filename!: string;
  md5!: string;
  path!: string;
  type!: string;

  constructor() {
    super();
    this.reset();
  }

  override reset() {
    this.filename = '';
    this.md5 = '';
    this.path = '';
    this.type = '';
  }
}