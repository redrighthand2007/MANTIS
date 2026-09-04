<div align="center">
  <img src="design/logo/wings_only.svg" alt="Mantis Calculator" width="120" style="border-radius: 24px; box-shadow: 0 4px 14px rgba(0,0,0,0.1);"/>

  <h1>Mantis Calculator</h1>
  
  <p><strong>An "all-in-one" Android calculator built for power users.</strong></p>

  <p>
    <a href="https://android.com"><img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Platform"/></a>
    <a href="https://kotlinlang.org"><img src="https://img.shields.io/badge/Kotlin-100%25-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin"/></a>
    <a href="https://developer.android.com/jetpack/compose"><img src="https://img.shields.io/badge/Compose-Material_3-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Compose"/></a>
    <img src="https://img.shields.io/badge/Min_SDK-24-brightgreen?style=for-the-badge" alt="Min SDK"/>
  </p>
</div>

---

## 📖 Project Overview

### What is it?
An "all-in-one" Android calculator built for power users.

### Why did you build it?
Most calculator apps today are either too basic, fragmented across multiple apps, or bloated with intrusive ads and unnecessary permissions. 

**Mantis** solves this by unifying four professional-grade calculation engines into a single, beautiful, lightning-fast application. It is completely offline, ad-free, and designed with strict separation of concerns using Clean Architecture.

---

## ✨ Features

| Mode | Highlights |
|------|------------|
| 🔢 **Basic** | Standard arithmetic for everyday use, with parentheses support and precise large-number formatting. |
| 📐 **Scientific** | Advanced math (`sin`, `cos`, `tan`, `log`, `ln`, `x^y`, `x²`, `√`, `e`) with a dynamic, stateful toggle between **Degrees and Radians**. |
| 💻 **Programmer** | Real-time base conversions (`DEC`, `HEX`, `OCT`, `BIN`) and live bitwise logic operations (`AND`, `OR`, `XOR`, `<<`, `>>`). |
| ⚖️ **Converter** | Instant unit conversions spanning Length, Weight, Temperature, and **Volume**. |
| 📜 **History** | Persistent calculation history saved securely via local Room (SQLite) database. |
| ⚙️ **Settings** | Edge-to-edge Material 3 UI, Dark/Light theme switching, and custom Haptic Feedback integration. |

### 🛡️ Pro's
- **100% Offline** — Zero internet permissions required.
- **Zero Tracking** — No analytics, no ads, no telemetry.
- **Local Storage** — Your data never leaves your device.

---

## 🛠️ Tech Stack

* **Language:** Kotlin (100%)
* **UI Framework:** Jetpack Compose with Material 3 (Edge-to-Edge window insets, custom entry/exit animations)
* **Architecture:** Clean Architecture + MVVM + Unidirectional Data Flow (UDF)
* **Dependency Injection:** Dagger Hilt
* **Database & Preferences:** Room Database (History) and Jetpack DataStore (Settings)
* **Math Engine:** mXparser (for robust string-based expression evaluation)
* **Navigation:** Jetpack Navigation Compose with custom transitions

---

## 🏗️ Architecture

```text
             USER
               │
               ▼
      ┌─────────────────────────────┐
      │  UI (Jetpack Compose)       │
      └────────────┬────────────────┘
                   │ (UDF / StateFlow)
                   ▼
      ┌─────────────────────────────┐
      │         ViewModel           │
      └────────────┬────────────────┘
                   │
       ┌───────────┴───────────┐
       ▼                       ▼
 ┌─────────────┐         ┌─────────────┐
 │ mXparser    │         │ Room DB     │
 │ (Math)      │         │ (History)   │
 └─────────────┘         └─────────────┘
```

The project is modularized by feature, ensuring code is scalable, testable, and highly decoupled.

```text
com.kush.mantis/
├── core/                          # Shared UI components, DI modules, Data layers
├── features/                      
│   ├── basic/                     # UI, ViewModel, and Math Use Cases
│   ├── scientific/                # Trigonometric engine and state
│   ├── programmer/                # Base-N conversions & bitwise logic
│   ├── converter/                 # Real-time unit conversions
│   └── history/                   # Room database integration
│   └── settings/                  # DataStore preferences & Haptics
├── navigation/                    # Bottom bar & animated route transitions
└── ui/theme/                      # Material 3 color schemes
```

- **Domain Layer:** Business logic (e.g., `EvaluateExpressionUseCase`, `GetHistoryUseCase`) isolates the UI from data sources.
- **Presentation Layer:** Jetpack Compose observes state emitted via Kotlin `StateFlow` from ViewModels.
- **Data Layer:** Room DAOs and DataStore manage actual persistence.

---

<div align="center">
  <br/>
  <p>Designed & Developed with ❤️ by <strong>Kush</strong></p>
</div>
