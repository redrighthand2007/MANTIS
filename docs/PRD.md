# Product Requirements Document (PRD)
## Mantis Calculator App

**Document Version:** 1.0
**Product Name:** Mantis Calculator
**Platform:** Android (Min SDK 26)

---

## 1. Executive Summary
Mantis Calculator is a premium, privacy-first, offline Android calculator app designed to be the ultimate all-in-one mathematical tool. It targets users who need more than a basic calculator by offering Scientific, Programmer, and Unit Converter modes natively, along with a persistent history tracker. Unlike modern calculator apps that are bloated with ads or require cloud connectivity, Mantis operates 100% locally on the device, ensuring maximum privacy and zero latency.

## 2. Target Audience
- **Students & Academics:** Need access to quick scientific functions (Trigonometry, Logarithms) without switching to a dedicated scientific calculator app.
- **Developers & Engineers:** Need to rapidly convert between number bases (Decimal, Hexadecimal, Binary, Octal) and perform bitwise operations.
- **Everyday Users:** Need quick, ad-free unit conversions (Length, Weight, Temperature) and a reliable history tape to double-check their math.
- **Privacy Advocates:** Users who refuse to use apps that collect analytics, telemetry, or require internet permissions.

## 3. Core Objectives
- **All-in-One Utility:** Combine four critical tools (Basic, Scientific, Programmer, Converter) into a single, unified interface.
- **Seamless Navigation:** Ensure users can switch between modes instantly without losing context or experiencing UI layout jumps.
- **Zero Distractions:** Deliver a beautiful, Material 3 design free of ads, popups, and tracking.
- **Tactile Experience:** Provide premium haptic feedback for a satisfying calculation experience.

---

## 4. Product Features & Requirements

### 4.1 Basic Calculator
- **Standard Arithmetic:** Support for addition, subtraction, multiplication, and division.
- **Parentheses:** Support for nested grouping of operations.
- **Real-Time Evaluation:** Display the result of the expression in real-time before the user presses "=".
- **Formatting:** Numbers must be formatted cleanly (e.g., `4.00` drops trailing zeros to display as `4`).

### 4.2 Scientific Calculator
- **Core Functions:** Trigonometry (`sin`, `cos`, `tan`, and their inverses), Logarithms (`log`, `ln`), Exponents (`x²`, `x³`, `xⁿ`), and Roots (`√`, `³√`).
- **Constants:** Include `π` and `e`.
- **Mode Toggles:** 
  - `DEG/RAD` toggle for trigonometric calculations.
  - `2nd` mode toggle to swap primary keys to secondary functions (e.g., `sin` becomes `sin⁻¹`).

### 4.3 Programmer Calculator
- **Multi-Base Display:** Show the current value simultaneously in Decimal (DEC), Hexadecimal (HEX), Octal (OCT), and Binary (BIN).
- **Active Base Switching:** Tapping a base makes it the active input mode.
- **Dynamic Keypad:** 
  - Keys `A-F` are only active in HEX mode.
  - Keys `8-9` are disabled in OCT and BIN modes.
  - Keys `2-9` are disabled in BIN mode.
- **Bitwise Operations:** Support for `AND`, `OR`, `XOR`, `NOT`, and bit shifting (`<<`, `>>`).

### 4.4 Unit Converter
- **Supported Categories:** Length, Weight, and Temperature.
- **Dual-Dropdown UI:** Top layout featuring a "From" unit dropdown and a "To" unit dropdown.
- **Real-time Conversion:** As the user types the input, the output must convert and update instantly.
- **Algorithm handling:** Temperature conversions must utilize formulaic conversion (e.g., `(F-32) * 5/9`), not flat multiplication ratios.

### 4.5 History System
- **Local Persistence:** All evaluated equations from the Basic and Scientific modes must be saved to a local SQLite database (Room).
- **Reverse-Chronological Tape:** History must be displayed bottom-up, with the most recent calculations appearing just above the taskbar.
- **Clean UI:** The history screen must be completely immersive and blank, save for the history cards and the bottom navigation bar.

### 4.6 Settings & Customization
- **Theme Engine:** Allow users to force Dark Mode, Light Mode, or follow the System Default. Theme changes must apply instantly across the entire application.
- **Haptic Feedback:** A master toggle to enable or disable vibration on keypress.
- **About Section:** A professional card attributing the app to the developer (Kush) and displaying the current version.

---

## 5. UI / UX Guidelines
- **Strict Proportions:** The layout must enforce a strict `35% (Display)` / `65% (Keypad)` weight ratio across Basic, Scientific, Programmer, and Converter tabs. This prevents the keypad from visually "jumping" or resizing when the user navigates between modes.
- **Color Palette:** Deep dark backgrounds with glowing neon green (`#00E676`) accents for operators and active states. Red accents for destructive actions (Clear) and Orange accents for backspace.
- **Bottom Navigation:** A persistent bottom tab bar must be present on every screen for instant switching.

---

## 6. Technical Requirements
- **Framework:** Jetpack Compose (100% Declarative UI).
- **Architecture:** MVVM (Model-View-ViewModel) enforcing a unidirectional data flow.
- **Dependency Injection:** Hilt.
- **Local Storage:** Room Database (History) and Jetpack DataStore (Settings).
- **Math Engine:** mXparser (for evaluating complex string expressions safely).
- **Permissions:** No permissions requested. Zero internet access.

## 7. Future Considerations (v2.0)
- Currency Converter (would require opt-in internet permission to fetch exchange rates).
- Floating Window mode for multitasking.
- History search and export functionality.
- Graphing capabilities for the Scientific mode.
