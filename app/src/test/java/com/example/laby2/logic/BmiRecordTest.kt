package com.example.laby2.logic

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat

class BmiRecordTest {

    @Test
    fun fromString() {
        val date = "19.04.1997"
        val dateFormatter = SimpleDateFormat(BmiRecord.SHORT_DATE_FORMAT)
        val dateTime: Long = dateFormatter.parse(date).time
        val color = 0xB80000
        val stringBmiRecord = "weight,height,bmiValue,bmiCategory,$color,$dateTime"
        val bmiRecord = BmiRecord.fromString(stringBmiRecord)

        Assert.assertEquals("weight", bmiRecord.weight)
        Assert.assertEquals("height", bmiRecord.height)
        Assert.assertEquals("bmiValue", bmiRecord.bmiValue)
        Assert.assertEquals("bmiCategory", bmiRecord.bmiCategoryWord)
        Assert.assertEquals(color, bmiRecord.bmiCategoryColor)
        Assert.assertEquals(dateFormatter.parse(date), bmiRecord.date)
    }
}