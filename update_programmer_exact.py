with open('app/src/main/java/com/kush/mantis/features/programmer/presentation/ProgrammerViewModel.kt', 'r', encoding='utf-8') as f:
    content = f.read()

# 1. Remove evaluateLive() calls
content = content.replace('evaluateLive()', '')

# 2. Rewrite updateValueFromInput
old_update = """    private fun updateValueFromInput() {
        val parsed = BaseConverter.parse(_inputString.value, _activeBase.value)
        if (parsed != null) {
            _currentValue.value = parsed
        }
        
    }"""
new_update = """    private fun updateValueFromInput() {
        val parsed = BaseConverter.parse(_inputString.value, _activeBase.value)
        if (parsed != null) {
            _currentValue.value = parsed
        }
    }"""
content = content.replace(old_update, new_update)

# 3. Rewrite OnEquals
old_equals = """            is ProgrammerEvent.OnEquals -> {
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
                    _currentValue.value = computed
                    val resultStr = BaseConverter.convert(computed, _activeBase.value)
                    _inputString.value = resultStr
                    
                    _expression.value = TextFieldValue(resultStr, TextRange(resultStr.length))
                    _result.value = ""
                    
                    previousValue = null
                    pendingOperator = null
                }
            }"""
new_equals = """            is ProgrammerEvent.OnEquals -> {
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
            }"""
content = content.replace(old_equals, new_equals)

# 4. Remove evaluateLive function definition entirely
old_eval = """    private fun  {
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
    }"""
content = content.replace(old_eval, "")

with open('app/src/main/java/com/kush/mantis/features/programmer/presentation/ProgrammerViewModel.kt', 'w', encoding='utf-8') as f:
    f.write(content)
