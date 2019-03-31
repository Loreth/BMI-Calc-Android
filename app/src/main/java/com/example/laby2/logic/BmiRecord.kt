package com.example.laby2.logic

import java.text.SimpleDateFormat
import java.util.*

class BmiRecord(
    val weight: String, val height: String, val bmiValue: String,
    val bmiCategoryWord: String, val bmiCategoryColor: Int, val date: Date
) : Comparable<BmiRecord> {
    override fun compareTo(other: BmiRecord): Int = compareValuesBy(this, other, BmiRecord::date)

    override fun toString(): String {
        return "$weight,$height,$bmiValue,$bmiCategoryWord,$bmiCategoryColor,${date.time}"
    }

    fun getShortDateAsString(): String {
        val dateFormatter = SimpleDateFormat(SHORT_DATE_FORMAT)
        return dateFormatter.format(date)
    }

    companion object {
        const val SHORT_DATE_FORMAT = "dd.MM.yyyy"
        fun fromString(string: String): BmiRecord {
            val fields = string.split(',')
            val date: Date = Date(fields[5].toLong())

            return BmiRecord(fields[0], fields[1], fields[2], fields[3], fields[4].toInt(), date)
        }
    }
}