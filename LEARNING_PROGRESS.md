# Mantis: Learning Progress Tracker

How Mantis gets built, from an empty folder to a signed app ready for the Play Store, one step at a time.

**How to use this file**
- **Done** shows whether the step exists in the project. ✅ means it's built, ⬜ means it isn't built yet (only the "Next steps" phase at the end).
- **Learned** is yours to fill in. When you can explain a step in your own words *without looking at the code*, change its ⬜ to ✅.
- Open the file named in each explanation and read it alongside this table.
- Work through the phases in order, because each one builds on the ones before it.

**Progress:** count your ✅ in the Learned column → ___ / 191 steps (180 built, 11 still to do)

---

## Phase 0: Tools and basics (before any code)

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 0.1 | ✅ | ⬜ | Install Android Studio | The official IDE for Android. It bundles the code editor, Android SDK, emulator, Gradle integration, layout preview and debugger. |
| 0.2 | ✅ | ⬜ | Android SDK & SDK levels | The SDK is the set of Android libraries and tools. Each Android version has an API level number (Android 8 = 26, Android 16 = 36). You choose which levels your app supports. |
| 0.3 | ✅ | ⬜ | JDK (Java Development Kit) | Kotlin compiles to JVM bytecode, so a JDK is needed to build. Mantis compiles to Java 17 bytecode, and Gradle itself runs on JDK 21 (`gradle/gradle-daemon-jvm.properties`). |
| 0.4 | ✅ | ⬜ | Kotlin language basics | Variables (`val`/`var`), functions, classes, `object` (singleton), `data class`, `sealed class`, null-safety (`?`, `?:`, `?.let`), lambdas, `when`. Every Mantis file uses these. |
| 0.5 | ✅ | ⬜ | Emulator / physical device | Run the app on an Android Virtual Device (AVD) or a real phone with USB debugging enabled. |
| 0.6 | ✅ | ⬜ | Git installed & configured | Version control: tracks every change. `git config user.name` / `user.email` set your identity on commits. |

## Phase 1: Project creation and the build system (Gradle)

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 1.1 | ✅ | ⬜ | New Project → "Empty Activity" (Compose) | Android Studio template that generates the folder structure, Gradle files, `MainActivity` and a Compose theme. This was the starting point. |
| 1.2 | ✅ | ⬜ | Package name / applicationId `com.kush.mantis` | A reverse-domain unique ID. It identifies the app on the Play Store forever and **can never change** after publishing. Set in `app/build.gradle.kts`. |
| 1.3 | ✅ | ⬜ | Project folder structure | `app/src/main/java` = Kotlin code, `app/src/main/res` = resources (icons, colors, strings), `app/src/test` = unit tests, `app/src/androidTest` = on-device tests. |
| 1.4 | ✅ | ⬜ | What Gradle is | The build tool. It downloads libraries, compiles Kotlin, packages resources and produces the APK/AAB. Configured with Kotlin script files (`.kts`). |
| 1.5 | ✅ | ⬜ | Gradle wrapper (`gradlew`, `gradle/wrapper/`) | Pins the exact Gradle version (9.4.1) so everyone builds the project the same way. Run `./gradlew build` with no Gradle installed. |
| 1.6 | ✅ | ⬜ | `settings.gradle.kts` | Names the project ("Mantis"), lists the modules (`:app`) and the repositories libraries come from (Google, Maven Central). |
| 1.7 | ✅ | ⬜ | Root `build.gradle.kts` | Declares the plugins (Android, Kotlin, Compose, Serialization, KSP, Hilt) with `apply false`, meaning "available to modules but not applied here". |
| 1.8 | ✅ | ⬜ | `app/build.gradle.kts` | The app module's build: SDK levels, version, build types, Java version, Compose switch and all dependencies. The most important build file. |
| 1.9 | ✅ | ⬜ | Version catalog `gradle/libs.versions.toml` | One central file for every library and plugin version. Build files refer to them as `libs.xxx` so versions are updated in one place. |
| 1.10 | ✅ | ⬜ | `minSdk 26`, `targetSdk 36`, `compileSdk 36` | minSdk = oldest Android that can install the app (8.0). targetSdk = the Android version whose behaviour rules you follow (16). compileSdk = the API you compile against. |
| 1.11 | ✅ | ⬜ | `versionCode` / `versionName` | versionCode (1) is an integer that must increase with every Play upload. versionName ("1.0.0") is what users see. |
| 1.12 | ✅ | ⬜ | `gradle.properties` | Project-wide settings: memory for the Gradle daemon (`-Xmx2048m`), UTF-8 encoding, AndroidX enabled, official Kotlin style. |
| 1.13 | ✅ | ⬜ | `local.properties` | Machine-specific path to your Android SDK. Git-ignored because it differs on every computer. |
| 1.14 | ✅ | ⬜ | Gradle Sync | Android Studio reads the Gradle files and downloads dependencies. You need to sync after every build-file change. |

