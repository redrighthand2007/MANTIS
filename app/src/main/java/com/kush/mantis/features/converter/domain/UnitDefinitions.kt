package com.kush.mantis.features.converter.domain

data class UnitDefinition(val name: String, val symbol: String, val toBase: Double)

object UnitDefinitions {
    val Length = listOf(
        UnitDefinition("Meter", "m", 1.0),
        UnitDefinition("Kilometer", "km", 1000.0),
        UnitDefinition("Centimeter", "cm", 0.01),
        UnitDefinition("Inch", "in", 0.0254),
        UnitDefinition("Foot", "ft", 0.3048),
        UnitDefinition("Mile", "mi", 1609.34)
    )

    val Weight = listOf(
        UnitDefinition("Gram", "g", 1.0),
        UnitDefinition("Kilogram", "kg", 1000.0),
        UnitDefinition("Pound", "lb", 453.592),
        UnitDefinition("Ounce", "oz", 28.3495)
    )

    val Temperature = listOf(
        UnitDefinition("Celsius", "°C", 1.0),
        UnitDefinition("Fahrenheit", "°F", 0.0),
        UnitDefinition("Kelvin", "K", 0.0)
    )

    val Base = listOf(
        UnitDefinition("Decimal", "DEC", 10.0),
        UnitDefinition("Hexadecimal", "HEX", 16.0),
        UnitDefinition("Binary", "BIN", 2.0),
        UnitDefinition("Octal", "OCT", 8.0)
    )

    val Categories = mapOf(
        "Length" to Length,
        "Weight" to Weight,
        "Temperature" to Temperature,
        "Base" to Base
    )
}
