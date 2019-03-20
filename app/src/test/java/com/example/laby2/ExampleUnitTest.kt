package com.example.laby2

import com.example.laby2.logic.BmiForKgCm
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun for_valid_data_should_count_bmi() {
        val bmi = BmiForKgCm(65, 180)
        assertEquals(20.061, bmi.countBmi(), 0.001)
    }
}
