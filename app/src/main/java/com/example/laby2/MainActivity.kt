package com.example.laby2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.laby2.logic.BmiForKgCm
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countButton.setOnClickListener {
            if (isDataValid()) {
                val bmi = BmiForKgCm(massInput.text.toString().toInt(), heightInput.text.toString().toInt())
                val bmiValue = bmi.countBmi()
                val df = DecimalFormat("#0.00")
                bmiValueTextV.text = df.format(bmiValue).toString()
                bmiDescription.text = bmi.oneWordBmiDescription(bmiValue)
                bmiValueTextV.setTextColor(getBmiColor(bmiValue))
            } else {
                showDataErrors()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bmi_menu, menu)
        return true
    }

    fun isDataValid(): Boolean {
        return massInput.text.isNotBlank() && heightInput.text.isNotBlank()
    }

    private fun showDataErrors() {
        if (massInput.text.isBlank()) massInput.error = "Mass input is blank!"
        if (heightInput.text.isBlank()) heightInput.error = "Height input is blank!"
    }

    private fun getBmiColor(bmiValue: Double): Int {
        val colorHexString = when (bmiValue) {
            in 0.0..18.999 -> "#3366CC"
            in 19.0..24.999 -> "#00A693"
            in 25.0..29.999 -> "#ff9933"
            in 30.0..39.999 -> "#B80000"
            else -> "#993399"
        }

        return Color.parseColor(colorHexString)
    }
}
