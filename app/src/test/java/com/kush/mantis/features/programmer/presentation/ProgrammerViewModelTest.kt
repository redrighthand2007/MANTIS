package com.kush.mantis.features.programmer.presentation

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProgrammerViewModelTest {

    private lateinit var viewModel: ProgrammerViewModel

    @Before
    fun setup() {
        viewModel = ProgrammerViewModel(SavedStateHandle())
    }

    private fun type(digits: String) = digits.forEach { viewModel.onEvent(ProgrammerEvent.OnInput(it.toString())) }
    private fun op(op: String) = viewModel.onEvent(ProgrammerEvent.OnBitwiseOp(op))
    private fun equals() = viewModel.onEvent(ProgrammerEvent.OnEquals)
    private fun delete() = viewModel.onEvent(ProgrammerEvent.OnDelete)
    private fun base(base: Int) = viewModel.onEvent(ProgrammerEvent.SetBase(base))
    private val expression get() = viewModel.expression.value.text

    @Test
    fun `chained operators evaluate left to right`() {
        type("5"); op("AND"); type("3"); op("OR"); type("8"); equals()
        assertEquals("9", viewModel.result.value)
    }

    @Test
    fun `digit after equals starts a new number`() {
        type("6"); equals(); type("1")
        assertEquals("1", expression)
        assertEquals(1L, viewModel.currentValue.value)
    }

    @Test
    fun `digits that would overflow 64 bits are ignored`() {
        type("9".repeat(20))
        assertEquals("9".repeat(18), viewModel.inputString.value)
        assertEquals(999_999_999_999_999_999L, viewModel.currentValue.value)

        viewModel.onEvent(ProgrammerEvent.OnClear)
        base(16)
        type("F".repeat(17))
        assertEquals("F".repeat(16), viewModel.inputString.value)
    }

    @Test
    fun `NOT shows the bit pattern and typing afterwards starts fresh`() {
        base(16)
        op("NOT")
        assertEquals("FFFFFFFFFFFFFFFF", expression)
        type("5")
        assertEquals("5", expression)
        assertEquals(5L, viewModel.currentValue.value)
    }

    @Test
    fun `shifting by 64 or more does not wrap around`() {
        type("1"); op("<<"); type("64"); equals()
        assertEquals("0", viewModel.result.value)

        op("NOT"); op(">>"); type("70"); equals()
        assertEquals("-1", viewModel.result.value)

        viewModel.onEvent(ProgrammerEvent.OnClear)
        type("1"); op("<<"); type("4"); equals()
        assertEquals("16", viewModel.result.value)
    }

    @Test
    fun `delete after an operator removes the operator cleanly`() {
        type("12"); op("XOR")
        assertEquals("12 XOR", expression)
        delete()
        assertEquals("12", expression)
        delete()
        assertEquals("1", expression)
    }

    @Test
    fun `switching base mid-calculation converts the whole expression`() {
        type("10"); op("AND"); type("6")
        base(2)
        assertEquals("1010 AND 110", expression)
        equals()
        assertEquals("10", viewModel.result.value)
        base(10)
        assertEquals("2", viewModel.result.value)
    }

    @Test
    fun `invalid digits for the base and leading zeros are ignored`() {
        base(2)
        type("0021")
        assertEquals("1", viewModel.inputString.value)
    }

    @Test
    fun `equals without a second operand reuses the first`() {
        type("5"); op("XOR"); equals()
        assertEquals("0", viewModel.result.value)
    }

    @Test
    fun `unfinished calculation survives the app being killed`() {
        val savedState = SavedStateHandle()
        val first = ProgrammerViewModel(savedState)
        first.onEvent(ProgrammerEvent.SetBase(16))
        "F".forEach { first.onEvent(ProgrammerEvent.OnInput(it.toString())) }
        first.onEvent(ProgrammerEvent.OnBitwiseOp("AND"))
        first.onEvent(ProgrammerEvent.OnInput("3"))

        val restored = ProgrammerViewModel(savedState)
        assertEquals(16, restored.activeBase.value)
        assertEquals("F AND 3", restored.expression.value.text)
        restored.onEvent(ProgrammerEvent.OnEquals)
        assertEquals("3", restored.result.value)
    }
}
