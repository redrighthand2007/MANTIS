package com.kush.mantis.features.basic.domain

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mariuszgromada.math.mxparser.License

class EvaluateExpressionUseCaseTest {

    private lateinit var evaluateExpressionUseCase: EvaluateExpressionUseCase

    @Before
    fun setup() {
        License.iConfirmNonCommercialUse("Kush")
        evaluateExpressionUseCase = EvaluateExpressionUseCase()
    }

    @Test
    fun `evaluate basic arithmetic correctly`() {
        assertEquals("4", evaluateExpressionUseCase("2+2"))
        assertEquals("10", evaluateExpressionUseCase("5*2"))
        assertEquals("3", evaluateExpressionUseCase("9/3"))
        assertEquals("-1", evaluateExpressionUseCase("4-5"))
    }

    @Test
    fun `evaluate order of operations`() {
        assertEquals("14", evaluateExpressionUseCase("2+3*4"))
        assertEquals("20", evaluateExpressionUseCase("(2+3)*4"))
    }

    @Test
    fun `evaluate degree and radian modes for trig`() {
        // sin(30 degrees) = 0.5
        assertEquals("0.5", evaluateExpressionUseCase("sin(30)", isDegreeMode = true))
        
        // sin(pi/2 radians) = 1
        assertEquals("1", evaluateExpressionUseCase("sin(pi/2)", isDegreeMode = false))
    }

    @Test
    fun `evaluate invalid expression returns Error`() {
        assertEquals("Error", evaluateExpressionUseCase("2+*3"))
        assertEquals("Error", evaluateExpressionUseCase("1/0"))
    }
}
