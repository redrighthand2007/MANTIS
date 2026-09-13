import re

with open('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterScreen.kt', 'r', encoding='utf-8') as f:
    content = f.read()

upper_display_replacement = '''        // Upper Display Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.35f)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Top Box (Input Value)
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    Text(
                        text = "Input",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                        Text(
                            text = inputValue.ifEmpty { "0" },
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))

            // Bottom Box (Output Value)
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    Text(
                        text = "Output",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                        Text(
                            text = outputValue.ifEmpty { "0" },
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                        )
                    }
                }
            }
        }'''

# Replace Upper Display Area
content = re.sub(r'\s*// Upper Display Area\s*Column\(.*?(?=\s*// PillSelector & Keypad Area)', '\n' + upper_display_replacement + '\n\n', content, flags=re.DOTALL)


pill_and_floating_bar = '''        // PillSelector & Keypad Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.65f)
        ) {
            com.kush.mantis.core.ui.components.PillSelector(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 12.dp),
                items = com.kush.mantis.features.converter.domain.UnitDefinitions.Categories.keys.toList(),
                selectedItem = category,
                onItemSelected = { selected ->
                    viewModel.onEvent(ConverterEvent.SetCategory(selected))
                }
            )

            // NEW Unit Selectors Bar
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 12.dp),
                shape = androidx.compose.foundation.shape.CircleShape,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
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
                            units.forEach { u ->
                                DropdownMenuItem(text = { Text(u.name) }, onClick = { viewModel.onEvent(ConverterEvent.SetFromUnit(u)); fromExpanded = false })
                            }
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
                            units.forEach { u ->
                                DropdownMenuItem(text = { Text(u.name) }, onClick = { viewModel.onEvent(ConverterEvent.SetToUnit(u)); toExpanded = false })
                            }
                        }
                    }
                }
            }

            // Keypad Area'''

content = re.sub(r'\s*// PillSelector & Keypad Area\s*Column\(\s*modifier = Modifier\s*\.fillMaxWidth\(\)\s*\.weight\(0\.65f\)\s*\)\s*\{\s*com\.kush\.mantis\.core\.ui\.components\.PillSelector\(.*?\}\s*\)\s*// Keypad Area', '\n' + pill_and_floating_bar, content, flags=re.DOTALL)

with open('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterScreen.kt', 'w', encoding='utf-8') as f:
    f.write(content)
