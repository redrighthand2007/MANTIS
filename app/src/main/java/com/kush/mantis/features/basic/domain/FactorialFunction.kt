package com.kush.mantis.features.basic.domain

import org.mariuszgromada.math.mxparser.FunctionExtension
import org.mariuszgromada.math.mxparser.mathcollection.SpecialFunctions

/**
 * x! for the expression parser. mXparser's built-in "!" silently floors decimals (2.5! = 6),
 * so whole numbers are multiplied exactly and other values use Gamma(x + 1).
 */
class FactorialFunction : FunctionExtension {
    private var x = Double.NaN

    override fun getParametersNumber(): Int = 1

    override fun setParameterValue(parameterIndex: Int, parameterValue: Double) {
        x = parameterValue
    }

    override fun getParameterName(parameterIndex: Int): String = "x"

    override fun calculate(): Double {
        if (x.isNaN() || x.isInfinite()) return Double.NaN
        if (x == Math.rint(x)) {
            // Undefined for negative whole numbers; 171! already overflows a Double
            if (x < 0) return Double.NaN
            if (x > MAX_EXACT) return Double.POSITIVE_INFINITY
            var result = 1.0
            for (k in 2..x.toInt()) result *= k
            return result
        }
        return SpecialFunctions.gamma(x + 1)
    }

    override fun clone(): FunctionExtension = FactorialFunction().also { it.x = x }

    companion object {
        const val NAME = "fact"
        private const val MAX_EXACT = 170.0

        /** Rewrites postfix factorials into function calls: "2.5!" -> "fact(2.5)", "sin(30)!" -> "fact(sin(30))". */
        fun rewritePostfix(expression: String): String {
            val sb = StringBuilder(expression)
            var bang = sb.indexOf("!")
            while (bang >= 0) {
                val start = operandStart(sb, bang)
                // Nothing before "!": leave it for the parser to reject
                if (start == bang) {
                    bang = sb.indexOf("!", bang + 1)
                    continue
                }
                val operand = sb.substring(start, bang)
                sb.replace(start, bang + 1, "$NAME($operand)")
                bang = sb.indexOf("!", start)
            }
            return sb.toString()
        }

        private val NUMBER = Regex("""[0-9.]+(E[+-]?[0-9]+)?""")

        private fun operandStart(s: CharSequence, bang: Int): Int {
            var j = bang
            if (j > 0 && s[j - 1] == ')') {
                // Bracketed operand, possibly a function call such as sin(...) or log10(...)
                var depth = 0
                while (j > 0) {
                    j--
                    if (s[j] == ')') depth++
                    if (s[j] == '(') depth--
                    if (depth == 0) break
                }
                var nameStart = j
                while (nameStart > 0 && s[nameStart - 1].isLetterOrDigit()) nameStart--
                // Leading digits belong to an implied multiplication ("2sin(30)!" -> 2*fact(sin(30)))
                while (nameStart < j && s[nameStart].isDigit()) nameStart++
                return nameStart
            }

            while (j > 0) {
                val c = s[j - 1]
                val isExponentSign = (c == '+' || c == '-') && j >= 3 && s[j - 2] == 'E' &&
                    (s[j - 3].isDigit() || s[j - 3] == '.')
                if (c.isLetterOrDigit() || c == '.' || isExponentSign) j-- else break
            }
            val token = s.substring(j, bang)
            if (NUMBER.matches(token)) return j
            // "2pi!" means 2 * pi!, so only the trailing name is the operand
            var nameStart = j
            while (nameStart < bang && (s[nameStart].isDigit() || s[nameStart] == '.')) nameStart++
            return nameStart
        }
    }
}
