/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 17:05:12
 * @LastEditTime: 2025-03-08 19:31:23
 * @Description:
 */

import type { ScoreDetailResult, UserScore } from '@yuri/types';

import { RequestType } from '@yuri/types';

import { BaseApi } from '..';

class Api extends BaseApi<UserScore> {
  override baseUrl: string = '/user/score';

  detail = (id: string) => {
    return this.add<UserScore>(RequestType.POST, `detail`, id);
  };

  /**
   * 阅卷结束
   * @param id relation id
   */
  reviewingCompleted = (id: string) => {
    return this.add<boolean>(RequestType.POST, `reviewing/completed`, id);
  };

  /**
   * 获取该发布的得分详情
   * @param id release id 发布id
   */
  scoreDetail = (id: string) => {
    return this.add<ScoreDetailResult>(RequestType.POST, `score/detail`, id);
  };
}

export const userScoreApi = new Api();
