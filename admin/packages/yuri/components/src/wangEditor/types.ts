/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-08 19:49:21
 * @LastEditTime: 2025-02-27 18:54:26
 * @Description:
 */
export interface WangEditorProps {
  /**
   * 默认提示内容
   */
  placeholder?: string;

  /**
   * 是否只读
   */
  readonly?: boolean;

  /**
   * 是否显示工具栏
   */
  showToolbarConfig?: boolean;
}
