package com.example.laby2.logic

interface Bmi {
    fun countBmi(mass: Int, height: Int): Double

    fun getMassRange(): IntRange

    fun getHeightRange(): IntRange
}