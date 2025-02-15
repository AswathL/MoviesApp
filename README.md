# Movie App

A simple Android application built with Jetpack Compose, Retrofit, and Hilt, following the MVVM architecture. The app fetches movie and TV show data from the WatchMode API and displays it in a user-friendly interface.

## Features

- **Home Screen**: Displays a list of movies and TV shows.
- **Details Screen**: Shows detailed information about a selected movie or TV show.
- **Shimmer Effect**: A loading animation while data is being fetched.
- **Error Handling**: Displays an error message if something goes wrong.
- **Navigation**: Uses Jetpack Navigation to navigate between screens.

## Screenrecording

![MoviesApp](https://github.com/AswathL/MoviesApp/blob/main/screen-20250215-235936~2.mp4)

## Screenshots

### Home Screen
![Home Screen](https://github.com/AswathL/MoviesApp/blob/main/Screenshot_20250216-011946.png)

### Details Screen
![Details Screen](https://github.com/AswathL/MoviesApp/blob/main/Screenshot_20250216-011952.png)


## Technologies Used

- **Jetpack Compose**: Modern Android UI toolkit.
- **Retrofit**: For making network requests.
- **Hilt**: For dependency injection.
- **Coil**: For image loading.
- **WatchMode API**: For fetching movie and TV show data.

## Project Structure
```
com.example.movies
├── data
│   ├── ApiService.kt
│   ├── MovieRepository.kt
│   ├── Title.kt
│   ├── TitleDetailsResponse.kt
├── di
│   └── NetworkModule.kt
├── userInterface
│   ├── HomeScreen.kt
│   ├── DetailsScreen.kt   
├── viewmodel
│   └── MovieViewModel.kt
├── MovieApp.kt
└── MainActivity.kt
```

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/AswathL/MoviesApp.git
   ```
2. Open the project in Android Studio.
3. Replace the API_KEY in `ApiService.kt` with your own WatchMode API key.
4. Build and run the app on an Android device or emulator.

## License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/AswathL/MoviesApp/blob/main/LICENSE) file for details.

---
