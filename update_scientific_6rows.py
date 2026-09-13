import codecs
import re

with codecs.open('app/src/main/java/com/kush/mantis/features/scientific/presentation/ScientificScreen.kt', 'r', 'utf-8') as f:
    content = f.read()

match_pattern = r'\s*Column\(\s*modifier = Modifier\s*\.fillMaxWidth\(\)\s*\.weight\(0\.65f\).*'

new_code = '''        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.65f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val rowModifier = Modifier.weight(1f)

            // Row 1 (Scientific Functions 1)
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton(if (isDegreeMode) "DEG" else "RAD", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.ToggleAngleMode) }
                CalcButton("sin", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("sin(")) }
                CalcButton("cos", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("cos(")) }
                CalcButton("tan", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("tan(")) }
                CalcButton("x^y", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("^")) }
            }

            // Row 2 (Scientific Functions 2)
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("√", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("√(")) }
                CalcButton("ln", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("ln(")) }
                CalcButton("log", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("log10(")) }
                CalcButton("π", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("π")) }
                CalcButton("e", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("e")) }
            }

            // Row 3
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("7", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("7")) }
                CalcButton("8", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("8")) }
                CalcButton("9", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("9")) }
                CalcButton("(", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("(")) }
                CalcButton(")", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput(")")) }
            }
            // Row 4
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("4", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("4")) }
                CalcButton("5", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("5")) }
                CalcButton("6", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("6")) }
                CalcButton("+", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("+")) }
                CalcButton("-", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("-")) }
            }
            // Row 5
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("1", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("1")) }
                CalcButton("2", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("2")) }
                CalcButton("3", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("3")) }
                CalcButton("÷", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("÷")) }
                CalcButton("×", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("×")) }
            }
            // Row 6
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("C", Modifier.weight(1f), textColor = AccentRed) { viewModel.onEvent(ScientificEvent.OnClear) }
                CalcButton("0", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("0")) }
                CalcButton(".", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput(".")) }
                CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ScientificEvent.OnDelete) }
                CalcButton("=", Modifier.weight(1f), color = MantisGreen, textColor = Color.Black) { viewModel.onEvent(ScientificEvent.OnEquals) }
            }
        }
    }
}
'''

content = re.sub(match_pattern, '\n' + new_code, content, flags=re.DOTALL)

with codecs.open('app/src/main/java/com/kush/mantis/features/scientific/presentation/ScientificScreen.kt', 'w', 'utf-8') as f:
    f.write(content)
