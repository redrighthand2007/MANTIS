import codecs
import re

with codecs.open('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterScreen.kt', 'r', 'utf-8') as f:
    content = f.read()

match_pattern = r'\s*// PillSelector, Floating Bar & Keypad Area\s*Column.*'

new_code = '''        // PillSelector, Floating Bar & Keypad Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.65f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val rowModifier = Modifier.weight(1f)
            val disabledColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)

            // Row 1: PillSelector
            Box(modifier = rowModifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                com.kush.mantis.core.ui.components.PillSelector(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    items = com.kush.mantis.features.converter.domain.UnitDefinitions.Categories.keys.toList(),
                    selectedItem = category,
                    onItemSelected = { selected -> viewModel.onEvent(ConverterEvent.SetCategory(selected)) }
                )
            }

            // Row 2: Unit Selectors Bar
            Box(modifier = rowModifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    shape = androidx.compose.foundation.shape.CircleShape,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // From Unit
                        Box(
                            modifier = Modifier.weight(1f).fillMaxHeight().clickable { fromExpanded = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "${fromUnit.name} ▼", color = MantisGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            DropdownMenu(expanded = fromExpanded, onDismissRequest = { fromExpanded = false }, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
                                units.forEach { u -> DropdownMenuItem(text = { Text(u.name) }, onClick = { viewModel.onEvent(ConverterEvent.SetFromUnit(u)); fromExpanded = false }) }
                            }
                        }

                        // Divider
                        Box(modifier = Modifier.width(1.dp).fillMaxHeight(0.6f).background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)))

                        // To Unit
                        Box(
                            modifier = Modifier.weight(1f).fillMaxHeight().clickable { toExpanded = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "${toUnit.name} ▼", color = MantisGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            DropdownMenu(expanded = toExpanded, onDismissRequest = { toExpanded = false }, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
                                units.forEach { u -> DropdownMenuItem(text = { Text(u.name) }, onClick = { viewModel.onEvent(ConverterEvent.SetToUnit(u)); toExpanded = false }) }
                            }
                        }
                    }
                }
            }

            // Row 3
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("7", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("7")) }
                CalcButton("8", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("8")) }
                CalcButton("9", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("9")) }
                CalcButton("(", Modifier.weight(1f), textColor = disabledColor) {}
                CalcButton(")", Modifier.weight(1f), textColor = disabledColor) {}
            }
            // Row 4
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("4", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("4")) }
                CalcButton("5", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("5")) }
                CalcButton("6", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("6")) }
                CalcButton("+", Modifier.weight(1f), textColor = disabledColor) {}
                CalcButton("-", Modifier.weight(1f), textColor = disabledColor) {}
            }
            // Row 5
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("1", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("1")) }
                CalcButton("2", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("2")) }
                CalcButton("3", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("3")) }
                CalcButton("÷", Modifier.weight(1f), textColor = disabledColor) {}
                CalcButton("×", Modifier.weight(1f), textColor = disabledColor) {}
            }
            // Row 6
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("C", Modifier.weight(1f), textColor = AccentRed) { viewModel.onEvent(ConverterEvent.OnClear) }
                CalcButton("0", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("0")) }
                CalcButton(".", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput(".")) }
                CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ConverterEvent.OnDelete) }
                CalcButton("=", Modifier.weight(1f), color = MantisGreen, textColor = Color.Black) {}
            }
        }
    }
}
'''

content = re.sub(match_pattern, '\n' + new_code, content, flags=re.DOTALL)

with codecs.open('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterScreen.kt', 'w', 'utf-8') as f:
    f.write(content)
