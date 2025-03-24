import type { QuestionCategory } from '@yuri/types';

import { BaseApi } from '..';

class Api extends BaseApi<QuestionCategory> {
  override baseUrl: string = '/question/category';
}
export const questionCategoryApi = new Api();
