import codecs
import re

with codecs.open('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterScreen.kt', 'r', 'utf-8') as f:
    content = f.read()

match_pattern = r'\s*// Upper Display Area.*?// Keypad Area'

new_middle = '''
        // Upper Display Area
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
        }

        // PillSelector, Floating Bar & Keypad Area
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

content = re.sub(match_pattern, '\n' + new_middle, content, flags=re.DOTALL)

with codecs.open('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterScreen.kt', 'w', 'utf-8') as f:
    f.write(content)
