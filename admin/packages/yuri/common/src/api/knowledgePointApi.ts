/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-03 19:44:49
 * @LastEditTime: 2025-02-08 14:36:06
 * @Description: 知识点api
 */
import type { KnowledgePoint } from '@yuri/types';
import { BaseApi } from '..';

class Api extends BaseApi<KnowledgePoint> {
  override baseUrl: string = '/knowledge/point';
}

export const knowledgePointApi = new Api();
