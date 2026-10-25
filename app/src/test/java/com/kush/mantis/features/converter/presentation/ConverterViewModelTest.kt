package com.kush.mantis.features.converter.presentation

import androidx.lifecycle.SavedStateHandle
import com.kush.mantis.features.converter.domain.ConvertUnitUseCase
import com.kush.mantis.features.converter.domain.UnitDefinitions
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConverterViewModelTest {

    private lateinit var viewModel: ConverterViewModel

    @Before
    fun setup() {
        viewModel = ConverterViewModel(ConvertUnitUseCase(), SavedStateHandle())
    }

    private fun type(keys: String) = keys.forEach { viewModel.onEvent(ConverterEvent.OnInput(it.toString())) }
    private val input get() = viewModel.inputValue.value
    private val output get() = viewModel.outputValue.value

    @Test
    fun `output updates live without pressing equals`() {
        assertEquals("0", output)
        type("5")
        assertEquals("0.005", output)
        viewModel.onEvent(ConverterEvent.SetToUnit(UnitDefinitions.Length[2]))
        assertEquals("500", output)
    }

    @Test
    fun `second decimal point is ignored`() {
        type("1.2.3")
        assertEquals("1.23", input)
    }

    @Test
    fun `sign toggle allows negative temperatures`() {
        viewModel.onEvent(ConverterEvent.SetCategory("Temp"))
        type("40")
        viewModel.onEvent(ConverterEvent.ToggleSign)
        assertEquals("-40", input)
        assertEquals("-40", output)
        viewModel.onEvent(ConverterEvent.ToggleSign)
        assertEquals("40", input)
    }

    @Test
    fun `swap exchanges units and recalculates`() {
        type("1")
        viewModel.onEvent(ConverterEvent.SwapUnits)
        assertEquals("km", viewModel.fromUnit.value.symbol)
        assertEquals("1,000", output)
    }

    @Test
    fun `base mode accepts hex digits and keeps the value when the input base changes`() {
        viewModel.onEvent(ConverterEvent.SetCategory("Base"))
        type("255")
        assertEquals("FF", output)

        viewModel.onEvent(ConverterEvent.SetFromUnit(UnitDefinitions.Base[2]))
        assertEquals("11111111", input)
        assertEquals("FF", output)

        type("2")
        assertEquals("11111111", input)

        viewModel.onEvent(ConverterEvent.SetFromUnit(UnitDefinitions.Base[1]))
        type("A")
        assertEquals("FFA", input)
    }

    @Test
    fun `base mode ignores decimals, sign and overflowing digits`() {
        viewModel.onEvent(ConverterEvent.SetCategory("Base"))
        type("1.")
        viewModel.onEvent(ConverterEvent.ToggleSign)
        assertEquals("1", input)

        viewModel.onEvent(ConverterEvent.OnClear)
        type("9".repeat(20))
        assertEquals("9".repeat(18), input)
    }

    @Test
    fun `switching between base and decimal categories resets input`() {
        viewModel.onEvent(ConverterEvent.SetCategory("Base"))
        viewModel.onEvent(ConverterEvent.SetFromUnit(UnitDefinitions.Base[1]))
        type("FF")
        viewModel.onEvent(ConverterEvent.SetCategory("Length"))
        assertEquals("0", input)
        assertEquals("0", output)
    }

    @Test
    fun `input is limited to 15 digits`() {
        type("1".repeat(20))
        assertEquals("1".repeat(15), input)
    }

    @Test
    fun `delete back to nothing resets to zero`() {
        type("5")
        viewModel.onEvent(ConverterEvent.ToggleSign)
        viewModel.onEvent(ConverterEvent.OnDelete)
        assertEquals("0", input)
    }

    @Test
    fun `category, units and input survive the app being killed`() {
        val savedState = SavedStateHandle()
        val first = ConverterViewModel(ConvertUnitUseCase(), savedState)
        first.onEvent(ConverterEvent.SetCategory("Temp"))
        first.onEvent(ConverterEvent.OnInput("4"))
        first.onEvent(ConverterEvent.OnInput("0"))
        first.onEvent(ConverterEvent.ToggleSign)

        val restored = ConverterViewModel(ConvertUnitUseCase(), savedState)
        assertEquals("Temp", restored.category.value)
        assertEquals("°C", restored.fromUnit.value.symbol)
        assertEquals("°F", restored.toUnit.value.symbol)
        assertEquals("-40", restored.inputValue.value)
        assertEquals("-40", restored.outputValue.value)
    }
}
