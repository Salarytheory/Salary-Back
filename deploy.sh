docker build --no-cache -t was .

CONTAINER_NAME="was"

if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
  docker stop $CONTAINER_NAME
  docker rm $CONTAINER_NAME
fi

docker run --env-file /root/.env -d -p 8080:8080 --name was was