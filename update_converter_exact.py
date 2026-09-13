with open('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterViewModel.kt', 'r', encoding='utf-8') as f:
    content = f.read()

content = content.replace('object OnClear : ConverterEvent()', 'object OnClear : ConverterEvent()\n    object OnEquals : ConverterEvent()')
content = content.replace('updateConversion()', '')

old_clear = """            is ConverterEvent.OnClear -> {
                _inputValue.value = "0"
                
            }"""
new_clear = """            is ConverterEvent.OnClear -> {
                _inputValue.value = "0"
                _outputValue.value = ""
            }
            is ConverterEvent.OnEquals -> {
                _outputValue.value = convertUnitUseCase(_inputValue.value, _category.value, _fromUnit.value, _toUnit.value)
            }"""
content = content.replace(old_clear, new_clear)

# Clear outputs when unit is switched
content = content.replace('_fromUnit.value = event.unit\n                ', '_fromUnit.value = event.unit\n                _outputValue.value = ""\n                ')
content = content.replace('_toUnit.value = event.unit\n                ', '_toUnit.value = event.unit\n                _outputValue.value = ""\n                ')
content = content.replace('_toUnit.value = temp\n                ', '_toUnit.value = temp\n                _outputValue.value = ""\n                ')

# Remove the private fun updateConversion
old_update_fn = """    private fun  {
        _outputValue.value = convertUnitUseCase(_inputValue.value, _category.value, _fromUnit.value, _toUnit.value)
    }"""
content = content.replace(old_update_fn, "")

with open('app/src/main/java/com/kush/mantis/features/converter/presentation/ConverterViewModel.kt', 'w', encoding='utf-8') as f:
    f.write(content)
