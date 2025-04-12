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
      return games;
    },
    game(parent, args, context) {
      return games.find(game => game.id === args.id)
    },
    reviews() {
      return reviews;
    },
    review(parent, args, context) {
      return reviews.find((review) => review.id === args.id);
    },
    authors() {
      return authors;
    },
    author(parent, args, context) {
      return authors.find(author => author.id === args.id)
    },
  },
};

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
