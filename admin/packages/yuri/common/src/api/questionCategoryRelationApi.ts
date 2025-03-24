import type { QuestionCategoryRelation } from '@yuri/types';

import { BaseApi } from '..';

class Api extends BaseApi<QuestionCategoryRelation> {
  override baseUrl: string = '/question/category/relation';
}
export const questionCategoryRelationApi = new Api();
