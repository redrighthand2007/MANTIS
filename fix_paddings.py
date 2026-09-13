import os
import re

def fix_display_panel():
    filepath = 'app/src/main/java/com/kush/mantis/core/ui/components/DisplayPanel.kt'
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # 1. Change padding and Arrangement
    content = content.replace('.padding(16.dp)', '.padding(horizontal = 16.dp, vertical = 8.dp)', 1)
    content = content.replace('verticalArrangement = Arrangement.SpaceEvenly', 'verticalArrangement = Arrangement.spacedBy(8.dp)')
    
    # 2. Remove Spacer between cards
    content = re.sub(r'\s*Spacer\(modifier = Modifier\.height\(8\.dp\)\)', '', content)
    
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

def fix_screen(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # 1. Remove the top Spacer(16.dp)
    content = re.sub(r'\s*Spacer\(modifier = Modifier\.height\(16\.dp\)\)', '', content)
    
    # 2. Fix keypad padding. The Keypad column padding is usually .padding(16.dp) or similar
    # We will just replace `.padding(16.dp)` with `.padding(horizontal = 16.dp, vertical = 8.dp)` globally in these files
    # except we need to be careful not to break the Box inner paddings.
    # Actually, Box inner padding for Text is usually `.padding(16.dp)`. Let's be specific.
    # We want to replace `.padding(16.dp)` when it's part of the Column modifier.
    
    # In screens, the keypad Column is typically:
    # .weight(0.60f)
    # .padding(16.dp)
    # We replace `.weight(0.60f)\n                .padding(16.dp)` with `.weight(0.60f)\n                .padding(horizontal = 16.dp, vertical = 8.dp)`
    content = content.replace('.weight(0.60f)\n                .padding(16.dp)', '.weight(0.60f)\n                .padding(horizontal = 16.dp, vertical = 8.dp)')
    content = content.replace('.weight(0.60f)\n            .padding(16.dp)', '.weight(0.60f)\n            .padding(horizontal = 16.dp, vertical = 8.dp)')
    
    # For ConverterScreen Upper Display Area
    if 'ConverterScreen' in filepath:
        content = content.replace('.weight(0.40f)\n                .padding(16.dp)', '.weight(0.40f)\n                .padding(horizontal = 16.dp, vertical = 8.dp)')
        content = content.replace('verticalArrangement = Arrangement.SpaceEvenly', 'verticalArrangement = Arrangement.spacedBy(8.dp)')
        content = re.sub(r'\s*Spacer\(modifier = Modifier\.height\(8\.dp\)\)', '', content)

    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

fix_display_panel()
fix_screen('app/src/main/java/com/kush/mantis/features/basic/presentation/BasicScreen.kt')
fix_screen('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterScreen.kt')
fix_screen('app/src/main/java/com/kush/mantis/features/scientific/presentation/ScientificScreen.kt')
fix_screen('app/src/main/java/com/kush/mantis/features/programmer/presentation/ProgrammerScreen.kt')

print("Padding fixed successfully.")
