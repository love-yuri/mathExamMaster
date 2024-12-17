/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-11 16:05:57
 * @LastEditTime: 2024-10-13 13:44:56
 * @Description: 基础api
 */

import { requestClient } from '#/common/base/baseApi/request';

import { type BaseEntity, type RequestConfig, RequestType } from './types';

/**
 * page 请求参数
 * @param current 当前页
 * @param size 每页大小
 */
export interface PageParam {
  current: number;
  size: number;
  total?: number;
}

/**
 * page 请求结果
 * @param current 当前页
 * @param records 数据列表
 * @param size 每页大小
 * @param total 总数
 */
export interface PageResult<T> extends BaseEntity {
  current: number;
  records: T[];
  size: number;
  total: number;
}

// 定义基础API函数
async function baseApi(config: RequestConfig): Promise<unknown> {
  switch (config.method) {
    case RequestType.GET: {
      return await requestClient.get(config.url, {
        params: config.params,
      });
    }
    case RequestType.POST: {
      return requestClient.post(config.url, config.params);
    }
  }
}

/**
 * 抽象基类
 * @description 提供基础的API请求方法
 */
export abstract class BaseApi<T extends BaseEntity> {
  // 基础添加函数
  protected add = <V>(
    method: RequestType,
    url: string,
    params?: any,
  ): Promise<V> => {
    if (url.startsWith('/')) {
      url = url.slice(1);
    }

    return baseApi({
      method,
      params,
      url: `${this.baseUrl}/${url}`,
    }) as Promise<V>;
  };

  abstract readonly baseUrl: string;

  // 创建
  create = (params: T): Promise<boolean> => {
    return baseApi({
      method: RequestType.POST,
      params,
      url: `${this.baseUrl}/create`,
    }) as Promise<boolean>;
  };

  // 删除
  delete = (id: number | string): Promise<boolean> => {
    return baseApi({
      method: RequestType.POST,
      url: `${this.baseUrl}/delete/${id}`,
    }) as Promise<boolean>;
  };

  // 列表
  list = (): Promise<T[]> => {
    return baseApi({
      method: RequestType.POST,
      url: `${this.baseUrl}/list`,
    }) as Promise<T[]>;
  };

  /**
   * 分页
   * @param params 分页参数
   */
  page = (params: PageParam): Promise<PageResult<T>> => {
    return baseApi({
      method: RequestType.POST,
      params,
      url: `${this.baseUrl}/page`,
    }) as Promise<PageResult<T>>;
  };

  // 更新
  update = (params: T): Promise<boolean> => {
    return baseApi({
      method: RequestType.POST,
      params,
      url: `${this.baseUrl}/update`,
    }) as Promise<boolean>;
  };
}
