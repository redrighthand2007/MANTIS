package com.kush.mantis.features.converter.domain

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConvertUnitUseCaseTest {

    private lateinit var convertUnitUseCase: ConvertUnitUseCase

    private val celsius = UnitDefinitions.Temperature[0]
    private val fahrenheit = UnitDefinitions.Temperature[1]
    private val kelvin = UnitDefinitions.Temperature[2]

    @Before
    fun setup() {
        convertUnitUseCase = ConvertUnitUseCase()
    }

    @Test
    fun `convert standard length units`() {
        val meters = UnitDefinition("Meter", "m", 1.0)
        val kilometers = UnitDefinition("Kilometer", "km", 1000.0)

        // 5 km to m
        assertEquals("5,000", convertUnitUseCase("5", "Length", kilometers, meters))

        // 500 m to km
        assertEquals("0.5", convertUnitUseCase("500", "Length", meters, kilometers))
    }

    @Test
    fun `convert temperature units`() {
        assertEquals("32", convertUnitUseCase("0", "Temp", celsius, fahrenheit))
        assertEquals("212", convertUnitUseCase("100", "Temp", celsius, fahrenheit))
        assertEquals("0", convertUnitUseCase("32", "Temp", fahrenheit, celsius))
        assertEquals("273.15", convertUnitUseCase("0", "Temp", celsius, kelvin))
    }

    @Test
    fun `negative temperatures convert and below absolute zero is an error`() {
        assertEquals("-40", convertUnitUseCase("-40", "Temp", celsius, fahrenheit))
        assertEquals("0", convertUnitUseCase("-459.67", "Temp", fahrenheit, kelvin))
        assertEquals("Error", convertUnitUseCase("-1", "Temp", kelvin, celsius))
    }

    @Test
    fun `imperial units use exact international definitions`() {
        assertEquals("1.609344", convertUnitUseCase("1", "Length", UnitDefinitions.Length[5], UnitDefinitions.Length[1]))
        assertEquals("0.45359237", convertUnitUseCase("1", "Weight", UnitDefinitions.Weight[2], UnitDefinitions.Weight[1]))
        assertEquals("28.349523125", convertUnitUseCase("1", "Weight", UnitDefinitions.Weight[3], UnitDefinitions.Weight[0]))
    }

    @Test
    fun `small results keep their significant digits`() {
        assertEquals("0.0000062137", convertUnitUseCase("1", "Length", UnitDefinitions.Length[2], UnitDefinitions.Length[5]))
    }

    @Test
    fun `invalid input is an error, not zero`() {
        assertEquals("Error", convertUnitUseCase("1.2.3", "Length", UnitDefinitions.Length[0], UnitDefinitions.Length[1]))
        assertEquals("Error", convertUnitUseCase("1.5", "Base", UnitDefinitions.Base[0], UnitDefinitions.Base[1]))
        assertEquals("", convertUnitUseCase("-", "Length", UnitDefinitions.Length[0], UnitDefinitions.Length[1]))
    }

    @Test
    fun `base conversion`() {
        assertEquals("255", convertUnitUseCase("FF", "Base", UnitDefinitions.Base[1], UnitDefinitions.Base[0]))
        assertEquals("11111111", convertUnitUseCase("255", "Base", UnitDefinitions.Base[0], UnitDefinitions.Base[2]))
    }
}
