# MANTIS Precision Calculator

Mantis is a fast, offline, privacy-first calculator app for Android. Designed with a perfectly balanced 10-row grid architecture and adaptive themes, it combines four professional-grade calculation engines into a single, beautiful utility.

## Features

- **Basic & Scientific:** Parentheses support, dynamic degree/radian toggle, and advanced trigonometric functions powered by mXparser.
- **Programmer:** Real-time base conversions (DEC, HEX, OCT, BIN) and bitwise logic operations.
- **Unit Converter:** Instant conversions spanning Length, Weight, Temperature, and Volume.
- **Chain Calculations:** Classic "evaluate on equals" engine seamlessly pulls answers back into the input field for continuous math chaining.
- **Perfect Geometry:** Built on a strict 40/60 display-to-keypad ratio across a 10-row grid layout (locked to Portrait for layout integrity).
- **Adaptive Aesthetics:** Dynamic, custom-drawn Mantis banners that adapt elegantly to your system's Light or Dark themes.
- **100% Offline & Stateless:** Zero tracking, zero ads, no databases, no internet permissions required.

## Tech Stack

- **Language:** Kotlin (100%)
- **UI Toolkit:** Jetpack Compose (Material 3)
- **Architecture:** Clean Architecture, MVVM, Unidirectional Data Flow (UDF)
- **Math Engine:** mXparser
- **Dependency Injection:** Dagger Hilt
- **Navigation:** Jetpack Navigation Compose

## Project Structure

\\\	ext
com.kush.mantis/
├── core/                # Shared UI components, Formatters, DI modules
├── features/                      
│   ├── basic/           # Basic Math UI, ViewModel, EvaluateUseCase
│   ├── scientific/      # Trigonometric engine and state
│   ├── programmer/      # Base-N conversions & bitwise logic
│   └── converter/       # Unit conversion engine
├── navigation/          # Animated route transitions & Bottom bar
└── ui/theme/            # Material 3 colors, typography, themes
\\\

## Installation

1. **Clone the repository:**
   \\\ash
   git clone https://github.com/redrighthand2007/Mantis-App.git
   cd Mantis-App
   \\\
2. **Open in Android Studio:** Open the project in Android Studio (Ladybug or newer).
3. **Build and Run:** Run the \pp\ configuration or build via terminal: \./gradlew installDebug\

## Usage

Simply launch Mantis and calculate. 
- Use the **floating bottom taskbar** to switch between modes.
- Evaluate expressions natively by hitting **=** to commit results and continue building off them.
- Everything runs instantly on your local device.

## Acknowledgements

- Designed & Developed by **Kush**
- Uses [mXparser](https://mathparser.org/) for robust mathematical evaluation.