import re

with open('app/src/main/java/com/kush/mantis/features/scientific/presentation/ScientificViewModel.kt', 'r', encoding='utf-8') as f:
    content = f.read()

# Remove evaluateLive() everywhere
content = re.sub(r'\s*evaluateLive\(\)', '', content)

# Remove evaluateLive private function entirely
content = re.sub(r'\s*private fun evaluateLive\(\) \{[\s\S]*?\}', '', content)

# Rewrite OnEquals
new_equals = '''            is ScientificEvent.OnEquals -> {
                val finalResult = evaluateExpressionUseCase(_expression.value.text, _isDegreeMode.value)
                if (finalResult.isNotEmpty() && finalResult != "NaN") {
                    _result.value = finalResult
                } else {
                    _result.value = "Error"
                }
            }'''

# Replace old OnEquals block
content = re.sub(r'\s*is ScientificEvent\.OnEquals -> \{[\s\S]*?\}', '\n' + new_equals, content, count=1)

with open('app/src/main/java/com/kush/mantis/features/scientific/presentation/ScientificViewModel.kt', 'w', encoding='utf-8') as f:
    f.write(content)
