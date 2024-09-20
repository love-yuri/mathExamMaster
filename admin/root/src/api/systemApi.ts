/* eslint-disable prettier/prettier */
/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2024-09-11 23:41:43
 * @Description:
 */
import { BaseApi } from '#/common/base/baseApi/baseApi';
import { type BaseEntity, RequestType } from '#/common/base/baseApi/types';
import { requestClient } from '#/common/base/baseApi/request';
import type { UploadFileParam } from 'node_modules/@vben/request/src/request-client/modules/uploader';

export interface GenerateParams {
  dataBaseName: string;
  tableName: string;
}

class Api extends BaseApi<BaseEntity> {
  override baseUrl: string = '/system';

  /**
   * 生成代码
   */
  generate = (params: GenerateParams) => this.add(RequestType.POST, '/generate', params);

  /**
   * 上传文件
   */
  upload = (params: UploadFileParam) => {
    return requestClient.upload(`${this.baseUrl  }/upload`, params);
  }
}

export const systemApi = new Api();
