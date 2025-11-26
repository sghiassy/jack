# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

JACK is an Android application built with modern Android development practices. It's a counter application that demonstrates clean architecture patterns with GraphQL integration, dependency injection, and Jetpack Compose UI.

**Tech Stack:**
- Kotlin 2.0.21
- Jetpack Compose for UI
- Hilt 2.51.1 for dependency injection
- Apollo GraphQL client (v4.1.1)
- Coroutines & Flow for reactive programming
- Architecture: Clean Architecture with domain/data/ui layers
- Build System: Gradle with Kotlin DSL (`.gradle.kts` files)

**Server:** GraphQL endpoint at https://jack.ghiassy.com/graphql

**Key Libraries:**
- Robolectric 4.13 for unit testing with Android framework
- MockK 1.13.13 for mocking
- Compose UI Test for Compose testing
- Kotlinx Coroutines Test for coroutine testing

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

## Project Structure

### Main App (`/app`)
The primary JACK counter application.

### Test App (`/test_app`)
A separate Android application used for external testing functionality. It can send broadcasts to the main JACK app to modify the counter value.

**Purpose:** Tests inter-app communication via Android Broadcasts
**Intent Action:** `CHANGE_VALUE` with an integer extra `count`

See `test_app/README.md` for more details.

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
- `components/`: Reusable Compose components (e.g., `CounterText`)
- `theme/`: Theme definitions (Color, Type, Theme)
- ViewModels expose StateFlow for reactive UI updates
- Uses `AndroidViewModel` to access Application context

**Use Case Layer** (`usecase/`):
- `DisplayLogic.kt`: Utility functions for UI presentation logic
- `stateToString()`: Converts MainViewUIState to display string

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
- Repository exposes `SharedFlow<CounterModel>` via `myFlow` (using `MutableSharedFlow`)
- ViewModel converts to `StateFlow<MainViewUIState>` using `stateIn`
- UI observes state changes via `collectAsStateWithLifecycle()`
- All async operations use coroutines with appropriate dispatchers (IO for disk operations)
- ViewModel operations run on `Dispatchers.IO` for disk writes

**UI State Management:**
- Sealed interface pattern with three states:
  - `MainViewUIState.Empty`: Initial/empty state
  - `MainViewUIState.Loading`: Data loading in progress
  - `MainViewUIState.Loaded(model: CounterModel)`: Data successfully loaded
- ViewModels are Hilt-injected with `@HiltViewModel`
- State updates flow through: User Action → ViewModel → Repository → SharedFlow → StateFlow → UI

### GraphQL Integration

GraphQL queries/mutations are in `app/src/main/graphql/`:
- `CounterQuery.graphql`
- `IncrementCounterMutation.graphql`
- `DecrementCounterMutation.graphql`
- `ChangeCounterMutation.graphql`
- `GamesQuery.graphql`

Apollo generates type-safe Kotlin code under package `com.ghiassy.jack`.

**Apollo Configuration:**
- Service name: `service`
- Introspection endpoint: `https://jack.ghiassy.com/graphql`
- Schema file: `app/src/main/graphql/schema.graphqls`

### Broadcast Receiver Pattern

The app uses Android's Broadcast system for inter-app communication:

**MyBroadcastReceiver** (`app/MyBroadcastReceiver.kt`):
- Receives `CHANGE_VALUE` broadcast intents
- Accepts an integer extra named `count`
- Updates counter value via `CounterRepository.change()`
- Registered dynamically in `MainActivity.onCreate()` with `RECEIVER_EXPORTED` flag
- Unregistered in `MainActivity.onDestroy()`
- Injected as a Singleton via Hilt `AppModule`
- Runs operations on `CoroutineScope(Dispatchers.IO)`

**Usage Example:**
```bash
adb shell am broadcast -a CHANGE_VALUE --ei count 5
```

This pattern allows external apps (like the `test_app`) to modify the counter value.

### Data Persistence

**DiskDatasourceImpl** (`data/datasources/DiskDatasourceImpl.kt`):
- Stores counter value in a simple text file: `counter.txt`
- Located in app's internal storage (`context.filesDir`)
- Initializes file with `"0"` if it doesn't exist
- Uses `Dispatchers.IO` for all file operations
- Methods: `readCounter()`, `writeCounter(value: Int)`

**CounterModel Default Value:** 420 (see `data/models/CounterModel.kt:3`)

### Android Components

**MainActivity** (`app/MainActivity.kt`):
- Annotated with `@AndroidEntryPoint` for Hilt injection
- Requires API 33+ (`@RequiresApi(Build.VERSION_CODES.TIRAMISU)`)
- Enables edge-to-edge display
- Dynamically registers/unregisters `MyBroadcastReceiver`
- Uses `Scaffold` with `JACKTheme`

**JACKApplication** (`app/JACKApplication.kt`):
- Annotated with `@HiltAndroidApp` to initialize Hilt

