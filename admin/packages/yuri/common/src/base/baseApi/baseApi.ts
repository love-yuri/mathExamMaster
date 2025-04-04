/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-11 16:05:57
 * @LastEditTime: 2025-03-12 18:56:57
 * @Description: 基础api
 */

import type {
  BaseEntity,
  PageParam,
  PageResult,
  RequestConfig,
} from '@yuri/types';

import { RequestType } from '@yuri/types';

import { requestClient } from './request';

// 定义基础API函数
async function baseApi(config: RequestConfig): Promise<unknown> {
  switch (config.method) {
    case RequestType.GET: {
      return await requestClient.get(config.url, {
        params: config.params,
        timeout: config.timeout,
      });
    }
    case RequestType.POST: {
      return requestClient.post(config.url, config.params, {
        timeout: config.timeout,
      });
    }
  }
}

/**
 * 抽象基类
 * @description 提供基础的API请求方法
 */
export abstract class BaseApi<T extends BaseEntity> {
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

  // 更新
  get = (id: number | string): Promise<T> => {
    return baseApi({
      method: RequestType.POST,
      url: `${this.baseUrl}/get/${id}`,
    }) as Promise<T>;
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

  // 基础添加函数
  protected add = <V>(
    method: RequestType,
    url: string,
    params?: any,
    timeout?: number,
  ): Promise<V> => {
    if (url.startsWith('/')) {
      url = url.slice(1);
    }
    if (url.endsWith('/')) {
      url = url.slice(0, -1);
    }

    return baseApi({
      method,
      params,
      timeout,
      url: `${this.baseUrl}/${url}`,
    }) as Promise<V>;
  };
}
