# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

JACK (Just a Counter, K?) is a GraphQL-based server application built with Apollo Server and TypeScript. The server provides a game review platform with CRUD operations for games, authors, and reviews, plus a simple counter feature. The application is containerized with Docker and deployed to a VPS using Docker Swarm with Caddy as a reverse proxy.

## Architecture

### Server Stack
- **Runtime**: Node.js 22
- **Framework**: Apollo Server 4.x (GraphQL)
- **Language**: TypeScript (ES2020, ESNext modules)
- **Port**: 3000 (internal), proxied through Caddy

### Data Model
The application uses in-memory data storage (no database) with three main entities:
- **Games**: Platform-based games with titles and platform arrays
- **Authors**: Review authors with verification status
- **Reviews**: Ratings and content linking games and authors
- **Counter**: Simple integer counter for demonstration purposes

Located in `src/_db.ts`, this mock database is reset on server restart.

### GraphQL Schema
Defined in `src/schema.ts` with:
- Queries for fetching games, reviews, authors, and counter state
- Mutations for adding/updating/deleting games and incrementing/decrementing counter
- Nested resolvers for relationships (Game.reviews, Review.author, etc.)

### Main Server Logic
`src/index.ts` contains:
- Apollo Server initialization
- All GraphQL resolvers (Query, Mutation, and type resolvers)
- Server startup on port 3000

## Development Workflow

### Local Development
```bash
# Compile TypeScript
npm run compile

# Build and start server locally
npm start
```

The TypeScript compiler outputs to `dist/` directory, configured in `tsconfig.json`.

### Docker Operations

**Context Management**:
```bash
# Switch to local Docker
npm run docker:context:default

# Switch to remote server (Hostinger VPS)
npm run docker:context:hostinger
```

The remote Docker context connects via SSH to `root@72.61.65.125` (jack.ghiassy.com).

**Building & Publishing**:
```bash
# Build Docker image for linux/amd64
npm run docker:build:image

# Push to Docker Hub (sghiassy/jack:latest)
npm run docker:push
```

**Deployment**:
```bash
# Deploy to VPS using Docker Swarm
npm run docker:deploy
```

This deploys the `docker-compose.yml` stack named `myapp` which includes:
- `web` service: The Node.js GraphQL server
- `caddy` service: Reverse proxy handling HTTPS for jack.ghiassy.com

### Deployment Architecture

The application runs on a VPS at `72.61.65.125` (SSH: `root@72.61.65.125`):
- Docker Swarm orchestration
- Caddy handles SSL/TLS and reverse proxies to the web service
- Caddyfile must be manually copied to `/root/Caddyfile` on the server
- Uses overlay network for service communication

**Monitoring Commands**:
```bash
# List stacks
docker stack ls

# View service status
docker stack services myapp

# View logs
docker service logs myapp_web
docker service ps myapp_web --no-trunc
```

## GraphQL Examples

The README.md contains several example queries and mutations for testing. Key operations:

**Fetch games**:
```graphql
query GamesQuery {
  games { id title }
}
```

**Add a game**:
```graphql
mutation AddMutation($game: AddGameInput!) {
  addGame(game: $game) { id title platform }
}
```

**Update counter**:
```graphql
mutation { incrementCounter(by: 5) { value } }
```

## Important Notes

- **No persistence**: All data is in-memory and resets on server restart
- **Package manager**: Use `npm` (not `yarn`), despite some README examples using `yarn`
- **Platform**: Docker images must be built for `linux/amd64` architecture
- **Remote context**: The Docker context named `hostinger` enables remote Docker commands as if running on the VPS
- **Caddy setup**: The Caddyfile in this directory must be manually copied to the server's `/root/` directory before first deployment