**Network Security:**
- Custom `network_security_config.xml` configured
- Trusts user-added certificates in debug builds
- Disables cleartext traffic in production (`cleartextTrafficPermitted="false"`)

## Project Configuration

- **Package:** `com.shaheenghiassy.jack`
- **Application ID:** `com.shaheenghiassy.jack`
- **Min SDK:** 24
- **Target SDK:** 35
- **Compile SDK:** 35
- **Java Version:** 11
- **Version Code:** 1
- **Version Name:** 1.0
- **Build Files:** Kotlin DSL (`.gradle.kts` files, not Groovy `.gradle`)
- **Version Catalog:** `gradle/libs.versions.toml` for centralized dependency management

## Testing Notes

### Test Infrastructure

**Test Frameworks:**
- JUnit 4 for test structure
- Robolectric 4.13 for unit tests requiring Android framework
- MockK 1.13.13 for mocking (both JVM and Android)
- Compose UI Test (androidx.compose.ui:ui-test-junit4) for Compose UI testing
- Kotlinx Coroutines Test for testing coroutines

**Test Configuration:**
- Unit tests have `isIncludeAndroidResources = true` for accessing Android resources
- JVM args include `-XX:+EnableDynamicAgentLoading` to suppress MockK warnings

**Test Locations:**
- Unit tests: `app/src/test/java/`
- Instrumented tests: `app/src/androidTest/java/`

**Existing Tests:**
- `MainViewTest.kt`: Comprehensive Robolectric + Compose UI tests for MainView
  - Tests all three UI states (Empty, Loading, Loaded)
  - Tests button interactions and ViewModel calls
  - Uses `@Config(sdk = [33], manifest = Config.NONE)`
  - Uses `createComposeRule()` for Compose testing

**Testing Best Practices:**
- Use MockK with `relaxed = true` for simple mocks
- Use `composeTestRule` for Compose UI testing
- Mock ViewModels when testing Composables
- Use `onNodeWithText()` and `assertIsDisplayed()` for UI verification
- Use `verify(exactly = N)` for interaction verification

## Important File Paths

### Configuration Files
- `app/build.gradle.kts` - Main app build configuration
- `build.gradle.kts` - Project-level build configuration
- `settings.gradle.kts` - Project settings and modules
- `gradle/libs.versions.toml` - Centralized version catalog
- `app/src/main/AndroidManifest.xml` - App manifest
- `app/src/main/res/xml/network_security_config.xml` - Network security config
- `app/proguard-rules.pro` - ProGuard rules (currently default)

### Source Code
- `app/src/main/java/com/shaheenghiassy/jack/`
  - `app/JACKApplication.kt` - Application class
  - `app/MainActivity.kt` - Main activity
  - `app/MyBroadcastReceiver.kt` - Broadcast receiver for external commands
  - `di/AppModule.kt` - Hilt dependency injection setup
  - `domain/repository/CounterRepository.kt` - Repository interface
  - `domain/datasource/DiskDatasource.kt` - Datasource interface
  - `data/repositories/CounterRepositoryImpl.kt` - Repository implementation
  - `data/datasources/DiskDatasourceImpl.kt` - Disk persistence implementation
  - `data/datasources/APIDatasourceImpl.kt` - API implementation (commented out)
  - `data/models/CounterModel.kt` - Data model
  - `ui/mainview/MainView.kt` - Main Composable screen
  - `ui/mainview/MainViewModel.kt` - ViewModel
  - `ui/mainview/MainViewUIState.kt` - UI state definition
  - `ui/components/CounterText.kt` - Reusable counter text component
  - `ui/theme/` - Theme, Color, Type definitions
  - `usecase/DisplayLogic.kt` - Display utility functions

### GraphQL
- `app/src/main/graphql/schema.graphqls` - GraphQL schema
- `app/src/main/graphql/*.graphql` - Query and mutation definitions

### Tests
- `app/src/test/java/com/shaheenghiassy/jack/ui/mainview/MainViewTest.kt` - Main UI tests
- `app/src/test/java/com/shaheenghiassy/jack/ExampleUnitTest.kt` - Example test

## Common Development Tasks

### Adding a new GraphQL operation
1. Create `.graphql` file in `app/src/main/graphql/`
2. Run `./gradlew app:generateApolloSources`
3. Import generated code from `com.ghiassy.jack` package

### Modifying the counter logic
- **Business logic:** `domain/repository/CounterRepository.kt`
- **Implementation:** `data/repositories/CounterRepositoryImpl.kt`
- **Data model:** `data/models/CounterModel.kt`

### Adding a new UI component
- Create in `ui/components/`
- Follow Compose best practices
- Add tests in `app/src/test/java/`

### Debugging broadcast receiver
```bash
# Send test broadcast
adb shell am broadcast -a CHANGE_VALUE --ei count 10

# View logs
adb logcat | grep "shizz"
```