## Phase 2: Libraries (dependencies) and what each one does

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 2.1 | ✅ | ⬜ | `implementation` vs `testImplementation` vs `debugImplementation` | Decides where a library is included: the app, unit tests only, on-device tests only, or debug builds only (such as the Compose preview tooling). |
| 2.2 | ✅ | ⬜ | Jetpack Compose + Compose BOM | Compose is Android's modern UI toolkit: you describe UI in Kotlin functions instead of XML. The BOM (Bill of Materials) keeps all Compose library versions compatible. |
| 2.3 | ✅ | ⬜ | Material 3 + Material Icons Extended | Google's design system components (`Card`, `Surface`, `DropdownMenu`, `Scaffold`) and icons (calculator, science, code, swap icons on the bottom bar). |
| 2.4 | ✅ | ⬜ | AndroidX Core KTX, Activity Compose | Kotlin extensions for Android and `setContent {}`, which puts Compose UI into an Activity. |
| 2.5 | ✅ | ⬜ | Lifecycle (runtime, viewmodel-compose) | Gives `ViewModel`, `viewModelScope`, and lets Compose observe state safely with the screen's lifecycle. |
| 2.6 | ✅ | ⬜ | Navigation Compose + Kotlinx Serialization | Moves between screens. Serialization lets routes be type-safe `@Serializable` objects instead of strings. |
| 2.7 | ✅ | ⬜ | Hilt (Dagger) + KSP | Dependency injection library. KSP (Kotlin Symbol Processing) generates Hilt's code at compile time (`ksp(libs.hilt.compiler)`). |
| 2.8 | ✅ | ⬜ | mXparser | Math expression parser. It turns the text `"2+3×sin(30)"` into a number. It does the heavy maths for Basic and Scientific. |
| 2.9 | ✅ | ⬜ | Core SplashScreen | Backport of the Android 12+ splash screen API so the launch screen looks the same on Android 8–16. |
| 2.10 | ✅ | ⬜ | Test libraries | JUnit 4 (tests), kotlinx-coroutines-test (control coroutines in tests), Espresso / Compose UI test (on-device UI tests). |

## Phase 3: App entry point, manifest and resources

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 3.1 | ✅ | ⬜ | `AndroidManifest.xml` | The app's ID card for Android: its Application class, Activities, icon, label, theme and which Activity opens from the launcher (`MAIN` + `LAUNCHER` intent filter). |
| 3.2 | ✅ | ⬜ | No INTERNET permission | The manifest asks for zero permissions, so the app *cannot* go online. This is the basis of the "100% offline & private" promise. |
| 3.3 | ✅ | ⬜ | `MantisApp.kt` (Application class) | Runs once before any screen. `@HiltAndroidApp` starts Hilt's dependency container for the whole app. |
| 3.4 | ✅ | ⬜ | `MainActivity.kt` | The single Activity (screen host). `@AndroidEntryPoint` lets Hilt inject into it, and `setContent {}` starts the Compose UI. |
| 3.5 | ✅ | ⬜ | Single-Activity architecture | One Activity, with every screen as a Compose destination. This is simpler and is the modern recommended approach. |
| 3.6 | ✅ | ⬜ | Activity lifecycle (`onCreate`) | Android calls `onCreate` when the screen is created. The order matters: `installSplashScreen()` → `enableEdgeToEdge()` → `super.onCreate()`. |
| 3.7 | ✅ | ⬜ | mXparser license call | `License.iConfirmNonCommercialUse("Kush")` confirms the free non-commercial license. ⚠ If you ever charge money or add ads, check mXparser's commercial license. |
| 3.8 | ✅ | ⬜ | `res/values/strings.xml` | Holds text such as `app_name` = "Mantis". Keeping text in resources makes translation possible later. |
| 3.9 | ✅ | ⬜ | `res/values/colors.xml` + `values-night/` | XML colors used before Compose loads (splash and icon background). The `-night` folder is used automatically in dark mode. |
| 3.10 | ✅ | ⬜ | `res/values/themes.xml` (XML theme) | `Theme.App.Starting` sets the splash background, splash icon, and the theme to switch to after the splash (`postSplashScreenTheme`). |
| 3.11 | ✅ | ⬜ | Splash screen | `installSplashScreen()` plus the theme shows the Mantis logo while the app starts. (commit "Fix splash icon") |
| 3.12 | ✅ | ⬜ | Adaptive launcher icon | `mipmap-anydpi-v26/ic_launcher.xml` combines a background color, a vector foreground and a **monochrome** layer for Android 13+ themed icons. |
| 3.13 | ✅ | ⬜ | Density buckets (mdpi → xxxhdpi) | PNG icons in 5 sizes so they're sharp on every screen density. Older devices use these, and newer ones use the adaptive XML. |
| 3.14 | ✅ | ⬜ | Vector drawable `ic_launcher_foreground.xml` | The logo as scalable XML paths instead of a bitmap. It stays sharp at any size and the file is tiny. |
| 3.15 | ✅ | ⬜ | Backup rules (`backup_rules.xml`, `data_extraction_rules.xml`) | Control what Android's auto-backup saves. Left as defaults because the app stores no data. |
| 3.16 | ✅ | ⬜ | Portrait lock (`screenOrientation="portrait"`) | Keeps the calculator upright on phones. Note: since Android 16 (target 36), large screens such as tablets **ignore** this lock, which is why the layouts also adapt. |

