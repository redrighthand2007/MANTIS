import re

with open('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterScreen.kt', 'r', encoding='utf-8') as f:
    content = f.read()

# Update the '=' button lambda
content = content.replace('CalcButton("=", Modifier.weight(1f), color = MantisGreen, textColor = Color.Black) {}', 'CalcButton("=", Modifier.weight(1f), color = MantisGreen, textColor = Color.Black) { viewModel.onEvent(ConverterEvent.OnEquals) }')

with open('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterScreen.kt', 'w', encoding='utf-8') as f:
    f.write(content)
