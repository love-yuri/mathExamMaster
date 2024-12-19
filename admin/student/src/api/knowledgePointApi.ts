/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-03 19:44:49
 * @LastEditTime: 2024-10-03 22:51:44
 * @Description: 知识点api
 */
import { BaseApi } from '#/common/base/baseApi/baseApi';
import { BaseEntity } from '#/common/base/baseApi/types';

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

class Api extends BaseApi<KnowledgePoint> {
  override baseUrl: string = '/knowledge/point';
}

export const knowledgePointApi = new Api();
