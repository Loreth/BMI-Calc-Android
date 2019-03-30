package com.example.laby2.logic

class BmiForLbIn() : Bmi {
    override fun countBmi(weight: Int, height: Int): Double = (weight * 703.0) / (height * height)

    override fun getWeightRange(): IntRange = IntRange(BMI_MASS_MIN, BMI_MASS_MAX)

    override fun getHeightRange(): IntRange = IntRange(BMI_HEIGHT_MIN, BMI_HEIGHT_MAX)

    companion object {
        private const val BMI_MASS_MIN = 44
        private const val BMI_MASS_MAX = 2200
        private const val BMI_HEIGHT_MIN = 20
        private const val BMI_HEIGHT_MAX = 100
    }
}