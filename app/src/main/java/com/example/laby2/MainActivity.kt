package com.example.laby2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.example.laby2.about_me.AboutMeActivity
import com.example.laby2.bmi_description.BmiDescriptionActivity
import com.example.laby2.logic.Bmi
import com.example.laby2.logic.BmiCategory
import com.example.laby2.logic.BmiForKgCm
import com.example.laby2.logic.BmiForLbIn
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NullPointerException
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        count_button.setOnClickListener {
            if (verifyData()) {
                val bmiValue = bmi.countBmi(mass_input.text.toString().toInt(), height_input.text.toString().toInt())
                bmi_value_text.text = bmiValue.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toString()
                bmi_short_desc.text = BmiCategory.getCategory(bmiValue).shortDescription
                bmi_value_text.setTextColor(BmiCategory.getBmiColor(resources, bmiValue))
                bmi_description_button.visibility = View.VISIBLE
                bmi_short_desc.visibility = View.VISIBLE
            }
        }

        bmi_description_button.setOnClickListener {
            if (!bmi_value_text.text.equals(getString(R.string.bmi_main_default_bmi_display_value))) {
                val intent = Intent(this@MainActivity, BmiDescriptionActivity::class.java)
                intent.putExtra(KEY_BMI_VALUE, bmi_value_text.text)
                intent.putExtra(KEY_BMI_COLOR, bmi_value_text.currentTextColor)
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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putCharSequence(KEY_BMI_VALUE, bmi_value_text.text)
        outState?.putInt(KEY_BMI_COLOR, bmi_value_text.currentTextColor)
        outState?.putCharSequence(KEY_BMI_SHORT_DESC, bmi_short_desc.text)
        outState?.putInt(KEY_BMI_SHORT_DESC_VISIBILITY, bmi_short_desc.visibility)
        outState?.putInt(KEY_BMI_DESC_BUTTON_VISIBILITY, bmi_description_button.visibility)
        outState?.putCharSequence(KEY_MASS_TEXT, mass_text_view.text)
        outState?.putCharSequence(KEY_HEIGHT_TEXT, height_text_view.text)
        outState?.putCharSequence(KEY_HEIGHT_TEXT, height_text_view.text)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState == null) throw NullPointerException(NULL_BUNDLE_MESSAGE)

        bmi_value_text.text = savedInstanceState.getCharSequence(KEY_BMI_VALUE)
        bmi_value_text.setTextColor(savedInstanceState.getInt(KEY_BMI_COLOR))
        bmi_short_desc.text = savedInstanceState.getCharSequence(KEY_BMI_SHORT_DESC)
        bmi_short_desc.visibility = savedInstanceState.getInt(KEY_BMI_SHORT_DESC_VISIBILITY)
        bmi_description_button.visibility = savedInstanceState.getInt(KEY_BMI_DESC_BUTTON_VISIBILITY)
        mass_text_view.text = savedInstanceState.getCharSequence(KEY_MASS_TEXT)
        height_text_view.text = savedInstanceState.getCharSequence(KEY_HEIGHT_TEXT)
    }

    private fun switchUnits() {
        if (metricUnits) {
            mass_text_view.text = getString(R.string.bmi_main_mass_lb)
            height_text_view.text = getString(R.string.bmi_main_height_in)
            bmi = BmiForLbIn()
        } else {
            mass_text_view.text = getString(R.string.bmi_main_mass_kg)
            height_text_view.text = getString(R.string.bmi_main_height_cm)
            bmi = BmiForKgCm()
        }
        mass_input.text.clear()
        height_input.text.clear()
        metricUnits = !metricUnits
    }

    /**
     * verifies input data and sets errors if present
     * @return is data valid
     */
    private fun verifyData(): Boolean {
        var valid = verifyMass()
        valid = verifyHeight() && valid
        return valid
    }

    private fun verifyMass(): Boolean {
        if (mass_input.text.isBlank()) {
            mass_input.error = getString(R.string.bmi_empty_mass_error)
            return false
        } else if (!bmi.isMassInRange(mass_input.text.toString().toInt())) {
            val range = bmi.getMassRange()
            mass_input.error = getString(R.string.mass_range_error, range.first, range.second)
            return false
        }
        return true
    }

    private fun verifyHeight(): Boolean {
        if (height_input.text.isBlank()) {
            height_input.error = getString(R.string.bmi_empty_height_error)
            return false
        } else if (!bmi.isHeightInRange(height_input.text.toString().toInt())) {
            val range = bmi.getHeightRange()
            height_input.error = getString(R.string.height_range_error, range.first, range.second)
            return false
        }
        return true
    }

    companion object {
        private var metricUnits = true
        private var bmi: Bmi = BmiForKgCm()

        const val NULL_BUNDLE_MESSAGE = "null bundle in onRestoreInstanceState()"
        const val KEY_BMI_VALUE = "bmiValue"
        const val KEY_BMI_COLOR = "bmiColor"
        const val KEY_BMI_SHORT_DESC = "bmiShortDesc"
        const val KEY_BMI_SHORT_DESC_VISIBILITY = "bmiShortDescVisibility"
        const val KEY_BMI_DESC_BUTTON_VISIBILITY = "bmiDescButtonVisibility"
        const val KEY_MASS_TEXT = "massText"
        const val KEY_HEIGHT_TEXT = "heightText"
    }
}
