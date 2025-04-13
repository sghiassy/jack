export const typeDefs = `#graphql

  type Game {
    id: ID!
    title: String!
    platform: [String!]!
    reviews: [Review!]
  }

  type Review {
    id: ID!
    rating: Int!
    content: String!
    game: Game!
    author: Author!
  }

  type Author {
    id: ID!
    name: String!
    verified: Boolean!
    reviews: [Review!]
  }

  type Counter {
    value: Int!
  }

  type Query {
    reviews: [Review]
    review(id: ID!): Review
    games: [Game]
    game(id: ID!): Game
    authors: [Author ]
    author(id: ID!): Author
    counter: Counter
  }

  type Mutation {
    deleteGame(id: ID!): [Game]
    addGame(game: AddGameInput!): Game
    updateGame(id: ID!, edits: EditGameInput): Game

    incrementCounter(by:Int):Counter
    decrementCounter(by:Int):Counter
    changeCounter(newValue:Int!):Counter
  }

  input AddGameInput {
    title: String!
    platform: [String!]!
  }

  input EditGameInput {
    title: String
    platform: [String!]
  }

`;
