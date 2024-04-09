# Android Basic Template App (single module)
![Static Badge](https://img.shields.io/badge/platform-Android-green?color=3DDC84&link=https%3A%2F%2Fdeveloper.android.com%2F) ![Static Badge](https://img.shields.io/badge/Kotlin-1.9.22-purple?color=7f52ff&link=https%3A%2F%2Fkotlinlang.org%2Fdocs%2Fhome.html) ![Static Badge](https://img.shields.io/badge/licence-MIT-red?color=9d2235&link=https%3A%2F%2Fgithub.com%2Fgpillaca%2FUpcomingMovies%2Fblob%2Fmaster%2FLICENSE)

This template is compatible with the latest stable version of Android Studio.
Upcoming Movies App written in Kotlin using the TMDB API.

## Usage

1. Clone the repository:
   ```
   git clone git@github.com:gpillaca/android-basic-template.git
   ```
2. Run the customizer script:
   
   > bash [package] [YourApplicationName] [AppName]
  
   ```
   bash customizer.sh your.package.name YouApplication AppName 
   ```
   - **[package]** is your app ID (should be lowercase)
   - **[aplication name]** is Application Class (should be PascalCase) [Optionally]
   - **[appname]** is appname for you application (should be PascalCase) [Optionally]

3. In Android Studio: Clean your project

    - **Build > Clean project**
    - **File > Sync Project with Gradle Files**

> [!NOTE]
> the customizer script requires bash 4 and above. You might have to install a modern version on macOS:

```
brew install bash
```

> [!WARNING]  
> - Open `.gitignote` and uncommented `local.properties`.
> - In a real project don't upload the `local.properties` file with your privete keys.

## Screenshots

| Upcomming movies | Movie detail |
| --- | --- |
|<img src="https://github.com/gpillaca/UpcomingMovies/blob/master/screenshot/upcoming-movies.png?raw=true" width="300" /> | <img src="https://github.com/gpillaca/UpcomingMovies/blob/master/screenshot/movie-detail.png?raw=true" width="300" /> |

## Build
- [x] [KTS gradle files](https://docs.gradle.org/current/userguide/platforms.html)
- [x] [Version catalog](https://developer.android.com/build/migrate-to-catalogs)

## Architecture
- [ ] UI using [Jetpack Compose](https://developer.android.com/develop/ui/compose) - WIP
- [ ] [Navigation Compose](https://developer.android.com/develop/ui/compose/navigation) - WIP
- [x] Dependency injection with [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [x] [Jetpack ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [x] [Jetpack Navigation](https://developer.android.com/develop/ui/compose/navigation)
- [x] [Kotlin Coroutines and Flow](https://developer.android.com/kotlin/coroutines)
- [x] [Room Database](https://developer.android.com/training/data-storage/room)

- [x] [Gson](https://github.com/google/gson)
- [x] [Glide](https://github.com/bumptech/glide)
- [x] [Retrofit](https://square.github.io/retrofit/)
    
- [x] [Unit tests](https://developer.android.com/training/testing/local-tests)
- [x] [Mockito](https://github.com/mockito/mockito)
- [x] [Turbine](https://github.com/cashapp/turbine) is a small testing library for kotlinx.coroutines Flow

## Author

Geferson Pillaca, gpillacag@gmail.com 
