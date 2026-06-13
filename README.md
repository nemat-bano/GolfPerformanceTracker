# Golf Performance Tracker

Golf Performance Tracker is an Android application that allows users to browse golf players, view player performance metrics, and analyze shot data.

The project demonstrates modern Android development practices through two separate UI implementations:

XML Variant – Traditional Android UI built with Fragments, RecyclerView, Data Binding, Custom View and MotionLayout
Compose Variant – Modern Android UI built with Jetpack Compose

The application showcases Kotlin (Coroutines + Flow), MVVM, Clean Architecture, modularisation, dependency injection(Hilt), networking(Retrofit), data parsing(Moshi) local persistence(Room), offline-first architecture, background synchronisation, Paging 3, logging(Timber), and testing(Turbine, unit tesing using mockk).

## Features
  Player List
  Search players by name or club
  Expand and collapse player cards
  Navigate to player details
  Player Details
  View player profile information
  View player image
  View average speed and distance
  View shot history
  UI supports dark and light themes

## Build Variants

The application provides two build variants that demonstrate different Android UI approaches.

### Compose Variant
  Build Variants
    composeDebug
    composeRelease
  #### Technologies
    Jetpack Compose
    Material 3
    Navigation Compose
    Glide Compose
    StateFlow
    Retrofit
    Moshi
    Paging 3
  #### Architecture
    MVVM
  Things to Note
    Uses remote data directly
    No local persistence
    No offline-first implementation
  #### Paging 3 Support
    The Compose implementation uses Paging 3 to efficiently load and display large player datasets from the remote API.
  #### Architecture:
    LazyColumn -> collectAsLazyPagingItems() -> ViewModel -> Pager -> PlayerPagingSource -> Retrofit + Moshi -> MockAPI
  #### Key Components:
    * Paging 3
    * PagingSource
    * Pager
    * LazyPagingItems
    * Retrofit
  #### Benefits:
    * Efficient memory usage
    * Incremental data loading
    * Built-in loading and error states
    * Scalable architecture for larger datasets

The Compose variant uses a network-backed PagingSource and does not rely on local persistence.

### XML Variant
  Build Variants
    xmlDebug
    xmlRelease
  #### Technologies
    Fragments
    RecyclerView
    Data Binding
    MotionLayout
    Room Database
    WorkManager
    Retrofit
    Moshi
  #### Architecture
    MVVM with offile first approach
  Things to Note
    Implements offline-first architecture
    Room acts as the single source of truth
    Supports automatic synchronization when connectivity is restored

## Modularization
  The project follows Clean Architecture and is organized into four Gradle modules.

  ### app: Application entry point and configuration.
      Responsibilities:
        Application setup
        Hilt initialization
        WorkManager configuration
        Build variants
        Dependency wiring
      Depends on:
        ui
        data
        domain

  ### domain: Contains the business layer. The domain module contains no Android framework dependencies.
      Responsibilities:
        Domain models
        Repository interfaces
        Business rules
      Depends on: 
        None

  ### data: Responsible for data retrieval, persistence, synchronization, and repository implementations.
      Responsibilities:
        Retrofit API definitions
        Moshi DTOs
        Room Database
        Room Entities
        Room DAOs
        Repository implementations
        DTO ↔ Entity ↔ Domain mapping
        WorkManager workers
        Offline-first synchronization
      Depends on:
        domain

  ### ui: Responsible for presentation and user interaction.
      Contains:  
        Compose screens,  
        Compose navigation,  
        Fragments,  
        RecyclerView,  
        Data Binding,  
        MotionLayout,  
        ViewModels,  
        UI State,  
        Navigation,  
        Pagination,  

      Depends on:
        domain

## Offline-First Architecture (XML Variant)

  The XML implementation follows an offline-first architecture.

  Room Database acts as the Single Source of Truth.

  Network -> Repository -> Room Database -> Flow -> ViewModel -> UI
         
  Benefits
    Previously fetched data remains available offline.
    UI automatically updates when Room data changes.
    Cached data remains available when network requests fail.
    Data is synchronized automatically when connectivity is restored.

## Background Synchronization

  The XML variant uses WorkManager to synchronize data when network connectivity becomes available.

  No Internet -> Cached Room Data -> Internet Restored -> WorkManager -> Repository Sync -> Room Update -> Automatic UI Refresh

## Technology Stack
  ### Core
    Kotlin
    Clean Architecture
    MVVM
    Modularization
    Coroutines
    Flow
    RecyclerView
    Data Binding
    MotionLayout
    Repository Pattern and Single Source of Truth
    Jetpack Compose
  ### Dependency Injection
    Hilt
  ### Networking
    Retrofit
    Moshi
  ### Local Storage
    Room Database
  ### Background Processing
    WorkManager
  ### Image Loading
    Glide
    Glide Compose
  ### Pagination
    Paging 3 with Jetpack Compose
  ### Logging
    Timber
  ### Testing
    JUnit
    Turbine
  
## API Endpoints
  Data is retrieved from MockAPI.io.
  
  ### Players  
    GET /Players  
  ### Player Details
    GET /Players/{id}
  ### Shots
    GET /Shots?playerId={id}

## Setup Instructions
  ### Prerequisites
      Android Studio Narwhal or newer
      JDK 17
      Android SDK 35
      Gradle 8+
  ### Clone Repository
      git clone https://github.com/nemat-bano/GolfPerformanceTracker.git
      cd GolfPerformanceTracker
  ### Open Project
      Open Android Studio
      Select Open
      Choose the project root directory
      Allow Gradle synchronization to complete

  ### Selecting a Build Variant
  #### Using Android Studio
        Open Android Studio
        Navigate to:
        View → Tool Windows → Build Variants
        Select one of the following variants for the app module:
        composeDebug or xmlDebug
        Click Run
  #### Using Command Line
        Build Compose Variant
          ./gradlew assembleComposeDebug
        Build XML Variant
          ./gradlew assembleXmlDebug
        Install Compose Variant
          ./gradlew installComposeDebug
        Install XML Variant
          ./gradlew installXmlDebug
  ### Run Unit Tests
        ./gradlew test
  ### Run Instrumentation Tests
        ./gradlew connectedAndroidTest

## Testing
    The project includes:
      Unit Tests
        PlayerMapperTest
        ShotMapperTest
      Flow Tests
        Turbine-based Flow testing

## Logging
    The application uses Timber for structured logging.

## Future Improvements
    Paging 3 support
  


   
    
