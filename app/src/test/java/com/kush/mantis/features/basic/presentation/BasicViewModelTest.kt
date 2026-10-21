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
        input.forEach { viewModel.onEvent(BasicCalcEvent.OnNumberClick(it.toString())) }
    }

    @Test
    fun `large result can be reused in the next calculation`() {
        type("1000+234")
        viewModel.onEvent(BasicCalcEvent.OnEqualsClick)
        assertEquals("1,234", viewModel.result.value)
        assertEquals("1234", viewModel.expression.value.text)

        type("+1")
        viewModel.onEvent(BasicCalcEvent.OnEqualsClick)
        assertEquals("1,235", viewModel.result.value)
    }
}
