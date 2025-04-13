# Android Implementation

This is the Android Implementation of JACK

## Basket of Technologies

The combination of these technologies are based almost entirely on what I'm using at work

- Kotlin
- Jetpack Compose
- GraphQL
  - w/Realtime updates
  - caching
  - offline first
- Dependency Injection (Ultralight)

## TODOs
- Threading
- Dependency Injection
- Theming
- Counter Functionality
- Connect to server


## GraphQL

To download new server definitions

`./gradlew :app:downloadServiceApolloSchemaFromIntrospection`

To update the GraphQL Models locally

`./gradlew app:generateApolloSources`
