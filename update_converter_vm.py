import re

with open('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterViewModel.kt', 'r', encoding='utf-8') as f:
    content = f.read()

# Add OnEquals to ConverterEvent
content = content.replace('object OnClear : ConverterEvent()', 'object OnClear : ConverterEvent()\n    object OnEquals : ConverterEvent()')

# Remove updateConversion() from SetCategory, SetFromUnit, SetToUnit, SwapUnits, OnInput, OnDelete
content = re.sub(r'\s*updateConversion\(\)', '', content)

# Rewrite OnClear
new_clear = '''            is ConverterEvent.OnClear -> {
                _inputValue.value = "0"
                _outputValue.value = ""
            }
            is ConverterEvent.OnEquals -> {
                _outputValue.value = convertUnitUseCase(_inputValue.value, _category.value, _fromUnit.value, _toUnit.value)
            }'''
content = re.sub(r'\s*is ConverterEvent\.OnClear -> \{[\s\S]*?\}', '\n' + new_clear, content)

# Clear output on unit changes just to be clean
content = content.replace('_fromUnit.value = _units.value[0]\n                _toUnit.value = _units.value.getOrElse(1) { _units.value[0] }', '_fromUnit.value = _units.value[0]\n                _toUnit.value = _units.value.getOrElse(1) { _units.value[0] }\n                _outputValue.value = ""')
content = content.replace('_fromUnit.value = event.unit', '_fromUnit.value = event.unit\n                _outputValue.value = ""')
content = content.replace('_toUnit.value = event.unit', '_toUnit.value = event.unit\n                _outputValue.value = ""')
content = content.replace('_toUnit.value = temp', '_toUnit.value = temp\n                _outputValue.value = ""')
content = content.replace('_inputValue.value = "0"\n                _outputValue.value = ""', '_inputValue.value = "0"\n                _outputValue.value = ""')

# We don't want to completely remove updateConversion() private function because we just replaced the calls. We can leave it or remove it. We replaced all calls so it's unused, but it doesn't break the build (Kotlin warns on unused private functions but might compile). Let's explicitly remove it to be safe.
content = re.sub(r'\s*private fun updateConversion\(\) \{[\s\S]*?\}', '', content)

with open('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterViewModel.kt', 'w', encoding='utf-8') as f:
    f.write(content)
