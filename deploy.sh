docker build -t salary .

docker stop salary

docker rm salary

docker run --env-file /root/.env -d -p 8080:8080 --name salary salary