import { BaseEntity } from './base';

export class QuestionCategoryRelation extends BaseEntity {
  /**
   * 分类id
   */
  categoryId!: string;

  /**
   * 题目id
   */
  questionBankId!: string;

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.questionBankId = '';
    this.categoryId = '';
  }
}
