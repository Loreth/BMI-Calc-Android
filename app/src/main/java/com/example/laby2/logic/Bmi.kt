package com.example.laby2.logic

interface Bmi {
    fun countBmi(weight: Int, height: Int): Double

    fun getWeightRange(): IntRange

    fun getHeightRange(): IntRange
}