# README

Following series at: https://www.youtube.com/watch?v=ZmL46xVdYzM

`jack.ghiassy.com`

## Access Server

`ssh root@72.61.65.125`

## Server Setup

### Install Docker

When setting up a new VPS, you need to install Docker. Just follow the instructions at:  https://docs.docker.com/engine/install/ubuntu/

### Copy over Caddyfile

`scp -i ~/.ssh/shaheen_ed25519 Caddyfile root@jack.ghiassy.com:/root`

## Dev Setup

Create a Docker Context
`docker context create hostinger --docker "host=ssh://root@72.61.65.125"`

Now you can switch context

`docker context use hostinger`

So now `docker ps` responds with the info from the Server!

## Pushing a new Docker image

### Build a new image

```bash
yarn docker:build:image
```

### Push to Dockerhub

Run
```bash
yarn docker:push
```

## Deploying to the server

```bash
yarn docker:deploy
```

## Docker Commands

List containers: `docker stack ls`
Service info: `docker stack services myapp`
Logs: `docker service ps myapp_web --no-trunc`
Logs: `docker service logs myapp_web`



From this YouTube Series: https://youtu.be/xMCnDesBggM?si=sP5deZgoDfuYYeyl

### Review Query

```graphql
query ReviewQuery($id:ID!) {
  review(id:$id) {
    rating
    game {
      title
      platform
      reviews {
        rating
      }
    }
    author {
      name,
      verified
    }
  }
}
```
Input
```json
{
  "id": "1"
}
```

### Delete Mutation

```graphql
mutation DeleteMutation($id: ID!) {
  deleteGame(id: $id) {
    id
    title
    platform
  }
}
```
Input
```json
{
  "id": "4"
}
```
### Add Mutation
```graphql
mutation AddMutation($game: AddGameInput!) {
  addGame(game: $game) {
    id
    title
    platform
  }
}
```
Input
```json
{
  "game": {
    "title": "a new game",
    "platform": ["switch", "ps5"]
  }
}
```
### Games Query
```graphql
query GamesQuery {
  games {
    id
    title
  }
}
```

```bash
http POST http://localhost:4000/graphql \
  query='query GamesQuery { games { id title } }'
```
### Update Game

```graphql
mutation UpdateGame($id: ID!, $edits: EditGameInput!) {
  updateGame(id: $id, edits: $edits) {
    id,
    title,
    platform
  }
}
```
Input
```json
{
  "id": "5",
  "edits": {
    "title": "updated title"
  }
}
```
