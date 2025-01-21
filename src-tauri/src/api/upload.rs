use std::fs::File;
use std::io::Read;
use tauri_plugin_http::reqwest;
use tauri_plugin_http::reqwest::multipart;
use log::info;
use crate::api::base::R;

#[derive(serde::Serialize, serde::Deserialize, Debug)]
pub struct SystemFile {
  filename: String, // 文件名
  md5: String,      // 文件 MD5 值
  path: String,     // 路径
  pub(crate) id: String,      // 文件 ID
}

pub async fn upload_file(mut file: File, file_name: &str, token: &str, base_url: &str) -> Result<R<SystemFile>, String> {
  // 读取文件内容
  let mut file_data = Vec::new();
  file.read_to_end(&mut file_data).map_err(|e| e.to_string())?;
  
  // 创建 multipart 表单
  let part = multipart::Part::bytes(file_data)
    .file_name(file_name.to_string())
    .mime_str("application/octet-stream")
    .map_err(|e| e.to_string())?;
  
  let form = multipart::Form::new().part("file", part);
  
  // 发送 POST 请求
  let client = reqwest::Client::new();
  let url = if base_url == "/api" {
      "http://localhost:2078/system/upload".to_string()
  } else {
      format!("{}/system/upload", base_url)
  };

  let response = client
    .post(&url) // 替换为你的后端地址
    .multipart(form)
    .header("Authorization", token)
    .send()
    .await
    .map_err(|e| e.to_string())?;
  
  // 检查响应状态
  if response.status().is_success() {
    let response_text = response.text().await.map_err(|e| e.to_string())?;
    let file: R<SystemFile> = serde_json::from_str(&response_text).map_err(|e| e.to_string())?;
    Ok(file)
  } else {
    let msg = format!("Upload failed with status: {}", response.status());
    info!("发送失败!! {msg}");
    Err(msg)
  }
}