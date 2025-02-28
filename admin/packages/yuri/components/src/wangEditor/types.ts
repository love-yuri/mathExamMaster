/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-08 19:49:21
 * @LastEditTime: 2025-02-28 15:02:12
 * @Description:
 */
export interface WangEditorProps {
  /**
   * 是否隐藏工具栏
   */
  hideToolbarConfig?: boolean;

  /**
   * 默认提示内容
   */
  placeholder?: string;

  /**
   * 是否只读
   */
  readonly?: boolean;
}
