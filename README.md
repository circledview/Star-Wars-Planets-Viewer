![CI](https://github.com/zehtabchi/Star-Wars-Planets-Viewer/actions/workflows/android-ci.yml/badge.svg)

# 🛠️ Luzia Planets Code Challenge

A modern Android application built as a coding challenge for **Luzia**. It demonstrates clean architecture, modularization, paging, and robust state handling using Jetpack Compose.

---

## 📆 Project Modules


| Module        | Responsibilty                                             |
|--------------|--------------------------------------------------|
|feature_planets_list  |  Planet list screen + ViewModel |
| feature_planet_details    | Planet details screen + ViewModel  |
| core_domain     | Domain layer: models + use cases            |
| core_data       | Data layer: repository implementations and api calls            |
| core_ui      | Shared UI components, theming, animations |

## 🛠️ Tech Stack

| Layer         | Tech                                                                 |
|---------------|----------------------------------------------------------------------|
| UI            | Jetpack Compose, Material 3, Animation APIs, StateFlow               |
| Architecture  | MVVM + Clean Architecture (with UseCases, Repository Pattern)        |
| Pagination    | Jetpack Paging 3                                                     |
| Dependency Injection | Koin                                                          |
| Coroutines    | Kotlinx Coroutines, Flow                                             |
| Networking    | Retrofit                                                             |
| Testing       | JUnit4, MockK, Turbine, Coroutine Test                               |
| Build         | Gradle (Kotlin DSL), Modularization                                  |

---

## 🧱 Architecture Overview

The app follows **Clean Architecture** with **separation of concerns** between the following layers:

- **Presentation Layer (`feature_*`)**: Compose UI, ViewModel, and screen state
- **Domain Layer (`core_domain`)**: UseCases, Models, and business logic
- **Data Layer (`core_data`)**: Repository interfaces + fake/mock implementations
- **UI Shared (`core_ui`)**: Reusable UI components, themes, shimmer loaders, etc.

---

## 🧪 Testing Strategy

| Layer        | Tests                                             |
|--------------|--------------------------------------------------|
| ViewModel    | Planet list and detail screens, loading & error  |
| UseCases     | Repository delegation, business logic            |
| Paging       | Smoke tests for PagingData via flows             |

Tools used:
- **Turbine**: Flow testing
- **MockK**: Dependency mocking
- **JUnit4**: Structured testing
- **Coroutines Test**: Dispatcher and scheduling control
- **Espresso**: UI testing

---

---

## ✅ Running Tests

### 🧪 Unit Tests

To run all unit tests (e.g., ViewModels, UseCases):

```bash
./gradlew testDebugUnitTest
```

### 🧪 UI Tests
To run instrumented Espresso tests:
```bash
./gradlew connectedAndroidTest
```
Or from Android Studio:

    Open the test class inside androidTest/

    Click the green play icon next to a test method

Make sure:

    A device or emulator is running

    The project is built in debug or instrumented test configuration

These tests are located in `app/src/androidTest/`

## 🌐 Networking

The project is utilizing Retrofit using suspend functions.

---

## ⚙️ CI/CD with GitHub Actions

This project includes a CI pipeline using **GitHub Actions** to ensure quality and catch regressions early.

### ✅ CI Steps

1. **Checkout the repository**
2. **Set up JDK 17** for Android builds
3. **Configure Gradle caching** to speed up builds
4. **Run Unit Tests** using JUnit4, MockK, Coroutines Test, and Turbine
5. **Assemble the Debug APK**
6. **Upload the APK** as a downloadable artifact in GitHub Actions

You can find the workflow in `.github/workflows/android-ci.yml`.

> ⚠️ **Note**: Espresso tests were removed from the CI pipeline due to emulator instability in GitHub-hosted runners. UI tests should be run locally for now.

### 📦 Artifact Output

After each successful run, you can download the built APK from the **"Artifacts"** section of the GitHub Actions run:


## ♿ Accessibility & UI

- I have a better level of accessibility using `semantic` modifier of compose for list items
- Shimmer loading effects with `AnimatedVisibility`
- Material 3 theming and scalable typography
- Centralized ui components `core_ui`

---

   
