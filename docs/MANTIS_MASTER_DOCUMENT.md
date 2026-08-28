# 📱 MANTIS CALCULATOR: Master Project Documentation

*A comprehensive guide covering the ideation, technical architecture, feature set, and development journey of the Mantis Calculator Android application.*

---

## 💡 1. The Idea & The Problem
**The Modern Problem:**
If you look at the Google Play Store today, the calculator app ecosystem is severely fragmented and user-hostile. 
* **Privacy Intrusions & Ads:** The vast majority of free calculator apps are bloated with intrusive full-screen video ads and require unnecessary permissions (like network access or location tracking) just to perform basic math.
* **Fragmentation:** Users who need basic math, complex trigonometry, hexadecimal base conversions, and unit conversions typically have to download 3 or 4 entirely separate apps. 

**The Mantis Idea (Our Solution):**
Mantis Calculator was envisioned to be the ultimate "All-In-One" utility for power users, developers, and students. 
* **Privacy-First:** 100% offline. Zero internet permissions required. No ads.
* **Unified Utility:** It combines a Basic Calculator, Scientific Calculator, Programmer (Base-N / Bitwise) Calculator, and Unit Converter into a single, beautiful, lightning-fast application.

---

## 🛠️ 2. Tech Stack & Technologies Used
Mantis is built strictly using modern, industry-standard Android development practices:
* **Language:** 100% Kotlin
* **UI Framework:** Jetpack Compose (Material 3) with Edge-to-Edge window inset handling and custom enter/exit animations.
* **Architecture:** Clean Architecture + MVVM (Model-View-ViewModel) + Unidirectional Data Flow (UDF).
* **Dependency Injection:** Dagger Hilt
* **Database & Persistence:** 
  * **Room (SQLite):** Used for storing the calculation history reliably.
  * **Jetpack DataStore:** Used for saving user preferences (Dark Mode, Haptic Feedback).
* **Math Engine:** **mXparser** — A highly capable mathematical evaluation library used to parse string-based equations (including trigonometric functions and custom modes).
* **Testing:** JUnit for ensuring mathematical precision and bitwise logic accuracy.

---

## 🚀 3. Comprehensive Feature Set

### 1. Basic Mode
For everyday calculations. Supports standard arithmetic operators, decimals, and percentage calculations. 

### 2. Scientific Mode
Aimed at students and engineers. Features advanced mathematical operators:
* Trigonometry (`sin`, `cos`, `tan`)
* Logarithms and Natural Logs (`log`, `ln`)
* Roots and Exponents (`√`, `x²`, `x^y`)
* Constants (`π`, `e`)
* **Stateful Context:** Features a live toggle between **Degrees** and **Radians** that instantly updates the mathematical engine's context.

### 3. Programmer Mode
Designed for Computer Science and IT professionals. 
* **Base Conversions:** Instantly converts inputs between Decimal (`DEC`), Hexadecimal (`HEX`), Octal (`OCT`), and Binary (`BIN`).
* **Bitwise Operations:** Supports live logic operations including `AND`, `OR`, `XOR`, Left Shift (`<<`), and Right Shift (`>>`).

### 4. Converter Mode
Real-time unit conversions across four major categories: Length, Weight, Temperature, and Volume. 

### 5. Persistent History
Every time a user presses equals (`=`) in the Basic or Scientific mode, the equation and result are saved to a local SQLite database. Users can view their past calculations in the History tab and clear them at will.

### 6. Settings & Haptics
* **Theming:** Users can force Light Mode, Dark Mode, or match the System default.
* **Haptics:** A toggle to enable/disable physical vibration feedback when interacting with buttons and navigating between screens.

---

## 🏗️ 4. How It Handles Everything (Architecture & Redirections)

### Architectural Flow (Clean Architecture + MVVM)
The project strictly isolates concerns to prevent "spaghetti code":
1. **Presentation Layer (UI):** Jetpack Compose Screens (`ScientificScreen`, `BasicScreen`) observe state from ViewModels.
2. **Presentation Layer (ViewModels):** `ScientificViewModel`, `HistoryViewModel`, etc. These handle user intents (like button clicks) using Kotlin `StateFlow`. They do *not* execute complex logic themselves.
3. **Domain Layer (Use Cases):** Encapsulates the core business logic. For example, `EvaluateExpressionUseCase` handles the mXparser logic, and `InsertHistoryUseCase` handles database validation. ViewModels inject these Use Cases via Hilt.
4. **Data Layer (Repositories & DAOs):** Room DAOs and DataStore repositories actually write to the disk.

