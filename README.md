# Mantis (Precision n' Power)
The ultimate offline calculator. Basic. Scientific. Programmer. Converter.

## 📸 Preview / Demo
Demo / Screenshots Coming Soon!

## ✨ Features
- **Basic & Scientific:** Advanced trigonometric functions, parenthesis support, and dynamic Radian/Degree toggles powered by mXparser.
- **Programmer Mode:** Real-time base conversions (DEC, HEX, OCT, BIN) and live bitwise logic ops (AND, OR, XOR, `<<`, `>>`).
- **Unit Converter:** Instant, accurate conversions spanning Length, Weight, Temperature, and Volume.
- **Chain Calculations:** Classic "evaluate on equals" engine seamlessly pulls your answers back into the input field for continuous math chaining!
- **Perfect Geometry:** Built on a strict 40/60 display-to-keypad ratio across a meticulously measured 10-row grid layout.
- **Adaptive Aesthetics:** Custom-drawn banners adapt elegantly to Light or Dark themes. Soft pastel greens in the day, deep stealthy forest greens at night.
- **100% Offline & Private:** Zero tracking. Zero ads. Zero databases. Zero internet permissions required.

## 🎯 Why This Project?
Most calculator apps today are either too basic, fragmented across multiple apps, or bloated with intrusive ads and unnecessary permissions. 

Mantis solves this by providing:
- Four professional-grade math engines unified into one app.
- A mathematically perfect 10-row UI structure that never distorts.
- A completely private, offline, and stateless calculation environment.

## 🛠️ Tech Stack
- **Frontend:** Android (100% Kotlin, Jetpack Compose, Material 3)
- **Math Engine:** mXparser (Robust mathematical evaluation)
- **Architecture:** MVVM, Clean Architecture, Unidirectional Data Flow (UDF)
- **Dependency Injection:** Dagger Hilt
- **Navigation:** Jetpack Navigation Compose

## 📁 Project Structure
```text
MANTIS/
├── app/src/main/java/com/kush/mantis/
│   ├── core/           # Shared UI components, Formatters, DI modules
│   ├── features/       # Feature modules
│   │   ├── basic/      # Basic Math UI, ViewModel, EvaluateUseCase
│   │   ├── scientific/ # Trigonometric engine and state
│   │   ├── programmer/ # Base-N conversions & bitwise logic
│   │   └── converter/  # Unit conversion engine
│   ├── navigation/     # Animated route transitions & Bottom bar
│   └── ui/             # Material 3 colors, typography, themes
└── README.md
```

## ⚙️ Installation
1. **Clone the repository:** 
   ```bash
   git clone https://github.com/redrighthand2007/Mantis-App.git
   cd MANTIS
   ```
2. **Open the project:** Open the folder in Android Studio (Ladybug or newer recommended).
3. **Sync Gradle:** Allow Android Studio to sync the Gradle files and download all required dependencies.

## 🚀 Usage
- Build and run the app on an Android Emulator or physical device (API 26+).
- Use the floating bottom taskbar to seamlessly switch between calculation modes.
- Evaluate expressions natively by hitting **=** to commit results and continue building off them!

## 🔧 Configuration
**Zero configuration required.** 
Unlike cloud-based apps, Mantis does not rely on any third-party APIs, backends, or services. It is 100% completely offline. Just build and run.

## 📊 Results / Performance
- **Fluid Animations:** Custom dynamic sliding transitions guarantee a seamless 60fps UX across all screen navigations.
- **Zero-Latency Processing:** Intensive logic parses instantly via optimized mXparser logic offloaded from the UI thread.
- **Adaptive Design:** Edge-to-edge UI natively handles device insets.

## 🧠 How It Works
- **Architecture:** The app follows the MVVM (Model-View-ViewModel) architecture and uses Jetpack Compose for declarative UI. State is managed via Kotlin StateFlow and Coroutines.
- **Calculation Flow:** Mantis uses a strict "Calculate on Equals" system. When the user hits `=`, the expression is parsed via mXparser, and the resulting answer is instantly pushed back into the input buffer to allow continuous mathematical chaining.

## 🗺️ Roadmap
- [x] Basic & Scientific Calculator Engines
- [x] Programmer & Converter Modes
- [x] Strict 10-Row Mathematical Grid Layout
- [x] Modern UI: Adaptive Banners, Floating Taskbar
- [x] Smooth sliding navigation transitions
- [x] "Evaluate on Equals" continuous math engine
- [ ] Custom Landscape Mode optimization
- [ ] Additional Unit Converter modules

## 🤝 Contributing
Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://github.com/redrighthand2007/Mantis-App/issues) if you want to contribute.

## 📄 License
Distributed under the MIT License. See LICENSE for more information.

## 👨‍💻 Author
**Kush**  
GitHub: [@redrighthand2007](https://github.com/redrighthand2007)

## 🙌 Acknowledgements
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for modern Android UI
- [mXparser](https://mathparser.org/) for the incredibly robust math evaluation engine
- [Shields.io](https://shields.io/) for clean repository badges