package com.example.laby2.logic

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat

class BmiRecordTest {

    @Test
    fun fromString() {
        val stringBmiRecord = "weight,height,bmiValue,bmiCategory,19.04.1997"
        val dateFormatter = SimpleDateFormat(BmiRecord.SHORT_DATE_FORMAT)
        val bmiRecord = BmiRecord.fromString(stringBmiRecord)

        Assert.assertEquals("weight", bmiRecord.weight)
        Assert.assertEquals("height", bmiRecord.height)
        Assert.assertEquals("bmiValue", bmiRecord.bmiValue)
        Assert.assertEquals("bmiCategory", bmiRecord.bmiCategory)
        Assert.assertEquals(dateFormatter.parse("19.04.1997"), bmiRecord.date)
    }
}