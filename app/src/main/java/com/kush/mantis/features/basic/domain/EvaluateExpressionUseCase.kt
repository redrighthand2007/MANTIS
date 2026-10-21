package com.kush.mantis.features.basic.domain

import com.kush.mantis.core.util.NumberFormatter
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.mXparser
import javax.inject.Inject

class EvaluateExpressionUseCase @Inject constructor() {

    /** Returns the numeric result, or null if the expression is invalid or overflows. */
    fun evaluate(expression: String, isDegreeMode: Boolean = true): Double? {
        if (expression.isBlank()) return null

        val sanitizedExpression = closeOpenBrackets(
            expression
                .replace("×", "*")
                .replace("÷", "/")
        )

        if (isDegreeMode) {
            mXparser.setDegreesMode()
        } else {
            mXparser.setRadiansMode()
        }

        val result = Expression(sanitizedExpression).calculate()
        return if (result.isNaN() || result.isInfinite()) null else result
    }

    operator fun invoke(expression: String, isDegreeMode: Boolean = true): String {
        if (expression.isBlank()) return ""
        val result = evaluate(expression, isDegreeMode) ?: return ERROR
        return NumberFormatter.formatResult(result) ?: ERROR
    }

    // Lets users leave trailing brackets open, e.g. "sin(30" evaluates as "sin(30)"
    private fun closeOpenBrackets(expression: String): String {
        val missing = expression.count { it == '(' } - expression.count { it == ')' }
        return if (missing > 0) expression + ")".repeat(missing) else expression
    }

    companion object {
        const val ERROR = "Error"
    }
}
