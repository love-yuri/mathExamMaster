/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-06 22:37:57
 * @LastEditTime: 2025-02-26 21:12:29
 * @Description: 题目
 */

import type {
  FullQuestionBank,
  PageParam,
  PageResult,
  QuestionBank,
  SaveQuestionAnswerParam,
} from '@yuri/types';

import { RequestType } from '@yuri/types';

import { BaseApi } from '../base/baseApi/baseApi';

class Api extends BaseApi<QuestionBank> {
  override baseUrl: string = '/question/bank';

  detail = (id: string) => {
    return this.add<FullQuestionBank>(RequestType.POST, `/detail/${id}`);
  };

  pageSimple = (param: PageParam) => {
    return this.add<PageResult<FullQuestionBank>>(
      RequestType.POST,
      '/page/simple',
      param,
    );
  };

  saveSimple = (param: SaveQuestionAnswerParam) => {
    return this.add<boolean>(RequestType.POST, '/save/simple', param);
  };

  updateSimple = (param: SaveQuestionAnswerParam) => {
    return this.add<boolean>(RequestType.POST, '/update/simple', param);
  };
}

export const questionBankApi = new Api();
