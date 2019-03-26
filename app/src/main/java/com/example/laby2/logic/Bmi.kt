package com.example.laby2.logic

import android.content.res.Resources
import android.graphics.Color
import com.example.laby2.R

interface Bmi {
    fun countBmi(mass: Int, height: Int): Double
    fun oneWordBmiDescription(resources: Resources, bmiValue: Double): String {
        return when (bmiValue) {
            in 0.0..18.999 -> resources.getString(R.string.bmi_underweight)
            in 19.0..24.999 -> resources.getString(R.string.bmi_normal)
            in 25.0..29.999 -> resources.getString(R.string.bmi_overweight)
            in 30.0..39.999 -> resources.getString(R.string.bmi_obese)
            else -> resources.getString(R.string.bmi_extr_obese)
        }
    }

    fun getBmiColor(resources: Resources, bmiValue: Double): Int {
        val colorHexString = when (bmiValue) {
            in 0.0..18.999 -> resources.getString(R.string.bmi_color_underweight)
            in 19.0..24.999 -> resources.getString(R.string.bmi_color_normal)
            in 25.0..29.999 -> resources.getString(R.string.bmi_color_overweight)
            in 30.0..39.999 -> resources.getString(R.string.bmi_color_obese)
            else -> resources.getString(R.string.bmi_color_extr_obese)
        }

        return Color.parseColor(colorHexString)
    }

    companion object {
        fun getFullBmiDescription(resources: Resources, bmiValue: Double): String {
            return when (bmiValue) {
                in 0.0..18.999 -> resources.getString(R.string.bmi_desc_underweight)
                in 19.0..24.999 -> resources.getString(R.string.bmi_desc_normal)
                in 25.0..29.999 -> resources.getString(R.string.bmi_desc_overweight)
                in 30.0..39.999 -> resources.getString(R.string.bmi_desc_obese)
                else -> resources.getString(R.string.bmi_desc_extr_obese)
            }
        }
    }
}