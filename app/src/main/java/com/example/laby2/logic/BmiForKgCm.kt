package com.example.laby2.logic

class BmiForKgCm(var mass: Int, var height: Int) : Bmi {
    override fun countBmi() = mass * 10000.0 / (height * height)
    fun oneWordBmiDescription(bmiValue: Double): String {
        return when (bmiValue) {
            in 0.0..18.999 -> "Underweight"
            in 19.0..24.999 -> "Normal"
            in 25.0..29.999 -> "Overweight"
            in 30.0..39.999 -> "Obese"
            else -> "Extreme obesity"
        }
    }
}