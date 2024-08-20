/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-11 16:05:57
 * @LastEditTime: 2024-08-21 00:55:59
 * @Description: 基础api
 */

import axios, { type AxiosResponse } from 'axios';
import message from '@/common/utils/message';

// 获取环境变量中的基础URL
const baseURL = import.meta.env.VITE_BASE_URL;

/**
 * @description: 请求类型
 */
export enum RequestType {
  GET,
  POST,
}

/**
 * @description: 请求配置
 */
type RequestConfig = {
  url: string;
  method: RequestType;
  params?: Object;
};

/**
 * @description: 响应类型
 */
type R = {
  code: number;
  data: any;
  message: string;
  isSuccess: boolean;
};

// 创建axios实例，设置基础URL和超时时间
const baseAxios = axios.create({
  baseURL,
  timeout: 5000,
});

/**
 * @description: 配置axios请求拦截器
 */
baseAxios.interceptors.request.use(
  function (config) {
    config.headers['Authorization'] =
      `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicm9sZSI6MSwiZXhwIjoxNzI0MTk3OTA0fQ.I0W8SnZRuUJ4qKwktmpuwN4tTEMn7s7b29AvaNgTwa8`;
    return config;
  },
  function (error) {
    message.error('请求失败');
    return Promise.reject(error);
  },
);

/**
 * @description: 配置axios返回拦截器
 */
baseAxios.interceptors.response.use(
  function (response: AxiosResponse) {
    const data = response.data as R;
    if (data.isSuccess) {
      return data.data;
    } else {
      message.error(`请求失败! code: ${data.code} -> ${data.message}`);
      throw new Error(`请求失败! code: ${data.code} -> ${data.message}`);
    }
  },
  function (error) {
    // 处理 HTTP 错误
    if (error.response) {
      // 请求已发出，服务器响应状态码不在 2xx 范围
      const data = error.response.data as R;
      message.error(`请求失败: ${data.code} -> ${data.message}`);
      return Promise.reject(error);
    } else if (error.request) {
      // 请求已发出，但没有收到响应
      message.error(`请求失败: 未知URL`);
      return Promise.reject(error);
    } else {
      // 其他错误，例如设置请求时发生了错误
      message.error(`请求失败: ${error.message}`);
      return Promise.reject(error);
    }
  },
);

// 定义基础API函数
async function baseApi(config: RequestConfig): Promise<Object> {
  switch (config.method) {
    case RequestType.GET:
      return await baseAxios.get(config.url, {
        params: config.params,
      });
    case RequestType.POST:
      return baseAxios.post(config.url, config.params);
  }
}

/**
 * 抽象基类
 * @description 提供基础的API请求方法
 */
export abstract class BaseApi {
  abstract readonly baseUrl: string;

  // 基础添加函数
  protected add = (method: RequestType, url: string, params?: any): Promise<any> => {
    if (url.endsWith('/')) {
      url = url.slice(0, -1);
    }

    return baseApi({
      url: `${this.baseUrl}/${url}`,
      method: method,
      params,
    });
  };

  // 创建
  create = (params: any): Promise<any> => {
    return baseApi({
      url: `${this.baseUrl}/create`,
      method: RequestType.POST,
      params,
    });
  };

  // 更新
  update = (params: any): Promise<any> => {
    return baseApi({
      url: `${this.baseUrl}/update`,
      method: RequestType.POST,
      params,
    });
  };

  // 删除
  delete = (id: string | number): Promise<any> => {
    return baseApi({
      url: `${this.baseUrl}/delete/${id}`,
      method: RequestType.POST,
    });
  };

  // 创建
  page = (params: any): Promise<any> => {
    return baseApi({
      url: `${this.baseUrl}/page`,
      method: RequestType.POST,
      params,
    });
  };
}
