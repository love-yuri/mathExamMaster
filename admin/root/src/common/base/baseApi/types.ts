/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-09 14:18:19
 * @LastEditTime: 2024-09-21 13:45:24
 * @Description: baseApi types
 */

/**
 * @description: 请求类型
 */
export enum RequestType {
  GET,
  POST,
  UPLOAD, // 上传
}

/**
 * @description: 请求配置
 */
export type RequestConfig = {
  method: RequestType;
  params?: any;
  url: string;
};

export interface BaseEntity {
  createTime?: string;
  id: string;
  updateTime?: string;
}
