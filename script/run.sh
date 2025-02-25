#!/bin/bash
###
 # @Author: love-yuri yuri2078170658@gmail.com
 # @Date: 2025-02-25 10:51:51
 # @LastEditTime: 2025-02-25 11:07:27
 # @Description: 
### 

# 查找运行中的 math-0.0.1-SNAPSHOT.jar 进程
echo "正在启动服务..."

pid=$(pgrep -f "math.*\.jar")

# 如果进程存在，则杀死它
if [ -n "$pid" ]; then
    echo "服务正在运行: $pid, 正在杀死进行"
    kill -9 $pid
    sleep 1  # 等待进程完全退出
fi

# 进入指定目录
cd /home/yuri/math || exit 1

# 启动新的服务并重定向日志
nohup java -jar $(ls math*.jar | head -n 1) > math.log 2>&1 &
echo "服务成功启动....."