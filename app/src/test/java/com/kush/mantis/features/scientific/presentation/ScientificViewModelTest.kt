package com.kush.mantis.features.scientific.presentation

import com.kush.mantis.features.basic.domain.EvaluateExpressionUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mariuszgromada.math.mxparser.License

class ScientificViewModelTest {

    private lateinit var viewModel: ScientificViewModel

    @Before
    fun setup() {
        License.iConfirmNonCommercialUse("Kush")
        viewModel = ScientificViewModel(EvaluateExpressionUseCase())
    }

    private fun keys(vararg labels: String) = labels.forEach { viewModel.onEvent(ScientificEvent.OnInput(it)) }

    private fun calc(vararg labels: String): String {
        viewModel.onEvent(ScientificEvent.OnClear)
        keys(*labels)
        viewModel.onEvent(ScientificEvent.OnEquals)
        return viewModel.result.value
    }

    @Test
    fun `second-mode functions are reachable and evaluate`() {
        viewModel.onEvent(ScientificEvent.ToggleSecondMode)
        assertTrue(viewModel.isSecondMode.value)
        assertEquals("30", calc("sin⁻¹", "0", ".", "5"))
        assertEquals("3", calc("³√", "2", "7"))
        assertEquals("2.7182818285", calc("eˣ", "1"))
        assertEquals("1,000", calc("10ˣ", "3"))
        assertEquals("120", calc("5", "n!"))
    }

    @Test
    fun `primary functions evaluate`() {
        assertEquals("0.5", calc("sin", "3", "0", ")"))
        assertEquals("4", calc("√", "1", "6"))
        assertEquals("1,024", calc("2", "xʸ", "1", "0"))
        assertEquals("2", calc("log", "1", "0", "0"))
    }

    @Test
    fun `pi keeps full precision when reused`() {
        calc("π")
        assertEquals("3.14159265358979", viewModel.expression.value.text)
        keys("×", "2")
        viewModel.onEvent(ScientificEvent.OnEquals)
        assertEquals("6.2831853072", viewModel.result.value)
    }

    @Test
    fun `undefined results show Error`() {
        assertEquals("Error", calc("log", "0"))
        assertEquals("Error", calc("tan", "9", "0"))
        assertEquals("Error", calc("2", "xʸ", "1", "0", "0", "0", "0"))
    }

    @Test
    fun `function after equals starts a new expression`() {
        calc("2", "+", "2")
        keys("sin", "3", "0")
        assertEquals("sin(30", viewModel.expression.value.text)
    }

    @Test
    fun `second decimal point is ignored`() {
        viewModel.onEvent(ScientificEvent.OnClear)
        keys("1", ".", "2", ".", "3")
        assertEquals("1.23", viewModel.expression.value.text)
    }
}
