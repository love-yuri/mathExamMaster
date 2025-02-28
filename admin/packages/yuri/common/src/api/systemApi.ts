import type { BaseEntity, GenerateParams } from '@yuri/types';

/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2025-02-28 10:09:46
 * @Description:
 */
import { RequestType } from '@yuri/types';

import { BaseApi } from '../base/baseApi/baseApi';
import { requestClient } from '../base/baseApi/request';

class Api extends BaseApi<BaseEntity> {
  override baseUrl: string = '/system';

  /**
   * 数据库列表
   */
  databases = () => this.add<string[]>(RequestType.GET, '/databases');

  /**
   * 生成代码
   */
  generate = (params: GenerateParams) => {
    return this.add(RequestType.POST, '/generate', params);
  };

  /**
   * 根据数据库获取表
   * @param dbName 数据库名
   */
  tables = (dbName: string) => {
    return this.add<string[]>(RequestType.GET, '/tables', dbName);
  };

  /**
   * 上传文件
   * @params params 上传文件参数
   */
  upload = (params: Record<string, any> & { file: Blob | File }) => {
    return requestClient.upload(`${this.baseUrl}/upload`, params);
  };
}

export const systemApi = new Api();
