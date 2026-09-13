import re

with open('app/src/main/java/com/kush/mantis/features/basic/presentation/BasicViewModel.kt', 'r', encoding='utf-8') as f:
    content = f.read()

# Remove evaluateLive() everywhere
content = re.sub(r'\s*evaluateLive\(\)', '', content)

# Remove evaluateLive private function entirely
content = re.sub(r'\s*private fun evaluateLive\(\) \{[\s\S]*?\}', '', content)

# Rewrite OnEqualsClick
new_equals = '''            is BasicCalcEvent.OnEqualsClick -> {
                val finalResult = evaluateExpressionUseCase(_expression.value.text)
                if (finalResult.isNotEmpty() && finalResult != "NaN") {
                    _result.value = finalResult
                } else {
                    _result.value = "Error"
                }
            }'''

# Replace old OnEqualsClick block
content = re.sub(r'\s*is BasicCalcEvent\.OnEqualsClick -> \{[\s\S]*?\}', '\n' + new_equals, content, count=1)

with open('app/src/main/java/com/kush/mantis/features/basic/presentation/BasicViewModel.kt', 'w', encoding='utf-8') as f:
    f.write(content)
