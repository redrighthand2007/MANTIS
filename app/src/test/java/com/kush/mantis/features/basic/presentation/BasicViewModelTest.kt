package com.kush.mantis.features.basic.presentation

import com.kush.mantis.features.basic.domain.EvaluateExpressionUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mariuszgromada.math.mxparser.License

class BasicViewModelTest {

    private lateinit var viewModel: BasicViewModel

    @Before
    fun setup() {
        License.iConfirmNonCommercialUse("Kush")
        viewModel = BasicViewModel(EvaluateExpressionUseCase())
    }

    private fun type(input: String) {
        input.forEach {
            val key = it.toString()
            if (key in "+-×÷()") viewModel.onEvent(BasicCalcEvent.OnOperatorClick(key))
            else viewModel.onEvent(BasicCalcEvent.OnNumberClick(key))
        }
    }

    private fun equals() = viewModel.onEvent(BasicCalcEvent.OnEqualsClick)

    @Test
    fun `large result can be reused in the next calculation`() {
        type("1000+234")
        equals()
        assertEquals("1,234", viewModel.result.value)
        assertEquals("1234", viewModel.expression.value.text)

        type("+1")
        equals()
        assertEquals("1,235", viewModel.result.value)
    }

    @Test
    fun `second decimal point in a number is ignored`() {
        type("1.2.3")
        assertEquals("1.23", viewModel.expression.value.text)
        type("+4.5")
        assertEquals("1.23+4.5", viewModel.expression.value.text)
    }

    @Test
    fun `digit after equals starts a new calculation, operator continues it`() {
        type("2+2")
        equals()
        type("5")
        assertEquals("5", viewModel.expression.value.text)

        type("+1")
        equals()
        type("×2")
        equals()
        assertEquals("12", viewModel.result.value)
    }

    @Test
    fun `invalid input shows Error and keeps the expression for fixing`() {
        type("1÷0")
        equals()
        assertEquals("Error", viewModel.result.value)
        assertEquals("1÷0", viewModel.expression.value.text)
    }

    @Test
    fun `chained results keep full precision`() {
        type("1÷3")
        equals()
        type("×3")
        equals()
        assertEquals("1", viewModel.result.value)
    }

    @Test
    fun `very small results are not rounded to zero`() {
        type("1÷3000000000")
        equals()
        assertEquals("3.33333333333333E-10", viewModel.result.value)
    }
}
