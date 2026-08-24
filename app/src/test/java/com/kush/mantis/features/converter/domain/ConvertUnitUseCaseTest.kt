package com.kush.mantis.features.converter.domain

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConvertUnitUseCaseTest {

    private lateinit var convertUnitUseCase: ConvertUnitUseCase

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
        val celsius = UnitDefinition("Celsius", "°C", 1.0)
        val fahrenheit = UnitDefinition("Fahrenheit", "°F", 1.0)
        val kelvin = UnitDefinition("Kelvin", "K", 1.0)

        // 0 C = 32 F
        assertEquals("32", convertUnitUseCase("0", "Temperature", celsius, fahrenheit))
        
        // 100 C = 212 F
        assertEquals("212", convertUnitUseCase("100", "Temperature", celsius, fahrenheit))

        // 32 F = 0 C
        assertEquals("0", convertUnitUseCase("32", "Temperature", fahrenheit, celsius))

        // 0 C = 273.15 K
        assertEquals("273.15", convertUnitUseCase("0", "Temperature", celsius, kelvin))
    }
}
