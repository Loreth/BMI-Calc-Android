package com.example.laby2.logic

import android.content.res.Resources
import com.example.laby2.R

enum class BmiCategory(val shortDescription: String, val longDescription: String, val imgResource: Int) {
    EXTREMELY_UNDERWEIGHT(
        "Extremely Underweight",
        "This indicates an extremely low Body Mass Index, with very little or unmeasurable body fat. " +
                "Malnutrition, health problems, or eating disorders are the most common causes for such a low figure." +
                " If you find yourself in this range, you should seek a professional medical, physical, and dietary" +
                " assessment. You could be at risk of starvation, nutritional deficiencies, osteoporosis, and other" +
                " serious health conditions.",
        R.drawable.ghost
    ),
    UNDERWEIGHT(
        "Underweight",
        "This indicates a very lean Body Mass Index, which means you have a low amount of body fat." +
                "If you are an athlete, this can be desirable. If you are not an athlete, this could mean that your" +
                " weight may be too low. Being underweight can lower your immunity - you should consider gaining" +
                " weight through good diet and exercise habits, to increase your muscle weight.",
        R.drawable.sad
    ),
    NORMAL(
        "Normal",
        "Great! This indicates a good Body Mass Index with the ideal, healthy amount of body fat." +
                " This range is associated with living longest, and the lowest incidence of serious illness." +
                " Coincidentally, it seems this ratio is what many individuals perceive to be the most aesthetically" +
                " pleasing as well.",
        R.drawable.cool
    ),
    OVERWEIGHT(
        "Overweight",
        "This indicates that you are overweight, and have too much body" +
                " fat. You are at increased risk for a variety of illnesses at your present weight. You should attempt to" +
                " find ways to lower your Body Mass Index and the amount of body fat by changing your eating habits and" +
                " exercising more.",
        R.drawable.sad
    ),
    OBESE(
        "Obese",
        "This is a very high Body Mass Index, with an extreme excess of body fat" +
                " that indicates an unhealthy condition. This BMI range is classified by international health organizations" +
                " as 'obese'. You are at risk for heart disease, diabetes, high blood pressure, gall bladder disease," +
                " some cancers, and an increased risk of death from all causes. *phew* You should consult with a physician" +
                " plus consider changing your diet and starting an exercise regime.",
        R.drawable.very_sad
    );

    companion object {
        fun getCategory(bmiValue: Double): BmiCategory {
            return when (bmiValue) {
                in 0.0..14.999 -> EXTREMELY_UNDERWEIGHT
                in 15.0..18.499 -> UNDERWEIGHT
                in 18.5..24.999 -> NORMAL
                in 25.0..29.999 -> OVERWEIGHT
                else -> OBESE
            }
        }

        fun getBmiColor(resources: Resources, bmiValue: Double): Int {
            val bmiCategory = getCategory(bmiValue)
            return when (bmiCategory) {
                EXTREMELY_UNDERWEIGHT -> resources.getColor(R.color.colorBmi_extr_underweight)
                UNDERWEIGHT -> resources.getColor(R.color.colorBmi_underweight)
                NORMAL -> resources.getColor(R.color.colorBmi_normal)
                OVERWEIGHT -> resources.getColor(R.color.colorBmi_overweight)
                OBESE -> resources.getColor(R.color.colorBmi_obese)
            }
        }
    }
}