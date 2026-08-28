# Context for AI Agent: Mantis Calculator Project

**Instructions for the AI Agent reading this:** 
The user wants to feature the "Mantis Calculator" project on their special GitHub profile repository (the `README.md` that appears on their main GitHub profile page). Use the following detailed context about the project, its architecture, and its features to generate an eye-catching, professional, and impressive GitHub Profile section.

---

## 📌 Project Overview
* **Name:** Mantis Calculator
* **Platform:** Native Android (Minimum API 24, Target API 35)
* **Status:** Fully Completed & Functional
* **Core Concept:** A privacy-focused, offline-first, "all-in-one" calculator designed for power users (engineers, developers, students) who are tired of downloading multiple ad-ridden apps for different calculation needs.

## 🚀 Key Features
The app contains four distinct calculation engines, plus utility features:
1. **Basic Mode:** Standard arithmetic for quick, everyday use.
2. **Scientific Mode:** Advanced mathematics including trigonometry (`sin`, `cos`, `tan`), logarithms, and exponents, featuring a dynamic state toggle between **Degrees and Radians**.
3. **Programmer Mode:** Supports Base-N conversions (Decimal, Hexadecimal, Binary, Octal) and live Bitwise operations (`AND`, `OR`, `XOR`, Bit Shifts).
4. **Converter Mode:** Seamless, real-time unit conversions across Length, Weight, Temperature, and Volume.
5. **Persistent History:** Every calculation is saved locally to a database, allowing users to review past work.
6. **Settings:** User-customizable UI with Dark/Light theme switching and Haptic Feedback toggles.

## 🛠️ Tech Stack & Tools
* **Language:** Kotlin (100%)
* **UI Framework:** Jetpack Compose (Material 3) with full Edge-to-Edge UI support.
* **Architecture:** Clean Architecture + MVVM (Model-View-ViewModel) + Unidirectional Data Flow (UDF).
* **Dependency Injection:** Dagger Hilt
* **Database / Local Storage:** Room Database (SQLite) for History, Jetpack DataStore for User Preferences.
* **Math Engine:** mXparser (for evaluating complex string-based mathematical expressions).
* **Testing:** JUnit (Comprehensive unit tests for mathematical accuracy and bitwise logic).

## 🧠 Technical Highlights & Achievements (To highlight developer competence)
* **Strict Separation of Concerns:** The project rigidly follows Clean Architecture. The UI layer (Compose) is completely isolated from the Data layer (Room/DataStore) via a dedicated **Domain Layer** consisting of Use Cases (e.g., `GetHistoryUseCase`, `EvaluateExpressionUseCase`).
* **Complex State Management:** Successfully handled multiple, completely different UI states and user intents using Kotlin `StateFlow` and sealed classes for event handling.
* **Robust Math Logic:** Integrated the `mXparser` library and wrapped it in Domain Use Cases to handle edge cases like division by zero, invalid formatting, and dynamic trigonometric context switching without crashing the app.
* **Modern Android Standards:** Utilized the latest Android development standards including Kotlin Symbol Processing (KSP), Material 3 design paradigms, and modern edge-to-edge window insets to prevent UI overlap with system navigation bars.

## 🎯 The Problem it Solves
Most mobile calculators are either too basic, require internet permissions (violating privacy), or are bloated with ads. Mantis solves this by providing professional-grade tools (like bitwise operations and complex trig) in a single, lightweight, extremely fast, completely offline, and ad-free package.
