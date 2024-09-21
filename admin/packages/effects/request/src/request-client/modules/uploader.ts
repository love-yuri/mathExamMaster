/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 16:04:07
 * @LastEditTime: 2024-09-21 15:03:19
 * @Description: 文件上传器材
 */
import type { AxiosRequestConfig, AxiosResponse } from 'axios';

import type { RequestClient } from '../request-client';

export interface UploadFileParam {
  file: Blob | File; // 文件
  [key: string]: any;
}

class FileUploader {
  private client: RequestClient;

  constructor(client: RequestClient) {
    this.client = client;
  }

  public async upload(
    url: string,
    file: UploadFileParam,
    config?: AxiosRequestConfig,
  ): Promise<any> {
    const formData = new FormData();

    for (const key in file) {
      formData.append(key, file[key]);
    }

    const finalConfig: AxiosRequestConfig = {
      ...config,
      headers: {
        'Content-Type': 'multipart/form-data',
        ...config?.headers,
      },
    };

    return this.client.post(url, formData, finalConfig);
  }
}

export { FileUploader };
