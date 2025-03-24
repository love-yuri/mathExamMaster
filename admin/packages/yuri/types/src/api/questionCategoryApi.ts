import { BaseEntity } from './base';

export class QuestionCategory extends BaseEntity {
  /**
   * 分类描述
   */
  description!: string;

  /**
   * 分类名称
   */
  name!: string;

  /**
   * 手动调用reset
   */
  constructor() {
    super();
    this.reset();
  }

  override reset(): void {
    this.name = '';
    this.description = '';
  }
}
