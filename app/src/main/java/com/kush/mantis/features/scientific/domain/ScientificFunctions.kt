package com.kush.mantis.features.scientific.domain

object ScientificFunctions {
    // Key label -> text inserted into the expression
    val functionMap = mapOf(
        "sin" to "sin(",
        "cos" to "cos(",
        "tan" to "tan(",
        "sin⁻¹" to "asin(",
        "cos⁻¹" to "acos(",
        "tan⁻¹" to "atan(",
        "ln" to "ln(",
        "log" to "log10(",
        "√" to "√(",
        "³√" to "root(3, ",
        "x²" to "^2",
        "x³" to "^3",
        "xʸ" to "^",
        "eˣ" to "e^",
        "10ˣ" to "10^",
        "n!" to "!",
        "|x|" to "abs(",
        "π" to "pi",
        "e" to "e"
    )

    /** Inputs that extend the previous value (e.g. "+", "^2") rather than starting a new expression. */
    fun continuesExpression(inserted: String): Boolean =
        inserted.isNotEmpty() && inserted.first() in "+-×÷^!)"
}
