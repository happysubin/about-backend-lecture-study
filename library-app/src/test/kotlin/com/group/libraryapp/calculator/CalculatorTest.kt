package com.group.libraryapp.calculator

import org.junit.jupiter.api.Assertions.*

fun main() {
    val calculatorTest = CalculatorTest()
    calculatorTest.addTest()
}

class CalculatorTest {

    fun addTest() {
        val calculator = Calculator(5)
        calculator.add(3)

        if(calculator.number != 8) {
            throw IllegalStateException()
        }
    }
}