package com.example.laby2

import com.example.laby2.logic.BmiForKgCm
import com.example.laby2.logic.BmiForLbIn
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class BmiUnitTest {
    @Test
    fun for_valid_data_should_count_bmi_kg_cm() {
        val bmi = BmiForKgCm()
        assertEquals(20.061, bmi.countBmi(65, 180), 0.001)
    }

    @Test
    fun for_valid_data_should_count_bmi_lb_in() {
        val bmi = BmiForLbIn()
        assertEquals(20.082, bmi.countBmi(144, 71), 0.001)
    }

    @Test
    fun for_out_of_range_value_should_return_false_lb_in() {
        val bmi = BmiForLbIn()
        assertEquals(false, bmi.getHeightRange().contains(100000))
        assertEquals(false, bmi.getMassRange().contains(100000))
    }

    @Test
    fun for_out_of_range_value_should_return_false_kg_cm() {
        val bmi = BmiForKgCm()
        assertEquals(false, bmi.getHeightRange().contains(100000))
        assertEquals(false, bmi.getMassRange().contains(100000))
    }

    @Test
    fun for_in_range_value_should_return_false_lb_in() {
        val bmi = BmiForLbIn()
        assertEquals(true, bmi.getHeightRange().contains(60))
        assertEquals(true, bmi.getMassRange().contains(130))
    }

    @Test
    fun for_in_range_value_should_return_false_kg_cm() {
        val bmi = BmiForKgCm()
        assertEquals(true, bmi.getHeightRange().contains(180))
        assertEquals(true, bmi.getMassRange().contains(70))
    }
}
