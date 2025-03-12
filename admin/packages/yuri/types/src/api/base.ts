/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-09 14:18:19
 * @LastEditTime: 2025-03-12 18:57:05
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
  timeout?: number; // 请求超时时间
  url: string;
};

/**
 * @description: 基础实体
 * 需要重写reset方法
 */
export abstract class BaseEntity {
  createTime?: string;
  id?: string;
  updateTime?: string;

  constructor() {
    this.reset();
  }

  /**
   * @description: 拷贝另外一个类的属性
   * @param {this} other 另一个属性
   * 该属性仅仅在数据是自行new的情况下才能使用
   * 如果数据是从服务器获取的，则不能使用该方法
   */
  public copy(other: this): void {
    const keys = Object.keys(this) as Array<keyof this>;

    keys.forEach((key) => {
      this[key] = other[key]!; // 复制属性
    });
  }

  abstract reset(): void;
}

/**
 * @description: 响应类型
 */
export type R = {
  code: number;
  data: any;
  isSuccess: boolean;
  message: string;
};

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
