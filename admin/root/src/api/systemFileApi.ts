/* eslint-disable prettier/prettier */
/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2024-10-03 21:48:28
 * @Description:
 */
import { BaseApi } from '#/common/base/baseApi/baseApi';
import { requestClient } from '#/common/base/baseApi/request';
import { BaseEntity } from '#/common/base/baseApi/types';
import { useAppConfig } from '@vben/hooks';
import type { UploadFileParam } from '@vben/request';

const { apiURL } = useAppConfig(import.meta.env, import.meta.env.PROD);

export class SystemFile extends BaseEntity {
  filename!: string;
  md5!: string;
  path!: string;
  type!: string;

  constructor() {
    super();
    this.reset();
  }

  override reset() {
    this.filename = '';
    this.md5 = '';
    this.path = '';
    this.type = '';
  }
}

class Api extends BaseApi<SystemFile> {
  override baseUrl: string = '/system/file';

  /**
   * 获取文件访问链接
   * @param fileId 文件id
   */
  getFile = (fileId: string) => `${apiURL}${this.baseUrl}/get/${fileId}`;

  /**
   * wmf文件转jpg
   */
  wmfToJpg = (params: UploadFileParam): Promise<SystemFile> => {
    return requestClient.upload(`${this.baseUrl  }/wmf/to/jpg`, params);
  }

}

export const systemFileApi = new Api();
