package com.example.laby2.bmi_description

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.laby2.MainActivity
import com.example.laby2.R
import com.example.laby2.logic.Bmi
import com.example.laby2.logic.BmiCategory
import kotlinx.android.synthetic.main.activity_about_me.*
import kotlinx.android.synthetic.main.activity_bmi_description.*

class BmiDescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_description)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.bmi_actionbar_title_info)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val bmiValue = intent.getStringExtra(MainActivity.KEY_BMI_VALUE)
        bmi_value.text = bmiValue
        bmi_value.setTextColor(intent.getIntExtra(MainActivity.KEY_BMI_COLOR, Color.BLACK))
        bmi_full_description.text = BmiCategory.getCategory(bmiValue.toDouble()).longDescription
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
