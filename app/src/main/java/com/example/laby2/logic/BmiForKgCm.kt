package com.example.laby2.logic

class BmiForKgCm() : Bmi {
    override fun countBmi(mass: Int, height: Int): Double = mass * 10000.0 / (height * height)

    override fun isMassInRange(mass: Int): Boolean = mass in BMI_MASS_MIN..BMI_MASS_MAX

    override fun isHeightInRange(height: Int): Boolean = height in BMI_HEIGHT_MIN..BMI_HEIGHT_MAX

    override fun getMassRange(): Pair<Int, Int> = Pair(BMI_MASS_MIN, BMI_MASS_MAX)

    override fun getHeightRange(): Pair<Int, Int> = Pair(BMI_HEIGHT_MIN, BMI_HEIGHT_MAX)

    companion object {
        private const val BMI_MASS_MIN = 20
        private const val BMI_MASS_MAX = 999
        private const val BMI_HEIGHT_MIN = 50
        private const val BMI_HEIGHT_MAX = 260
    }
}