## Phase 4: Design and theming

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 4.1 | ✅ | ⬜ | Logo design (`design/logo/`) | Wings SVG, a 1024px master and a 512px Play Store icon. The brand green `#22E08A` comes from this logo. |
| 4.2 | ✅ | ⬜ | `ui/theme/Color.kt` | Every brand color as a named constant: Mantis greens, dark and light backgrounds and surfaces, text colors, and orange/blue/red accents. |
| 4.3 | ✅ | ⬜ | `ui/theme/Theme.kt` → `MantisTheme` | Builds a Material 3 `ColorScheme` for light and dark, then wraps the whole app in `MaterialTheme`. Screens use `MaterialTheme.colorScheme.primary` instead of hard-coded colors. |
| 4.4 | ✅ | ⬜ | Color roles (primary, surface, onSurface …) | Material's naming system. `onX` is the text color drawn *on top of* X. Following it keeps contrast readable in both themes. |
| 4.5 | ✅ | ⬜ | Dark mode | `isSystemInDarkTheme()` picks the dark scheme automatically, so theme switches are instant (commit "Make themes instant"). |
| 4.6 | ✅ | ⬜ | Dynamic color (turned off) | Android 12+ can color apps from the wallpaper. It's disabled (`dynamicColor = false`) so the Mantis green branding always shows. |
| 4.7 | ✅ | ⬜ | `ui/theme/Type.kt` (Typography) | Default text styles (font, size, line height). Only `bodyLarge` is customised. |
| 4.8 | ✅ | ⬜ | Units: `dp` vs `sp` | `dp` = density-independent size for layout. `sp` = like dp but also scales with the user's font-size setting, so it's always used for text. |

## Phase 5: Compose UI fundamentals (used everywhere)

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 5.1 | ✅ | ⬜ | `@Composable` functions | Functions that describe UI. When their input state changes, Compose re-runs them ("recomposition") and updates the screen. |
| 5.2 | ✅ | ⬜ | Layouts: `Column`, `Row`, `Box` | Vertical stack, horizontal stack, and overlay. Every Mantis screen is built from these three. |
| 5.3 | ✅ | ⬜ | `Modifier` chain | Changes size, padding, background, click behaviour and more. Order matters: `.padding().background()` ≠ `.background().padding()`. |
| 5.4 | ✅ | ⬜ | `Modifier.weight()` | Shares space proportionally. The display gets `0.40f` and the keypad `0.60f`, and every key is `weight(1f)`, so the grid fills any screen evenly (commits "Enforce equal heights", "Lock mathematical grid"). |
| 5.5 | ✅ | ⬜ | `Arrangement.spacedBy` / `Alignment` | Gaps between children (8.dp between keys) and positioning (results aligned bottom-end, like a real calculator). |
| 5.6 | ✅ | ⬜ | `Surface`, `Card`, shapes | Material containers with color, elevation (shadow) and shape (`RoundedCornerShape(12.dp)`, `CircleShape` for pills). |
| 5.7 | ✅ | ⬜ | `remember` / `mutableStateOf` | Local UI state that survives recomposition, for example whether a dropdown is open (`fromExpanded`) in the Converter. |
| 5.8 | ✅ | ⬜ | `collectAsState()` | Turns a ViewModel `StateFlow` into Compose state, so the UI redraws whenever the value changes. |
| 5.9 | ✅ | ⬜ | `Canvas` drawing | The Basic screen banner draws soft decorative circles with `drawCircle` (commits "Add creative banner", gradient tweaks). |
| 5.10 | ✅ | ⬜ | `Brush.linearGradient` | The diagonal green gradient behind the "M A N T I S" banner, with different colors for light and dark. |
| 5.11 | ✅ | ⬜ | `RowScope` extension composables | `private fun RowScope.DigitKey(...)` creates a helper that can use `Modifier.weight` because it only runs inside a `Row`. |
| 5.12 | ✅ | ⬜ | `CompositionLocal` | Values passed down the UI tree implicitly. Mantis forces `LocalLayoutDirection` to LTR so keypads don't mirror in Arabic or Hebrew. |

