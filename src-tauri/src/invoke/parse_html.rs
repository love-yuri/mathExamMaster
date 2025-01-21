use std::fs::File;
use std::path::{Path, MAIN_SEPARATOR};
use log::info;
use regex::Regex;
use crate::api::upload::upload_file;

#[tauri::command]
pub async fn parse_html(str: &str, token: &str, base_url: &str) -> Result<String, String> {
  let mut html = String::from(str);
  
  let re = Regex::new(r#"<img\s+[^>]*src="([^"]*)"[^>]*>"#).unwrap();
  let h_reg = Regex::new(r#"height="(\d*)[^"]*""#).unwrap();
  let w_reg = Regex::new(r#"width="(\d*)[^"]*""#).unwrap();
  
  // 查找所有匹配的 <img> 标签
  for cap in re.captures_iter(&html.clone()) {
    let img = &cap[0];
    let src = &cap[1];
    
    // 提取 height 属性值
    let height = h_reg
      .captures(img)
      .and_then(|c| c.get(1))
      .map_or("N/A", |m| m.as_str());
    
    // 提取 width 属性值
    let width = w_reg
      .captures(img)
      .and_then(|c| c.get(1))
      .map_or("N/A", |m| m.as_str());
    
    // 查找index
    let index = html.find(img).unwrap();
    
    let mut flag = img.len();
    if width != "N/A" && height != "N/A" {
      let style = format!(r#" style="height: {}px; width: {}px;" "#, height, width);
      flag += style.len();
      html.insert_str(index + 4, &style);
    }
    
    if src.starts_with("file://") {
      let mut new_src = format!("{}/system/file/get/3", base_url);
      let mut img_src = src.replace("file://", "").replace("/", &MAIN_SEPARATOR.to_string());
      let os = std::env::consts::OS;
      if os == "windows" && img_src.starts_with("\\") {
        img_src = img_src[1..].to_string();
      }
      
      if let Ok(file) = File::open(&img_src) {
        let file_name = Path::new(&img_src)
          .file_name() // 获取文件名部分（如 "file.txt"）
          .and_then(|name| name.to_str()) // 转换为 &str
          .unwrap_or("temp"); // 如果提取失败，使用默认文件名
        
        let file = upload_file(file, file_name, token, base_url).await?;
        new_src = format!("{}/system/file/get/{}", base_url, file.data.unwrap().id);
      } else {
        info!("找不到文件: {}", img_src);
      }
      flag = flag - (src.len() - new_src.len());
      
      // 设置为只替换一次，防止出现图片重复但是查找不重复的问题
      html = html.replacen(src, &new_src, 1);
    }
    
    let span = html[..index].matches("<span").count();
    let span_end = html[..index].matches("</span").count();
    
    // println!("span {}, /span {}", span, span_end);
    
    let insert_count = span - span_end;
    for _ in 0..insert_count {
      html.insert_str(index, "</span>");
    }
    
    let span = html[(index + 7 * insert_count)..].matches("<span").count();
    let span_end = html[(index + 7 * insert_count)..].matches("</span").count();
    
    // println!("span {}, /span {}", span, span_end);
    
    for _ in 0..(span_end - span) {
      html.insert_str(index + flag + 7 * insert_count, "<span>");
    }
    
    // println!("src: {}, height: {}, width: {}", src, height, width);
  }
  Ok(html)
}
