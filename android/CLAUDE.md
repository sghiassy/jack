# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

JACK is an Android application built with modern Android development practices. It's a counter application that demonstrates clean architecture patterns with GraphQL integration, dependency injection, and Jetpack Compose UI.

**Tech Stack:**
- Kotlin
- Jetpack Compose for UI
- Hilt for dependency injection
- Apollo GraphQL client (v4.1.1)
- Coroutines & Flow for reactive programming
- Architecture: Clean Architecture with domain/data/ui layers

**Server:** GraphQL endpoint at https://jack.ghiassy.com/graphql

## Build & Development Commands

### Building
```bash
./gradlew build
./gradlew assembleDebug
./gradlew assembleRelease
```

### Testing
```bash
# Run all tests
./gradlew test

# Run unit tests
./gradlew testDebugUnitTest

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest

# Run specific test class
./gradlew test --tests "com.shaheenghiassy.jack.ExampleUnitTest"
```

### GraphQL Schema Management
```bash
# Download latest schema from server introspection
./gradlew :app:downloadServiceApolloSchemaFromIntrospection

# Generate GraphQL models after schema changes
./gradlew app:generateApolloSources
```

**Note:** After modifying `.graphql` files or updating the schema, you must run `generateApolloSources` to regenerate the type-safe models.

### Linting & Code Quality
```bash
./gradlew lint
./gradlew lintDebug
```

### Clean Build
```bash
./gradlew clean
```

## Architecture

### Layer Structure

The codebase follows clean architecture with clear separation of concerns:

**Domain Layer** (`domain/`):
- `repository/`: Repository interfaces (contracts)
- Pure Kotlin with no Android dependencies
- Defines business contracts

**Data Layer** (`data/`):
- `repositories/`: Repository implementations
- `datasources/`: Data source implementations (Disk, API)
- `models/`: Data models
- Handles data persistence and API communication

**UI Layer** (`ui/`):
- `mainview/`: Main screen with ViewModel and UI state
- `components/`: Reusable Compose components
- `theme/`: Theme definitions (Color, Type, Theme)
- ViewModels expose StateFlow for reactive UI updates

**Dependency Injection** (`di/`):
- `AppModule`: Singleton-scoped dependencies (repositories, receivers)
- `ActivityModule`: Activity-scoped dependencies
- `ViewModelModule`: ViewModel dependencies
- Uses Hilt with `@HiltAndroidApp` on `JACKApplication`

### Key Patterns

**Repository Pattern:**
- Domain defines interfaces (`CounterRepository`)
- Data layer provides implementations (`CounterRepositoryImpl`)
- Currently uses `DiskDatasourceImpl` for local persistence
- `APIDatasourceImpl` exists but is commented out (see app/src/main/java/com/shaheenghiassy/jack/data/repositories/CounterRepositoryImpl.kt:14)

**Reactive Data Flow:**
- Repository exposes `Flow<CounterModel>` via `myFlow`
- ViewModel converts to `StateFlow<MainViewUIState>` using `stateIn`
- UI observes state changes via `collectAsState()`
- All async operations use coroutines with appropriate dispatchers (IO for disk operations)

**UI State Management:**
- Sealed interface pattern: `MainViewUIState.Loading` | `MainViewUIState.Loaded`
- ViewModels are Hilt-injected with `@HiltViewModel`
- State updates flow through: User Action → ViewModel → Repository → Flow → UI

### GraphQL Integration

GraphQL queries/mutations are in `app/src/main/graphql/`:
- `CounterQuery.graphql`
- `IncrementCounterMutation.graphql`
- `DecrementCounterMutation.graphql`
- `ChangeCounterMutation.graphql`
- `GamesQuery.graphql`

Apollo generates type-safe Kotlin code under package `com.ghiassy.jack`.

## Project Configuration

- **Package:** `com.shaheenghiassy.jack`
- **Min SDK:** 24
- **Target SDK:** 35
- **Compile SDK:** 35
- **Java Version:** 11

## Testing Notes

Test files are located at:
- Unit tests: `app/src/test/java/`
- Instrumented tests: `app/src/androidTest/java/`

When writing tests, use the existing test infrastructure with JUnit 4 and AndroidX test libraries.