## Phase 6: Navigation (moving between the 4 calculators)

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 6.1 | ✅ | ⬜ | Type-safe routes `navigation/Routes.kt` | `@Serializable object BasicRoute` and the others. Using objects instead of strings means typos are compile errors, not crashes. |
| 6.2 | ✅ | ⬜ | `NavHost` + `rememberNavController` (`MantisNavHost.kt`) | NavHost maps each route to its screen (`composable<BasicRoute> { BasicScreen() }`). The NavController performs the navigation. |
| 6.3 | ✅ | ⬜ | `Scaffold` with `bottomBar` | Material layout skeleton that places the bottom bar and gives `innerPadding` so content doesn't hide under it. |
| 6.4 | ✅ | ⬜ | Floating bottom bar (`BottomNavBar.kt`) | Pill-shaped bar with 4 icons. The selected one is tinted green. Built by hand instead of the default NavigationBar for a custom look (commit "Add floating taskbar"). |
| 6.5 | ✅ | ⬜ | Tab navigation options | `popUpTo(BasicRoute){saveState}`, `launchSingleTop`, `restoreState`: switching tabs doesn't pile up a back stack and each tab keeps its state. |
| 6.6 | ✅ | ⬜ | Directional slide animations | Compares route indexes to slide left or right depending on whether you move to a tab on the right or the left (300ms `tween`) (commit "Add bottom animations"). |
| 6.7 | ✅ | ⬜ | Removing features | History, settings and about screens were built early and later deleted to keep the app focused (commit "Remove unused features"). Removing code is part of the work too. |

## Phase 7: Architecture (how the code is organised)

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 7.1 | ✅ | ⬜ | Feature-based packages | `features/basic`, `scientific`, `programmer`, `converter`, each with its own `domain/` and `presentation/`. `core/` holds shared code. |
| 7.2 | ✅ | ⬜ | Clean Architecture layers | **Presentation** (Screens + ViewModels) → **Domain** (pure logic: use cases, converters). Domain has no Android UI code, so it's easy to test. |
| 7.3 | ✅ | ⬜ | MVVM pattern | **Model** = domain logic, **View** = Composable screen, **ViewModel** = holds the screen's state and handles user actions. The View only displays and forwards clicks. |
| 7.4 | ✅ | ⬜ | ViewModel survives rotation | A ViewModel outlives configuration changes (rotation, dark-mode switch), so the typed expression isn't lost. |
| 7.5 | ✅ | ⬜ | `StateFlow` / `MutableStateFlow` | Observable state holders. Private `_expression` (mutable) is exposed as public read-only `expression` so only the ViewModel can change state. |
| 7.6 | ✅ | ⬜ | Events as a `sealed class` | `BasicCalcEvent.OnNumberClick`, `OnEqualsClick` and so on. The UI sends one event type to `onEvent()`, and `when` must handle every case. |
| 7.7 | ✅ | ⬜ | Unidirectional Data Flow (UDF) | State flows **down** (ViewModel → UI), events flow **up** (UI → ViewModel). There's one source of truth and no surprises. |
| 7.8 | ✅ | ⬜ | Use Cases (`EvaluateExpressionUseCase`, `ConvertUnitUseCase`) | One class = one business action. `operator fun invoke` lets you call it like a function: `convertUnitUseCase(...)`. |
| 7.9 | ✅ | ⬜ | Dependency Injection with Hilt | Classes ask for dependencies in their constructor (`@Inject constructor`) and Hilt creates and supplies them. Nobody calls `new` by hand. |
| 7.10 | ✅ | ⬜ | `@HiltViewModel` + `hiltViewModel()` | Hilt builds the ViewModel with its use case and SavedStateHandle already injected. Screens get it with `viewModel: BasicViewModel = hiltViewModel()`. |
| 7.11 | ✅ | ⬜ | Hilt module `core/di/AppModule.kt` | Tells Hilt how to build things it can't construct alone: here, which `CoroutineDispatcher` to use (`Dispatchers.Default`). Tests swap in their own. |
| 7.12 | ✅ | ⬜ | `SavedStateHandle` (process death) | Android may kill a backgrounded app to free memory. Saving state here restores the expression and mode when the user returns. |
| 7.13 | ✅ | ⬜ | Coroutines & `viewModelScope` | `viewModelScope.launch {}` runs async work that's cancelled automatically when the ViewModel dies. |
| 7.14 | ✅ | ⬜ | Background thread `withContext(dispatcher)` | Evaluation runs off the main (UI) thread, so a slow expression can never freeze the app (commit "fix freeze"). |

