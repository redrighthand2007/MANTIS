<div align="center">
  <img src="design/logo/wings_only.svg" alt="Mantis Calculator Logo" width="120" style="border-radius: 24px; box-shadow: 0 4px 14px rgba(0,0,0,0.1);"/>

  <h1>Mantis Calculator</h1>
  
  <p><strong>An "all-in-one" offline Android calculator built for power users.</strong></p>

  <p>
    <a href="https://android.com"><img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Platform"/></a>
    <a href="https://kotlinlang.org"><img src="https://img.shields.io/badge/Kotlin-100%25-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin"/></a>
    <a href="https://developer.android.com/jetpack/compose"><img src="https://img.shields.io/badge/Compose-Material_3-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Compose"/></a>
    <img src="https://img.shields.io/badge/Min_SDK-24-brightgreen?style=for-the-badge" alt="Min SDK"/>
    <img src="https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge" alt="License"/>
  </p>
</div>

---

## 📸 Preview / Demo

<div align="center">
  <!-- Add your screenshots here. Replace the placeholders with actual screenshot paths. -->
  <img src="https://via.placeholder.com/200x400.png?text=Basic+Mode" width="200" alt="Basic Mode" style="margin: 10px; border-radius: 12px;"/>
  <img src="https://via.placeholder.com/200x400.png?text=Scientific+Mode" width="200" alt="Scientific Mode" style="margin: 10px; border-radius: 12px;"/>
  <img src="https://via.placeholder.com/200x400.png?text=Programmer+Mode" width="200" alt="Programmer Mode" style="margin: 10px; border-radius: 12px;"/>
</div>

## ✨ Features

- **🧮 Basic & Scientific Engines**: Parentheses support, dynamic degree/radian toggle, and advanced trigonometric/logarithmic functions.
- **👨‍💻 Programmer Mode**: Real-time base conversions (DEC, HEX, OCT, BIN) and live bitwise logic operations (AND, OR, XOR, `<<`, `>>`).
- **🔄 Unit Converter**: Instant conversions spanning Length, Weight, Temperature, and Volume.
- **✨ Fluid UI & UX**: Modern floating pill-shaped taskbar, seamless sliding animations between modes, and custom haptic feedback integration.
- **📝 Smart Cursor & Copy/Paste**: Fully editable equation cursor and native long-press copy/paste selection.
- **📜 Persistent History**: History saved securely on your device using a local Room (SQLite) database.
- **🔒 100% Offline & Private**: Zero tracking, zero ads, zero internet permissions required. Your data never leaves your device.

## 🎯 Why This Project?

Most calculator apps today are either too basic, fragmented across multiple apps, or bloated with intrusive ads and unnecessary permissions. 

**Mantis** solves this by unifying four professional-grade calculation engines into a single, beautiful, lightning-fast application. Designed with strict separation of concerns using Clean Architecture, it ensures a snappy, reliable experience that respects your privacy.

## 🛠️ Tech Stack

- **Frontend / UI:** Android, Kotlin (100%), Jetpack Compose (Material 3)
- **Architecture:** Clean Architecture, MVVM, Unidirectional Data Flow (UDF)
- **Database / Local Storage:** Room Database (SQLite), Jetpack DataStore
- **Math Engine:** mXparser (robust string-based expression evaluation)
- **Dependency Injection:** Dagger Hilt
- **Navigation:** Jetpack Navigation Compose with custom animated transitions

## 📁 Project Structure

```text
com.kush.mantis/
├── core/                # Shared UI components, DI modules, Data layers
├── features/                      
│   ├── basic/           # UI, ViewModel, and Math Use Cases
│   ├── scientific/      # Trigonometric engine and state
│   ├── programmer/      # Base-N conversions & bitwise logic
│   ├── converter/       # Real-time unit conversions
│   ├── history/         # Room database integration
│   └── settings/        # DataStore preferences & Haptics
├── navigation/          # Floating bottom bar & animated route transitions
└── ui/theme/            # Material 3 color schemes
```

## ⚙️ Installation

To build and run the app locally:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/redrighthand2007/Mantis-App.git
   cd Mantis-App
   ```
2. **Open in Android Studio:**
   Open the project using Android Studio (Ladybug or newer recommended).
3. **Build and Install:**
   Click the **Run** button (▶️) or build via the terminal:
   ```bash
   ./gradlew assembleDebug
   ```

## 🚀 Usage

Since Mantis is completely offline, simply launch the app and start calculating! 
- Use the **floating bottom taskbar** to switch between Basic, Scientific, Programmer, Converter, and History modes. 
- **Tap anywhere** in the math expression to move the editable cursor.
- **Long press** the result to securely copy it to your clipboard.

## 🔧 Configuration

**Zero configuration required.** Mantis does not rely on any third-party APIs or cloud services. Everything runs natively on your device, ensuring total privacy and offline reliability.

## 📊 Results / Performance

- **Fluid Animations:** Custom dynamic sliding transitions guarantee a seamless 60fps UX across all screen navigations.
- **Zero-Latency Processing:** Intensive logic parses instantly via optimized mXparser logic offloaded from the UI thread.
- **Adaptive Design:** Edge-to-edge UI securely handles device insets natively.

## 🧠 How It Works

Mantis strictly adheres to Clean Architecture principles to keep components modular and highly decoupled.

```text
             USER
               ↕
      ┌────────────────────────────────────────────────────────┐
      │  UI (Jetpack Compose)                                  │
      └────────────────────────┬───────────────────────────────┘
                   ↕ (UDF / StateFlow)
      ┌────────────────────────┴───────────────────────────────┐
      │         ViewModel                                      │
      └────────────────────────┬───────────────────────────────┘
                   ↕                                 ↕
       ┌───────────────────────┴───┐       ┌─────────┴─────────────┐
       │ mXparser (Math Engine)    │       │ Room DB (History)     │
       └───────────────────────────┘       └───────────────────────┘
```

- **Domain Layer:** Business logic (`EvaluateExpressionUseCase`, `GetHistoryUseCase`) isolates the UI from data sources.
- **Presentation Layer:** Jetpack Compose observes state emitted via Kotlin `StateFlow` from ViewModels.
- **Data Layer:** Room DAOs and DataStore manage actual persistence.

## 🗺️ Roadmap

- [x] Core Basic & Scientific Calculator Engines
- [x] Programmer & Converter Modes
- [x] Persistent History with Room Database
- [x] Modern UI: Editable Cursor, Copy/Paste, Floating Taskbar
- [x] Smooth sliding navigation transitions
- [ ] Custom Landscape Mode optimization
- [ ] Additional Unit Converter modules

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! 
1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

## 👨‍💻 Author

Designed & Developed with 💚 by **Kush**
- GitHub: [@redrighthand2007](https://github.com/redrighthand2007)

## ⭐ Acknowledgements

- [mXparser](https://mathparser.org/) for the incredibly robust math evaluation engine.
- The **Jetpack Compose** team for the beautiful modern UI toolkit.
