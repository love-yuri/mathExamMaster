/* eslint-disable prettier/prettier */
/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2024-09-23 23:05:34
 * @Description:
 */
import { BaseApi } from '#/common/base/baseApi/baseApi';
import { requestClient } from '#/common/base/baseApi/request';
import { type BaseEntity } from '#/common/base/baseApi/types';
import { useAppConfig } from '@vben/hooks';
import type { UploadFileParam } from '@vben/request';

const { apiURL } = useAppConfig(import.meta.env, import.meta.env.PROD);

export interface SystemFile extends BaseEntity {
  filename: string;
  md5: string;
  path: string;
  type: string;
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