## Phase 8: Shared components and utilities (`core/`)

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 8.1 | ✅ | ⬜ | `CalcButton` (reusable key) | One composable for every key in all 4 modes: rounded surface, colors, enabled/disabled state, press animation, haptics and accessibility label. |
| 8.2 | ✅ | ⬜ | Press-scale animation | `collectIsPressedAsState()` → scale to 0.95 while held, giving a "pop" feel without a ripple (commit "Fix pop animations"). |
| 8.3 | ✅ | ⬜ | Haptic feedback (`HapticHelper.kt`) | A small vibration (`KEYBOARD_TAP`) on every key press and tab change (commit "Enhance haptic feedback"). |
| 8.4 | ✅ | ⬜ | `DisplayPanel` | Shared two-card display: **Input** (editable text field with cursor) on top, **Output** (result) below. Used by Basic, Scientific and Programmer. |
| 8.5 | ✅ | ⬜ | `TextFieldValue` (text + cursor) | Stores the text *and* the cursor/selection position, so users can tap into the middle of an expression and edit there. |
| 8.6 | ✅ | ⬜ | `DisableSoftKeyboard` | Intercepts text input so the phone's keyboard never pops up over the calculator keypad while the cursor still works (commit "Disable software keyboard"). |
| 8.7 | ✅ | ⬜ | Copy / paste | `BasicTextField` allows selecting and pasting into Input, and `SelectionContainer` makes the result copyable (commit "Add copy paste"). |
| 8.8 | ✅ | ⬜ | `AutoSizeText` | Single-line text that shrinks by 10% steps until it fits (`onTextLayout` + `hasVisualOverflow`). Long results and big system fonts never overflow. |
| 8.9 | ✅ | ⬜ | `PillSelector` | Rounded segmented control (HEX/DEC/OCT/BIN, Length/Weight/Temp/Base). Uses `selectable` with `Role.Tab` for accessibility (commit "Add unified selectors"). |
| 8.10 | ✅ | ⬜ | `isCompactHeight()` | True when the window is under 600dp tall (split-screen). Screens then hide the banner and labels so keys stay usable. |
| 8.11 | ✅ | ⬜ | `TopHeader` (currently unused) | A title-row component left over from earlier designs. Nothing calls it now, so it's a candidate for cleanup (see Phase 17). |
| 8.12 | ✅ | ⬜ | `CursorUtil` | Pure functions to insert/delete at the cursor, clear, and `canInsertDecimal` (blocks a second "." in the same number). Handles backwards selections with `min/max`. |
| 8.13 | ✅ | ⬜ | `NumberFormatter.formatResult` | Rounds to 15 significant digits (removes floating-point noise like 0.1+0.2=0.30000000000000004) and adds grouping commas (`1,234`). |
| 8.14 | ✅ | ⬜ | Scientific notation | Values ≥ 1e15 or < 1e-6 display as `1.5E20`, so huge or tiny numbers stay readable. |
| 8.15 | ✅ | ⬜ | `NumberFormatter.toExpression` | The same number *without* commas, at full precision, so a result can be fed back into the parser for the next calculation. |
| 8.16 | ✅ | ⬜ | `BigDecimal` & `MathContext` | Exact decimal arithmetic used for rounding and formatting. `Double` alone can't represent most decimals exactly. |

## Phase 9: Basic calculator

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 9.1 | ✅ | ⬜ | `BasicScreen` layout | Display (40%) + banner + 4×5 key grid (60%). Numbers in the default color, operators green, C red, ⌫ orange, = filled green. |
| 9.2 | ✅ | ⬜ | Creative banner | "M A N T I S / PRECISION CALCULATOR" card with a gradient and circles, hidden in split-screen. It went through several design iterations (solid, diagonal, muted). |
| 9.3 | ✅ | ⬜ | `BasicViewModel` state | `expression` (TextFieldValue) + `result` (String), both restored from SavedStateHandle. |
| 9.4 | ✅ | ⬜ | Typing numbers & operators | `OnNumberClick` / `OnOperatorClick` → `CursorUtil.insertText` at the cursor position. |
| 9.5 | ✅ | ⬜ | Clear (C) and Delete (⌫) | C empties input and result. ⌫ removes the character before the cursor (or the selected text). |
| 9.6 | ✅ | ⬜ | Evaluate on "=" only | The result appears only when "=" is pressed, not while typing (commit "Evaluate on equals"). |
| 9.7 | ✅ | ⬜ | `EvaluateExpressionUseCase` | Replaces display symbols (`×`→`*`, `÷`→`/`), auto-closes brackets, rewrites factorials, then asks mXparser to calculate. |
| 9.8 | ✅ | ⬜ | Auto-close brackets | `"sin(30"` is treated as `"sin(30)"`: missing `)` are added at the end. |
| 9.9 | ✅ | ⬜ | Error handling | NaN, Infinity or invalid input → shows "Error" instead of a wrong or blank answer (commit "Handle NaN inputs"). |
| 9.10 | ✅ | ⬜ | Expression length limit (200) | Long chains slow mXparser down, so input and paste beyond 200 characters are rejected to keep it under a second. |
| 9.11 | ✅ | ⬜ | Result chaining (`justEvaluated`) | After "=": typing a digit starts fresh, but typing an operator continues from the answer (`= 5`, then `+2`) (commit "Chain evaluated results"). |
| 9.12 | ✅ | ⬜ | Stale-result guard | If the user edits while a calculation is running, the old answer is discarded (`if (_expression.value.text != text) return`). |
| 9.13 | ✅ | ⬜ | Thread safety (`synchronized(LOCK)`) | mXparser's degree/radian mode is global, so evaluations are locked to stop Basic and Scientific clashing. |

