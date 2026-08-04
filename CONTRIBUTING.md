# Contributing to Mantis Calculator

Thank you for your interest in contributing! 🦗

## How to Contribute

### 🐛 Reporting Bugs
1. Check if the bug has already been reported in [Issues](../../issues)
2. Open a new issue using the **Bug Report** template
3. Include steps to reproduce, expected vs actual behavior

### 💡 Suggesting Features
1. Open a new issue using the **Feature Request** template
2. Explain the feature and why it would be useful

### 🔧 Submitting Code
1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Make your changes following the project structure
4. Test on an emulator or physical device
5. Commit: `git commit -m "Add amazing feature"`
6. Push: `git push origin feature/amazing-feature`
7. Open a Pull Request

## Code Guidelines

- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use the existing MVVM architecture pattern
- Place new features in `features/<feature_name>/` with `domain/` and `presentation/` subdirectories
- Reuse `CalcButton` and `DisplayPanel` components where possible
- Write meaningful commit messages

## Project Structure

```
features/<name>/
├── domain/        # Business logic, use cases
├── data/          # Data sources, repositories (if needed)
└── presentation/  # Screen composables + ViewModel
```

## Questions?

Feel free to open an issue with the **Question** label.
