/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-03 19:44:49
 * @LastEditTime: 2025-02-08 14:11:00
 * @Description: 知识点api
 */
import { BaseEntity} from './base';

export class KnowledgePoint extends BaseEntity {
  /**
   * 知识点描述
   */
  description!: string;

  /**
   * 知识点名称
   */
  name!: string;

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.name = '';
    this.description = '';
  }
}