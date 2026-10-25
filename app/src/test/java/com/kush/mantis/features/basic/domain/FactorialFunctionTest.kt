package com.kush.mantis.features.basic.domain

import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mariuszgromada.math.mxparser.License

class FactorialFunctionTest {

    private lateinit var useCase: EvaluateExpressionUseCase

    @Before
    fun setup() {
        License.iConfirmNonCommercialUse("Kush")
        useCase = EvaluateExpressionUseCase(Dispatchers.Unconfined)
    }

    @Test
    fun `postfix factorials become function calls`() {
        assertEquals("fact(5)", FactorialFunction.rewritePostfix("5!"))
        assertEquals("fact(2.5)", FactorialFunction.rewritePostfix("2.5!"))
        assertEquals("2*fact(3)", FactorialFunction.rewritePostfix("2*3!"))
        assertEquals("fact((1+2))", FactorialFunction.rewritePostfix("(1+2)!"))
        assertEquals("2fact(sin(30))", FactorialFunction.rewritePostfix("2sin(30)!"))
        assertEquals("fact(fact(3))", FactorialFunction.rewritePostfix("3!!"))
        assertEquals("2fact(pi)", FactorialFunction.rewritePostfix("2pi!"))
        assertEquals("fact(1.5E-3)", FactorialFunction.rewritePostfix("1.5E-3!"))
        assertEquals("!", FactorialFunction.rewritePostfix("!"))
    }

    @Test
    fun `whole numbers are exact`() {
        assertEquals("120", useCase("5!"))
        assertEquals("1", useCase("0!"))
        assertEquals("720", useCase("3!!"))
        assertEquals("2.43290200817664E18", useCase("20!"))
    }

    @Test
    fun `decimals use the gamma function instead of being rounded`() {
        assertEquals("3.3233509704", useCase("2.5!"))
        assertEquals("0.8862269255", useCase("0.5!"))
        assertEquals("1.7724538509", useCase("2sin(30)!"))
    }

    @Test
    fun `negative whole numbers and overflow are errors`() {
        assertNull(useCase.evaluate("(-1)!"))
        assertNull(useCase.evaluate("171!"))
        assertNull(useCase.evaluate("!"))
    }
}
