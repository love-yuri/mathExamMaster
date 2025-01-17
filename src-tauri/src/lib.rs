mod parse_html;

use parse_html::parse_html;

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
  tauri::Builder::default()
    .setup(|app| {
      if cfg!(debug_assertions) {
        app.handle().plugin(
          tauri_plugin_log::Builder::default()
            .level(log::LevelFilter::Info)
            .build(),
        )?;
      }
      Ok(())
    })
    .invoke_handler(tauri::generate_handler![greet, parse_html])
    .run(tauri::generate_context!())
    .expect("error while running tauri application");
}
// 定义一个简单的命令
#[tauri::command]
fn greet(name: &str) -> String {
  let name = String::from("yuri is yes");
  format!("Hello, {}!", name)
}