###
 # @Author: love-yuri yuri2078170658@gmail.com
 # @Date: 2024-10-25 18:17:45
 # @LastEditTime: 2024-10-25 19:17:59
 # @Description: scp 命令
### 

# nohup java -jar math-0.0.1-SNAPSHOT.jar > math.log 2>&1 &
scp ./kotlin/target/math-0.0.1-SNAPSHOT.jar yuri@47.98.104.121:/home/yuri/math/
scp -r ./admin/root/dist yuri@47.98.104.121:/home/yuri/math/admin


location /api/ {
  proxy_pass http://127.0.0.1:2078/;
  proxy_set_header Host $host;
  proxy_set_header X-Real-IP $remote_addr;
  proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
  proxy_set_header X-Forwarded-Proto $scheme;

  # CORS headers
  add_header Access-Control-Allow-Origin *;
  add_header Access-Control-Allow-Methods 'GET, POST, OPTIONS';
  add_header Access-Control-Allow-Headers 'Content-Type, Authorization';
}