## Phase 10: Scientific calculator

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 10.1 | ✅ | ⬜ | `ScientificScreen` layout | Two rows of function keys above the same number pad as Basic. |
| 10.2 | ✅ | ⬜ | Function map `ScientificFunctions.kt` | Maps key labels to parser text: `log`→`log10(`, `³√`→`root(3, `, `x²`→`^2`, `π`→`pi`. The UI stays pretty and the parser gets valid syntax. |
| 10.3 | ✅ | ⬜ | "2nd" key (second functions) | Toggles keys to their alternates: sin→sin⁻¹, xʸ→n!, √→³√, ln→eˣ, log→10ˣ, π→e. The key turns green while active. |
| 10.4 | ✅ | ⬜ | DEG / RAD toggle | Chooses whether trig uses degrees or radians, passed to `mXparser.setDegreesMode()` / `setRadiansMode()`. |
| 10.5 | ✅ | ⬜ | Inverse trig, logs, roots, powers, π, e, \|x\| | All evaluated by mXparser through the function map. |
| 10.6 | ✅ | ⬜ | Smart chaining (`continuesExpression`) | After "=", inputs starting with `+ - × ÷ ^ ! )` extend the answer, and anything else (a number, `sin(`) starts fresh. |
| 10.7 | ✅ | ⬜ | Custom factorial `FactorialFunction.kt` | mXparser's built-in `!` gave wrong decimals (2.5! = 6). Mantis registers its own `fact()`: exact multiplication for whole numbers, **Gamma(x+1)** for decimals (2.5! = 3.3233…). |
| 10.8 | ✅ | ⬜ | Postfix rewrite `rewritePostfix` | Converts `5!` → `fact(5)`, `sin(30)!` → `fact(sin(30))`, `2pi!` → `2*fact(pi)` by scanning backwards to find the operand. |
| 10.9 | ✅ | ⬜ | Factorial limits | Negative whole numbers → Error (undefined). Above 170! → Infinity → Error (larger values don't fit in a Double). |
| 10.10 | ✅ | ⬜ | `FunctionExtension` interface | How you plug a custom function into mXparser: parameter count, set values, `calculate()`, `clone()`. |

## Phase 11: Programmer calculator

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 11.1 | ✅ | ⬜ | `ProgrammerScreen` layout | Base pill selector, A–F hex row, digits, bitwise operators (<<, >>, XOR, NOT, AND, OR), C, ⌫, =. The display is read-only. |
| 11.2 | ✅ | ⬜ | Number bases | HEX (16), DEC (10), OCT (8), BIN (2). The same value is shown in a different base when you switch. |
| 11.3 | ✅ | ⬜ | `BaseConverter.kt` | `convert(Long, base)` and `parse(String, base)` use Kotlin's `toString(radix)` / `toULongOrNull(radix)`. |
| 11.4 | ✅ | ⬜ | Disabled invalid digits | In BIN only 0/1 are enabled, in OCT 0–7, and so on (`isValidDigit` → `CalcButton(enabled = …)`). |
| 11.5 | ✅ | ⬜ | 64-bit values (`Long`) | All maths uses 64-bit integers. Digits that would overflow are ignored instead of producing garbage. |
| 11.6 | ✅ | ⬜ | Two's complement | DEC shows signed numbers, and HEX/OCT/BIN show the raw bit pattern (`NOT 0` = `FFFFFFFFFFFFFFFF`). This is how real CPUs store negatives. |
| 11.7 | ✅ | ⬜ | Bitwise operations | AND, OR, XOR, NOT, shift left/right using Kotlin's `and`, `or`, `xor`, `inv()`, `shl`, `shr`. |
| 11.8 | ✅ | ⬜ | Shift clamping | Kotlin only uses the low 6 bits of a shift distance (`1 shl 64 == 1`!), so shifts ≥ 64 are handled explicitly. |
| 11.9 | ✅ | ⬜ | Calculator state machine | `accumulator` (left value), `pendingOperator`, `operand`, `operandTyped`, `replaceOnNextDigit`. Together these track "12 AND _" like a real calculator. |
| 11.10 | ✅ | ⬜ | Chained operations | `5 AND 3 OR 1` evaluates left to right: the pending op is computed before the next one is applied. |
| 11.11 | ✅ | ⬜ | Smart delete | ⌫ right after an operator removes the operator and goes back to editing the left number. |
| 11.12 | ✅ | ⬜ | Saving the whole state | All 8 state variables go into SavedStateHandle in `refresh()`, so even a half-finished operation survives process death. |

## Phase 12: Unit converter

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 12.1 | ✅ | ⬜ | `ConverterScreen` layout | Input and Output cards, category pills, From ▼ / To ▼ dropdown bar, keypad. The keypad changes for the Base category (hex digits). |
| 12.2 | ✅ | ⬜ | `UnitDefinitions.kt` + `data class UnitDefinition` | Each unit = name, symbol, factor to the base unit (meter or gram). Uses exact international definitions (1 in = 0.0254 m, 1 lb = 453.59237 g). |
| 12.3 | ✅ | ⬜ | Conversion formula | `value × from.toBase ÷ to.toBase`: convert to the base unit, then to the target. One formula covers every length and weight pair. |
| 12.4 | ✅ | ⬜ | Temperature special case | Not a simple multiply: it goes through Celsius using offsets (°F = C×9/5+32, K = C+273.15). |
| 12.5 | ✅ | ⬜ | Absolute zero check | Anything below −273.15 °C shows "Error" because nothing physical can be colder. |
| 12.6 | ✅ | ⬜ | Base category | Reuses `BaseConverter` from the Programmer feature, which is code reuse across features. Changing the From base re-expresses the same number (DEC 255 → HEX FF). |
| 12.7 | ✅ | ⬜ | Live updating | `recalculate()` runs after every event, so there's no "=" key and the output follows the input instantly (commit "Fix converter equals"). |
| 12.8 | ✅ | ⬜ | Input rules | ± sign toggle, a single decimal point, a 15-digit limit, leading-zero replacement, and ⇅ swap units. |
| 12.9 | ✅ | ⬜ | `DropdownMenu` | Material menu for choosing units. Its open/closed state is local Compose `remember` state, not ViewModel state, because it's purely visual. |

## Phase 13: Polish, accessibility and adaptive layout

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 13.1 | ✅ | ⬜ | Edge-to-edge | `enableEdgeToEdge()` draws behind the transparent status and navigation bars, and `navigationBarsPadding()` keeps the bottom bar above the gesture handle. Required on Android 15+. |
| 13.2 | ✅ | ⬜ | Screen-reader labels (TalkBack) | `spokenLabels` makes "÷" read as "Divide", "⌫" as "Delete", and so on. Also uses `Role.Button` / `Role.Tab` semantics. |
| 13.3 | ✅ | ⬜ | Large font support | `AutoSizeText` everywhere means text shrinks instead of breaking the layout when the user sets a huge system font. |
| 13.4 | ✅ | ⬜ | Split-screen / tablets | Weight-based grids + `isCompactHeight()` let the UI work in any window size. |
| 13.5 | ✅ | ⬜ | LTR lock for RTL languages | The app supports RTL (`supportsRtl`), but calculator content stays left-to-right, as on physical calculators. |
| 13.6 | ✅ | ⬜ | Layout tuning per mode | Many small commits ("Fix basic sizes", "Fix grid padding", "Rearrange keyboards") making all four screens visually consistent. |
| 13.7 | ✅ | ⬜ | Lint fixes + `lint.xml` | Android Lint warns about bugs and bad practice. Fix the warnings, and suppress (with a comment) only the deliberate ones (commit "Fix lint warnings"). |

## Phase 14: Testing

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 14.1 | ✅ | ⬜ | Unit tests vs instrumented tests | `src/test` runs on your computer's JVM (fast). `src/androidTest` runs on a device or emulator (slower, needed for real UI). |
| 14.2 | ✅ | ⬜ | JUnit basics | `@Test` functions, `@Before` setup, `assertEquals(expected, actual)`. Backtick names like `` `large result can be reused` `` read as sentences. |
| 14.3 | ✅ | ⬜ | Domain tests | `EvaluateExpressionUseCaseTest`, `FactorialFunctionTest`, `BaseConverterTest`, `ConvertUnitUseCaseTest` test the pure maths logic in isolation. |
| 14.4 | ✅ | ⬜ | Utility tests | `CursorUtilTest`, `NumberFormatterTest` cover cursor editing and number formatting edge cases. |
| 14.5 | ✅ | ⬜ | ViewModel tests | `Basic/Scientific/Programmer/ConverterViewModelTest` simulate key presses (`type("1000+234")`, `equals()`) and check the displayed result. |
| 14.6 | ✅ | ⬜ | `MainDispatcherRule` | A JUnit rule that swaps Android's main thread for a test dispatcher so `viewModelScope` code runs instantly in tests. |
| 14.7 | ✅ | ⬜ | Why DI makes testing easy | Tests construct `EvaluateExpressionUseCase(Dispatchers.Unconfined)` and `SavedStateHandle()` directly. Constructor injection makes dependencies swappable. |
| 14.8 | ✅ | ⬜ | Running tests | `./gradlew test` from the terminal, or the green ▶ next to a test in Android Studio. |
| 14.9 | ✅ | ⬜ | Bug → test → fix cycle | October commits fixed real defects (result chaining with commas, freezes, factorial). Each fix came with a test so the bug can't silently return. |

## Phase 15: Git and GitHub

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 15.1 | ✅ | ⬜ | `git init`, add, commit | Save snapshots of the project. Mantis has 84 commits from 4 Aug to 25 Oct 2026. |
| 15.2 | ✅ | ⬜ | Good commit messages | Short, imperative, specific: "Add copy paste", "Fix converter equals". Read `git log --oneline` to see the project's whole story. |
| 15.3 | ✅ | ⬜ | `.gitignore` | Keeps build output, IDE files, `local.properties`, and **keystores/passwords** out of the repo. Secrets must never be committed. |
| 15.4 | ✅ | ⬜ | GitHub remote & push | The code lives at `github.com/redrighthand2007/MANTIS`. `git push` uploads commits, and the remote doubles as a backup and portfolio. |
| 15.5 | ✅ | ⬜ | README.md | The project's front page: screenshots, features, tech stack, structure, install steps. Written in Markdown (tables, code blocks, images). |
| 15.6 | ✅ | ⬜ | LICENSE | States how others may use the code. Without a license, nobody may legally reuse it. |
| 15.7 | ✅ | ⬜ | Repo cleanup | Removing old docs, templates and junk resources (Sept commits) keeps the repo professional and easy to read. |

## Phase 16: Release and Play Store

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 16.1 | ✅ | ⬜ | Debug vs release build types | Debug = fast, debuggable, unsigned with a debug key. Release = optimised, signed and uploadable. |
| 16.2 | ✅ | ⬜ | R8 shrinking & obfuscation | `isMinifyEnabled` + `isShrinkResources` remove unused code and resources: the APK went from ~11.7 MB to ~1.5 MB. |
| 16.3 | ✅ | ⬜ | `proguard-rules.pro` | Rules telling R8 what *not* to remove or rename (needed for reflection-based libraries). Currently defaults. |
| 16.4 | ✅ | ⬜ | App signing keystore (`.jks`) | Your digital signature proving updates come from you. **Lose it and you can't update the app.** Back it up safely. |
| 16.5 | ✅ | ⬜ | `keystore.properties` + example file | Passwords live in a git-ignored file read by Gradle. The `.example` file shows the format without real secrets. |
| 16.6 | ✅ | ⬜ | Building an AAB | `./gradlew bundleRelease` produces the Android App Bundle that Google Play requires (Play generates device-specific APKs from it). |
| 16.7 | ✅ | ⬜ | Target API 36 | Google Play requires a recent targetSdk for new apps and updates. Raising it means testing the new platform behaviours (commit "Target API 36"). |
| 16.8 | ✅ | ⬜ | Privacy policy `docs/privacy-policy.md` | Required by Play for every app. States that Mantis collects nothing, which matches the no-internet manifest. It must be hosted at a public URL. |
| 16.9 | ✅ | ⬜ | Store listing `docs/play-store-listing.md` | App name (30 chars), short description (80), full description (4000). Prepared in advance with character counts. |
| 16.10 | ✅ | ⬜ | Store assets `docs/store-assets/` | 5 phone screenshots (each mode + dark) and a 1024×500 feature graphic. The 512px Play icon is in `design/logo/wings_playstore_512.png`. |
| 16.11 | ⬜ | ⬜ | Play Console forms | Data safety ("no data collected"), content rating questionnaire, target audience, app category. These are filled in Play Console when you create the app there, and they're your next release step. |

## Phase 17: Next steps (not done yet, your learning roadmap)

| # | Done | Learned | Step / Feature | Explanation |
|---|:---:|:---:|---|---|
| 17.1 | ⬜ | ⬜ | Google Play developer account + closed testing | One-time fee. New personal accounts must run a closed test with testers for 14 days before production access. |
| 17.2 | ⬜ | ⬜ | Production release & staged rollout | Publish to 10% → 50% → 100% of users while watching crash reports in Play Console vitals. |
| 17.3 | ⬜ | ⬜ | Continuous Integration (GitHub Actions) | A `.github/workflows` file that runs `./gradlew test lint` on every push, so broken code is caught automatically. |
| 17.4 | ⬜ | ⬜ | Compose UI tests | Use `createComposeRule()` to tap keys on screen and assert the displayed result (the `ui-test-junit4` dependency is already added). |
| 17.5 | ⬜ | ⬜ | Clean leftovers | Delete unused `TopHeader`, the unused `baseExpanded`/`categoryExpanded` variables, the unused `@Inject` import in MainActivity, the duplicate `include(":app")` in `settings.gradle.kts`, and the example tests. |
| 17.6 | ⬜ | ⬜ | Haptics setting | `isHapticEnabled` is hard-coded `true`. Add a settings toggle saved with **DataStore**. |
| 17.7 | ⬜ | ⬜ | Strings into `strings.xml` | Move UI text ("Input", "Output", key labels) to resources to allow translations. |
| 17.8 | ⬜ | ⬜ | Feature branches & pull requests | Work on `feature/xyz` branches and merge through PRs instead of committing directly to `main`. |
| 17.9 | ⬜ | ⬜ | Tagged releases + CHANGELOG.md | `git tag v1.0.0` per release and a changelog listing what changed in each version. |
| 17.10 | ⬜ | ⬜ | Calculation history | A new feature idea: store past calculations locally with **Room** (Android's SQLite database library). |
