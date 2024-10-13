export interface PageResult {
  /**
   * 当前页数
   */
  current: number;

  /**
   * 每页数量
   */
  size: number;

  /**
   * 总条数
   */
  total: number;
}