### Redirections & Navigation
Navigation is handled by Jetpack Compose `NavHost` (`MantisNavHost.kt`). 
* **Bottom Navigation Bar:** A custom `BottomNavBar` allows seamless switching between the 6 routes.
* **Transitions:** Cross-fade animations (`fadeIn` / `fadeOut` over 300ms) are applied to the `NavHost` to ensure screen changes feel premium and fluid, rather than abrupt.
* **Haptics Integration:** The Bottom Navigation bar reads the user's `hapticFeedbackFlow` preference and triggers a `LongPress` vibration only if the user has haptics enabled and is navigating to a *new* screen.

---

## 🛤️ 5. Step-by-Step Development Journey

### Step 1: UI Foundation & Jetpack Compose
We began by scaffolding the 6 distinct screens using Jetpack Compose and setting up the `MantisNavHost`. The primary focus was creating responsive, scalable calculator grid layouts using Compose `weight` modifiers so the buttons looked perfect on both small phones and large tablets.

### Step 2: The Math Engine (mXparser)
Writing a math parser from scratch (handling order of operations and parentheses) is error-prone. We integrated `mXparser`, but ran into a challenge: formatting the output properly. We built a custom `NumberFormatter` utility to strip out unnecessary trailing decimals (turning `5.0` into `5`) and automatically add comma groupings (turning `5000` into `5,000`).

### Step 3: Programmer Logic
Implementing Base-N calculations was a unique challenge. We had to build a custom state machine in the `ProgrammerViewModel` to track the *previous* input, the *pending operator* (like `AND`), and the *current* input, allowing sequential bitwise operations just like a physical calculator.

### Step 4: Data Persistence (Room & DataStore)
We implemented a Room Database for the History tab. We created a `CalculationHistory` entity, a Data Access Object (DAO), and a Repository. We also set up Jetpack DataStore to remember user preferences.

### Step 5: Clean Architecture Wiring (Hilt Refactor)
Initially, we injected Repositories directly into ViewModels. To achieve true production-ready quality, we refactored the app to use **Domain Use Cases** (e.g., `GetHistoryUseCase`, `SettingsUseCases`). We used **Dagger Hilt** to inject these dependencies across the app, ensuring the code was modular and highly testable.

### Step 6: Unit Testing
We wrote extensive JUnit tests (`EvaluateExpressionUseCaseTest`, `BaseConverterTest`, `ConvertUnitUseCaseTest`) to mathematically prove that our logic, base conversions, and edge-cases (like division by zero) were handled safely without crashing.

### Step 7: UI Polish (Edge-to-Edge & Animations)
Finally, we polished the UI. We added `fadeIn`/`fadeOut` animations to the navigation graph. We also encountered an issue with modern Android's `enableEdgeToEdge()` API causing the system gesture bar to overlap our bottom menu. We resolved this by carefully managing system insets and reverting aggressive edge-to-edge drawing on the navigation bar to guarantee a clean layout across all Android versions.

---

## 🧱 6. Hardships & How We Solved Them (For Showcase Discussions)

**1. The Degree/Radian State Problem**
* **Hardship:** In the Scientific mode, users can toggle between Degrees and Radians. The mXparser engine needed to know this context when evaluating `sin(90)`, but mXparser relies on a global static configuration, which is dangerous in an asynchronous Android app.
* **Solution:** We wrapped the mXparser logic inside an injected `EvaluateExpressionUseCase`. We passed `isDegreeMode` as a parameter to the Use Case every time the user pressed equals. Inside the Use Case, we dynamically reconfigured `mXparser.setDegreesMode()` or `mXparser.setRadiansMode()` immediately before evaluation, guaranteeing mathematically accurate results regardless of how fast the user toggled the button.

**2. Formatting Bitwise Outputs**
* **Hardship:** In Programmer mode, when a user typed in Hexadecimal (e.g., `1A`), the app needed to convert it to Decimal to perform Bitwise math (`AND`, `OR`), and then convert the result *back* to Hexadecimal to display it.
* **Solution:** We abstracted all conversions into a `BaseConverter` utility object. The ViewModel stores the raw Decimal `Long` value in memory as the "source of truth", but the UI rigorously maps that `Long` into BIN, OCT, DEC, and HEX strings in real-time, completely decoupling the visual formatting from the mathematical logic.

**3. Coroutine Scope Memory Leaks in Database Calls**
* **Hardship:** Writing to the Room Database must happen on a background thread. Doing this directly in the Compose UI could lead to memory leaks if the user navigated away before the database transaction finished.
* **Solution:** We utilized `viewModelScope.launch(Dispatchers.IO)` within our ViewModels to trigger the injected `InsertHistoryUseCase`. This ensured that the database write operation was tied to the ViewModel's lifecycle and offloaded from the Main UI thread, keeping the app buttery smooth.
