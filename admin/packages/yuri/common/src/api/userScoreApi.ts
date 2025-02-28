/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 17:05:12
 * @LastEditTime: 2025-02-24 20:29:25
 * @Description:
 */

import type { UserScore } from '@yuri/types';

import { RequestType } from '@yuri/types';

import { BaseApi } from '..';

class Api extends BaseApi<UserScore> {
  override baseUrl: string = '/user/score';

  detail = (id: string) => {
    return this.add<UserScore>(RequestType.POST, `detail`, id);
  };
}

export const userScoreApi = new Api();
