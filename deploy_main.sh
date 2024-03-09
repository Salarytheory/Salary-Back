#docker build --no-cache -t was_main .

#CONTAINER_NAME="was_main"
#
## 컨테이너가 실행 중이면 정지 후 삭제
#if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
#  echo "Container $CONTAINER_NAME is running. Stopping and removing..."
#  docker stop $CONTAINER_NAME
#  docker rm $CONTAINER_NAME
#fi
#
## 컨테이너가 이미 존재하면 정지 후 삭제
#if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
#  echo "Container $CONTAINER_NAME exists. Removing..."
#  docker rm $CONTAINER_NAME
#fi
#
#docker run --env-file /root/.env -d -p 8080:8080 -e SPRING_PROFILES_ACTIVE=proc -v /applog/main:/logs --name was_main was_main


# 현재 실행 중인 컨테이너의 포트 확인
current_port=$(docker inspect -f '{{(index (index .NetworkSettings.Ports "8080/tcp") 0).HostPort}}' was_main_1 2>/dev/null)

if [ -z "$current_port" ]; then
  current_port=$(docker inspect -f '{{(index (index .NetworkSettings.Ports "9090/tcp") 0).HostPort}}' was_main_2 2>/dev/null)
fi

echo "Container is running on port $current_port"

new_container_name=""
old_container_name=""

# 새로운 포트 계산
if [ "$current_port" == "8080" ]; then
  new_port=9090
  new_container_name=was_main_2
  old_container_name=was_main_1
else
  new_port=8080
  new_container_name=was_main_1
  old_container_name=was_main_2
fi

echo "Starting new container $new_container_name on port $new_port"

# 새로운 포트를 환경 변수 파일에 추가
echo "SERVER_PORT=$new_port" >> /root/.env

# 새로운 컨테이너 시작
docker run --env-file /root/.env -d -p $new_port:$new_port -e SPRING_PROFILES_ACTIVE=proc -v /applog/main:/logs --name $new_container_name $new_container_name

# 엔진엑스 프록시 패스 업데이트
sudo sed -i "s|proxy_pass .*;|proxy_pass http://127.0.0.1:$new_port;|" /etc/nginx/nginx.conf
sudo systemctl restart nginx

echo "Updated Nginx proxy_pass to port $new_port"

# 기존 컨테이너 중지 및 삭제 (동적으로 변경)
docker stop $old_container_name
docker rm $old_container_name

echo "Stopped and removed old container running on port $current_port"