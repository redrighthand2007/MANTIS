import re

with open('app/src/main/java/com/kush/mantis/features/programmer/presentation/ProgrammerViewModel.kt', 'r', encoding='utf-8') as f:
    content = f.read()

# Remove evaluateLive calls
content = re.sub(r'\s*evaluateLive\(\)', '', content)

# Remove evaluateLive private function entirely
content = re.sub(r'\s*private fun evaluateLive\(\) \{[\s\S]*?\}', '', content)

# Rewrite OnEquals
new_equals = '''            is ProgrammerEvent.OnEquals -> {
                if (previousValue != null && pendingOperator != null) {
                    val current = _currentValue.value
                    val prev = previousValue!!
                    val computed = when (pendingOperator) {
                        "AND" -> prev and current
                        "OR" -> prev or current
                        "XOR" -> prev xor current
                        "<<" -> prev shl current.toInt()
                        ">>" -> prev shr current.toInt()
                        else -> current
                    }
                    _result.value = BaseConverter.convert(computed, _activeBase.value)
                } else {
                    _result.value = BaseConverter.convert(_currentValue.value, _activeBase.value)
                }
            }'''

# Replace old OnEquals block
content = re.sub(r'\s*is ProgrammerEvent\.OnEquals -> \{[\s\S]*?\}', '\n' + new_equals, content, count=1)

with open('app/src/main/java/com/kush/mantis/features/programmer/presentation/ProgrammerViewModel.kt', 'w', encoding='utf-8') as f:
    f.write(content)
