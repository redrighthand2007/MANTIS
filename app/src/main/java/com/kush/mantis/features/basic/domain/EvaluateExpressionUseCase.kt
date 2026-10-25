package com.kush.mantis.features.basic.domain

import com.kush.mantis.core.util.NumberFormatter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.Function
import org.mariuszgromada.math.mxparser.mXparser
import javax.inject.Inject

class EvaluateExpressionUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) {

    /** Evaluates off the main thread so a slow expression can never freeze the UI. */
    suspend fun evaluateAsync(expression: String, isDegreeMode: Boolean = true): Double? =
        withContext(dispatcher) { evaluate(expression, isDegreeMode) }

    /** Returns the numeric result, or null if the expression is invalid or overflows. */
    fun evaluate(expression: String, isDegreeMode: Boolean = true): Double? {
        if (expression.isBlank() || expression.length > MAX_EXPRESSION_LENGTH) return null

        val sanitizedExpression = FactorialFunction.rewritePostfix(
            closeOpenBrackets(
                expression
                    .replace("×", "*")
                    .replace("÷", "/")
            )
        )

        // mXparser's angle mode is global, so Basic and Scientific must not evaluate at the same time
        val result = synchronized(LOCK) {
            if (isDegreeMode) mXparser.setDegreesMode() else mXparser.setRadiansMode()
            val factorial = Function(FactorialFunction.NAME, FactorialFunction())
            Expression(sanitizedExpression, factorial).calculate()
        }
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

        /** Long chains of + and - get slow in mXparser; 200 characters stays well under a second on phones. */
        const val MAX_EXPRESSION_LENGTH = 200

        private val LOCK = Any()
    }
}
