docker build --no-cache -t was_main .

CONTAINER_NAME="was_main"

# 컨테이너가 실행 중이면 정지 후 삭제
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
  echo "Container $CONTAINER_NAME is running. Stopping and removing..."
  docker stop $CONTAINER_NAME
  docker rm $CONTAINER_NAME
fi

# 컨테이너가 이미 존재하면 정지 후 삭제
if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
  echo "Container $CONTAINER_NAME exists. Removing..."
  docker rm $CONTAINER_NAME
fi

docker run --env-file /root/.env -d -p 8082:8082 "SPRING_PROFILES_ACTIVE=proc" --name was_main was_main