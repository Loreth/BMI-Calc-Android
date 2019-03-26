package com.example.laby2.logic

class BmiForKgCm() : Bmi {
    override fun countBmi(mass: Int, height: Int) = mass * 10000.0 / (height * height)
}