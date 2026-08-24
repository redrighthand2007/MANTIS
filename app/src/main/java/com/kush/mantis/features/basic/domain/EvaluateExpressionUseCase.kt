package com.kush.mantis.features.basic.domain

import com.kush.mantis.core.util.NumberFormatter
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.mXparser
import javax.inject.Inject

class EvaluateExpressionUseCase @Inject constructor() {
    operator fun invoke(expression: String, isDegreeMode: Boolean = true): String {
        if (expression.isBlank()) return ""
        
        val sanitizedExpression = expression
            .replace("×", "*")
            .replace("÷", "/")
        
        if (isDegreeMode) {
            mXparser.setDegreesMode()
        } else {
            mXparser.setRadiansMode()
        }
        
        val e = Expression(sanitizedExpression)
        val result = e.calculate()
        
        return if (result.isNaN()) {
            "Error"
        } else {
            NumberFormatter.formatResult(result)
        }
    }
}
