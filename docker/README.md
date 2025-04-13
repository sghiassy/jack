# README

Following series at: https://www.youtube.com/watch?v=ZmL46xVdYzM


jack.yt

## Access Server

`ssh root@168.231.65.34`

## Server Setup

### Install Docker

When setting up a new VPS, you need to install Docker. Just follow the instructions at:  https://docs.docker.com/engine/install/ubuntu/

### Copy over Caddyfile

`scp -i ~/.ssh/shaheen_ed25519 Caddyfile root@jack.ghiassy.com:/root`

## Dev Setup

Create a Docker Context
`docker context create hostinger --docker "host=ssh://root@168.231.65.34"`

Now you can switch context

`docker context use hostinger`

So now `docker ps` responds with the info from the Server!

## Pushing a new Docker image

### Build a new image
`docker context use default`
`docker build --platform linux/amd64 -t sghiassy/jack .`

### Push to Dockerhub

For now I'm just using Docker Mac Desktop app

## Deploying to the server

`docker context use hostinger`
`docker stack deploy -c docker-compose.yml myapp`

## Docker Commands

List containers: `docker stack ls`
Service info: `docker stack services myapp`
Logs: `docker service ps myapp_web --no-trunc`
Logs: `docker service logs myapp_web`
