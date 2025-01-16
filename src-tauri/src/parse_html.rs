use regex::Regex;

#[tauri::command]
fn parse_html(str: &str) -> String {
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

    let index = html.find(img).unwrap();
    let mut flag = img.len();
    if width != "N/A" && height != "N/A" {
      let style = format!(r#" style="height: {}px; width: {}px;" "#, height, width);
      flag += style.len();
      html.insert_str(index + 4, &style);
    }

    if src.starts_with("file://") {
      let new_src = "/api/system/get/112222122";
      html = html.replace(src, new_src);
      flag = flag - (src.len() - new_src.len());
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
  html
}
