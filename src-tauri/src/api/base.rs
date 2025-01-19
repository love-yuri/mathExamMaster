use serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize)]
#[allow(non_snake_case)]
pub struct R<T> {
  code: i32,        // 状态码
  message: String,  // 消息
  pub data: Option<T>,  // 数据（可为空）
  isSuccess: bool, // 是否成功
}
