package com.kush.mantis.features.basic.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test
import org.mariuszgromada.math.mxparser.License

class EvaluateExpressionUseCaseTest {

    private lateinit var evaluateExpressionUseCase: EvaluateExpressionUseCase

    @Before
    fun setup() {
        License.iConfirmNonCommercialUse("Kush")
        evaluateExpressionUseCase = EvaluateExpressionUseCase(Dispatchers.Unconfined)
    }

    @Test
    fun `evaluate basic arithmetic correctly`() {
        assertEquals("4", evaluateExpressionUseCase("2+2"))
        assertEquals("10", evaluateExpressionUseCase("5×2"))
        assertEquals("3", evaluateExpressionUseCase("9÷3"))
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
    fun `invalid, undefined and overflowing expressions return Error`() {
        assertEquals("Error", evaluateExpressionUseCase("2+*3"))
        assertEquals("Error", evaluateExpressionUseCase("1/0"))
        assertEquals("Error", evaluateExpressionUseCase("tan(90)"))
        assertEquals("Error", evaluateExpressionUseCase("10^400"))
        assertNull(evaluateExpressionUseCase.evaluate("log10(0)"))
        assertEquals("", evaluateExpressionUseCase("  "))
    }

    @Test
    fun `missing closing brackets are added automatically`() {
        assertEquals("0.5", evaluateExpressionUseCase("sin(30"))
        assertEquals("20", evaluateExpressionUseCase("((2+3)*4"))
    }

    @Test
    fun `expressions over the length limit are rejected`() {
        assertNull(evaluateExpressionUseCase.evaluate("1+".repeat(150) + "1"))
        assertEquals("100", evaluateExpressionUseCase("1+".repeat(99) + "1"))
    }

    @Test
    fun `async evaluation returns the same result`() = kotlinx.coroutines.test.runTest {
        assertEquals(0.5, evaluateExpressionUseCase.evaluateAsync("sin(30)")!!, 1e-12)
    }
}
