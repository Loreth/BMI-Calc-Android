package com.example.laby2

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.laby2.about_me.AboutMeActivity
import com.example.laby2.bmi_description.BmiDescriptionActivity
import com.example.laby2.logic.Bmi
import com.example.laby2.logic.BmiForKgCm
import com.example.laby2.logic.BmiForLbIn
import kotlinx.android.synthetic.main.activity_main.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var metricUnits = true
    private var bmi: Bmi = BmiForKgCm()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countButton.setOnClickListener {
            if (isDataValid()) {
                val bmiValue = bmi.countBmi(massInput.text.toString().toInt(), heightInput.text.toString().toInt())
                bmiValueTextV.text = bmiValue.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toString()
                bmiDescription.text = bmi.oneWordBmiDescription(resources, bmiValue)
                bmiValueTextV.setTextColor(bmi.getBmiColor(resources, bmiValue))
            } else {
                showDataErrors()
            }
        }

        bmi_description_button.setOnClickListener {
            if (!bmiValueTextV.text.equals(getString(R.string.bmi_main_default_bmi_display_value))) {
                val intent = Intent(this@MainActivity, BmiDescriptionActivity::class.java)
                intent.putExtra("bmiValue", bmiValueTextV.text.toString())
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bmi_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_change_units -> {
            switchUnits()
            true
        }
        R.id.action_about_me -> {
            startActivity(Intent(this@MainActivity, AboutMeActivity::class.java))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val unitsMenuItem: MenuItem = menu!!.findItem(R.id.action_change_units)
        if (metricUnits) unitsMenuItem.title = getString(R.string.bmi_menu_imperial_units)
        else unitsMenuItem.title = getString(R.string.bmi_menu_metric_units)

        return super.onPrepareOptionsMenu(menu)
    }

    fun switchUnits() {
        if (metricUnits) {
            massTextView.text = getString(R.string.bmi_main_mass_lb)
            heightTextView.text = getString(R.string.bmi_main_height_in)
            bmi = BmiForLbIn()
        } else {
            massTextView.text = getString(R.string.bmi_main_mass_kg)
            heightTextView.text = getString(R.string.bmi_main_height_cm)
            bmi = BmiForKgCm()
        }
        massInput.text.clear()
        heightInput.text.clear()
        metricUnits = !metricUnits
    }

    fun isDataValid(): Boolean {
        return massInput.text.isNotBlank() && heightInput.text.isNotBlank()
    }

    private fun showDataErrors() {
        if (massInput.text.isBlank()) massInput.error = getString(R.string.bmi_empty_mass_error)
        if (heightInput.text.isBlank()) heightInput.error = getString(R.string.bmi_empty_height_error)
    }
}
