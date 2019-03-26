package com.example.laby2.logic

class BmiForLbIn(): Bmi {
    override fun countBmi(mass: Int, height: Int): Double = (mass * 703.0) / (height * height)
}