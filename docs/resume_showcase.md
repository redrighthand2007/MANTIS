# Mantis Calculator - Resume Showcase

Here is everything you need to showcase the Mantis Calculator on your resume, formatted to highlight your technical skills, architectural decisions, and the problem you solved.

## 📝 1. Resume Bullet Points (Following Your Format)

Use these 3-4 bullet points directly under the "Projects" section of your resume. They follow the exact action-verb structure from your image:

* **Developed** a privacy-focused, offline-first Android calculator app (Mantis) supporting Basic, Scientific, Programmer (Bitwise/Base conversion), and Unit Conversion modes for engineers and students.
* **Architected** the application using Clean Architecture and MVVM patterns, utilizing **Dagger Hilt** for dependency injection and **Jetpack Compose** for a responsive, declarative Material 3 user interface.
* **Implemented** a robust, offline calculation history system using **Room (SQLite)** and managed user preferences (dark mode, haptics) with **Jetpack DataStore**, ensuring zero data loss between app sessions.
* **Engineered** a highly accurate mathematical evaluation engine integrating the **mXparser** library, achieving 100% unit test pass rate across complex trigonometric state changes and bitwise operations.

---

## 🛠️ 2. Tech Stack (To list under "Skills" or "Technologies Used")

* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (Material 3)
* **Architecture:** Clean Architecture, MVVM (Model-View-ViewModel), Unidirectional Data Flow (UDF)
* **Dependency Injection:** Dagger Hilt
* **Local Storage / Databases:** Room (SQLite), Jetpack DataStore (Preferences)
* **Math Engine:** mXparser
* **Testing:** JUnit
* **Development Tools:** Android Studio, Gradle, KSP (Kotlin Symbol Processing)

---

## 💡 3. What the Project Does (Elevator Pitch)

**Mantis Calculator** is a comprehensive, multi-functional Android utility app designed for power users. It features four distinct modes:
1. **Basic:** Standard arithmetic for everyday use.
2. **Scientific:** Advanced trigonometry, logarithms, and roots with dynamic Degree/Radian state management.
3. **Programmer:** Base-N conversions (Decimal, Hexadecimal, Binary, Octal) and bitwise operations (`AND`, `OR`, `XOR`, `SHIFT`).
4. **Converter:** Seamless unit conversions across length, weight, temperature, and volume.

It also features a persistent **Calculation History** log and a **Settings** page for theming and haptics, all wrapped in a modern, edge-to-edge UI.

---

## 🎯 4. What Technical/User Problem It Solves

### The User Problem:
Most calculator apps on the Google Play Store are plagued by intrusive advertisements, require unnecessary internet permissions (violating privacy), or are too simplistic. Engineers, computer science students, and developers often have to download three separate apps just to get a scientific calculator, a base-N programmer calculator, and a unit converter.

**The Solution:** Mantis provides an "all-in-one", ad-free, offline-first solution that respects user privacy while delivering professional-grade tools in a single, lightweight package.

### The Technical Problem:
Managing complex UI states across four totally different calculator modes while maintaining a persistent database history can easily lead to "spaghetti code" and UI freezes on Android.

**The Solution:** By strictly adhering to **Clean Architecture** and the **MVVM pattern**, the app completely isolates the mathematical evaluation logic (Domain Layer) and database operations (Data Layer) from the UI (Presentation Layer). Using Kotlin `StateFlow` and Dagger Hilt ensures that the app remains incredibly fast, scalable, and crash-free, even when performing heavy calculations or writing to the Room database.
