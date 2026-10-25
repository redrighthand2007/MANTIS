package com.kush.mantis.features.basic.presentation

import com.kush.mantis.features.basic.domain.EvaluateExpressionUseCase
import org.junit.Assert.assertEquals
import androidx.lifecycle.SavedStateHandle
import com.kush.mantis.testutil.MainDispatcherRule
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mariuszgromada.math.mxparser.License

class BasicViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: BasicViewModel

    @Before
    fun setup() {
        License.iConfirmNonCommercialUse("Kush")
        viewModel = BasicViewModel(EvaluateExpressionUseCase(Dispatchers.Unconfined), SavedStateHandle())
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

    @Test
    fun `input is capped so a calculation can never freeze the app`() {
        type("1+".repeat(150))
        assertEquals(EvaluateExpressionUseCase.MAX_EXPRESSION_LENGTH, viewModel.expression.value.text.length)

        val pasted = androidx.compose.ui.text.input.TextFieldValue("9".repeat(500))
        viewModel.onEvent(BasicCalcEvent.OnExpressionChange(pasted))
        assertEquals(EvaluateExpressionUseCase.MAX_EXPRESSION_LENGTH, viewModel.expression.value.text.length)
    }

    @Test
    fun `expression and result survive the app being killed in the background`() {
        val savedState = SavedStateHandle()
        val first = BasicViewModel(EvaluateExpressionUseCase(Dispatchers.Unconfined), savedState)
        "7+2".forEach {
            val key = it.toString()
            if (key == "+") first.onEvent(BasicCalcEvent.OnOperatorClick(key)) else first.onEvent(BasicCalcEvent.OnNumberClick(key))
        }

        val restored = BasicViewModel(EvaluateExpressionUseCase(Dispatchers.Unconfined), savedState)
        assertEquals("7+2", restored.expression.value.text)
        restored.onEvent(BasicCalcEvent.OnEqualsClick)
        assertEquals("9", BasicViewModel(EvaluateExpressionUseCase(Dispatchers.Unconfined), savedState).result.value)
    }

    @Test
    fun `factorial key result is correct for decimals`() {
        type("2.5")
        viewModel.onEvent(BasicCalcEvent.OnOperatorClick("!"))
        equals()
        assertEquals("3.3233509704", viewModel.result.value)
    }
}
