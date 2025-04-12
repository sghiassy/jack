import { ApolloServer } from "@apollo/server";
import { startStandaloneServer } from "@apollo/server/standalone";

const PORT = 4000;

//  db
import { games, authors, reviews } from "./_db.js";

// types
import { typeDefs } from "./schema.js";

const resolvers = {
  Query: {
    games() {
      return games
    },
    reviews() {
      return reviews
    },
    authors() {
      return authors
    }
  }
}

// server setup
const server = new ApolloServer({
  typeDefs,
  resolvers,
});

const { url } = await startStandaloneServer(server, {
  listen: {
    port: PORT
}});

console.log('Server ready at post', PORT)
