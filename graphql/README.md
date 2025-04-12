# Backend Implementation

## Basket of Technologies

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
