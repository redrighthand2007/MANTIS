import re

def fix_view_model(filepath, is_scientific=False, is_programmer=False, is_converter=False):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    if is_converter:
        # Converter is unique
        content = content.replace('object OnClear : ConverterEvent()', 'object OnClear : ConverterEvent()\n    object OnEquals : ConverterEvent()')
        content = content.replace('updateConversion()', '') # Remove all automated conversions
        
        # Add OnEquals explicitly
        new_clear = '''            is ConverterEvent.OnClear -> {
                _inputValue.value = "0"
                _outputValue.value = ""
            }
            is ConverterEvent.OnEquals -> {
                _outputValue.value = convertUnitUseCase(_inputValue.value, _category.value, _fromUnit.value, _toUnit.value)
            }'''
        content = re.sub(r'\s*is ConverterEvent\.OnClear -> \{[\s\S]*?\}', '\n' + new_clear, content)
        
        # Clear output on unit changes
        content = content.replace('_fromUnit.value = _units.value[0]\n                _toUnit.value = _units.value.getOrElse(1) { _units.value[0] }', '_fromUnit.value = _units.value[0]\n                _toUnit.value = _units.value.getOrElse(1) { _units.value[0] }\n                _outputValue.value = ""')
        content = content.replace('_fromUnit.value = event.unit', '_fromUnit.value = event.unit\n                _outputValue.value = ""')
        content = content.replace('_toUnit.value = event.unit', '_toUnit.value = event.unit\n                _outputValue.value = ""')
        content = content.replace('_toUnit.value = temp', '_toUnit.value = temp\n                _outputValue.value = ""')
        content = content.replace('_inputValue.value = "0"\n                _outputValue.value = ""', '_inputValue.value = "0"\n                _outputValue.value = ""')
    
    elif is_programmer:
        content = content.replace('evaluateLive()', '')
        
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
        content = re.sub(r'\s*is ProgrammerEvent\.OnEquals -> \{[\s\S]*?\}', '\n' + new_equals, content, count=1)

    elif is_scientific:
        content = content.replace('evaluateLive()', '')
        new_equals = '''            is ScientificEvent.OnEquals -> {
                val finalResult = evaluateExpressionUseCase(_expression.value.text, _isDegreeMode.value)
                if (finalResult.isNotEmpty() && finalResult != "NaN") {
                    _result.value = finalResult
                }
            }'''
        content = re.sub(r'\s*is ScientificEvent\.OnEquals -> \{[\s\S]*?\}', '\n' + new_equals, content, count=1)
        
    else:
        # Basic
        content = content.replace('evaluateLive()', '')
        new_equals = '''            is BasicCalcEvent.OnEqualsClick -> {
                val finalResult = evaluateExpressionUseCase(_expression.value.text)
                if (finalResult.isNotEmpty() && finalResult != "NaN") {
                    _result.value = finalResult
                }
            }'''
        content = re.sub(r'\s*is BasicCalcEvent\.OnEqualsClick -> \{[\s\S]*?\}', '\n' + new_equals, content, count=1)

    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

fix_view_model('app/src/main/java/com/kush/mantis/features/basic/presentation/BasicViewModel.kt', False, False, False)
fix_view_model('app/src/main/java/com/kush/mantis/features/scientific/presentation/ScientificViewModel.kt', True, False, False)
fix_view_model('app/src/main/java/com/kush/mantis/features/programmer/presentation/ProgrammerViewModel.kt', False, True, False)
fix_view_model('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterViewModel.kt', False, False, True)

