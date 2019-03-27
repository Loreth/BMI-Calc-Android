package com.example.laby2.logic

interface Bmi {
    fun countBmi(mass: Int, height: Int): Double

    fun isMassInRange(mass: Int): Boolean

    fun isHeightInRange(height: Int): Boolean

    fun getMassRange(): Pair<Int, Int>

    fun getHeightRange(): Pair<Int, Int>
}