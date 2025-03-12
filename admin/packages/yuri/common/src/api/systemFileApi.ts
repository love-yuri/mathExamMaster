/* eslint-disable prettier/prettier */
/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-11 23:39:11
 * @LastEditTime: 2025-03-12 18:51:17
 * @Description:
 */


import type { SystemFile} from '@yuri/types';

import { useAppConfig } from '@vben/hooks';

import { BaseApi } from '../base/baseApi/baseApi';

const { apiURL } = useAppConfig(import.meta.env, import.meta.env.PROD);


class Api extends BaseApi<SystemFile> {
  override baseUrl: string = '/system/file';

  /**
   * 获取文件访问链接
   * @param fileId 文件id
   */
  getFile = (fileId: string) => `${apiURL}${this.baseUrl}/get/${fileId}`; 
}

export const systemFileApi = new Api();
