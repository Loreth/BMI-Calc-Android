package com.example.laby2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.laby2.aboutme.AboutMeActivity
import com.example.laby2.bmidescription.BmiDescriptionActivity
import com.example.laby2.history.HistoryActivity
import com.example.laby2.logic.*
import kotlinx.android.synthetic.main.activity_main.*
import java.math.RoundingMode
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        count_button.setOnClickListener {
            if (verifyData()) {
                val bmiValue = bmi.countBmi(
                    mass_input.text.toString().toInt(),
                    height_input.text.toString().toInt()
                )
                bmi_value_text.text = bmiValue.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toString()
                bmi_short_desc.text = BmiCategory.getCategory(bmiValue).shortDescription
                bmi_value_text.setTextColor(BmiCategory.getBmiColor(resources, bmiValue))

                bmi_description_button.visibility = View.VISIBLE
                bmi_short_desc.visibility = View.VISIBLE

                saveEntryToSharedPrefs(newHistoryEntry())
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
        R.id.action_history -> {
            startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
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
        menu.findItem(R.id.action_history).isEnabled = prefs.contains(PREFS_KEY_HISTORY)

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
        } else if (!bmi.getWeightRange().contains(mass_input.text.toString().toInt())) {
            val range = bmi.getWeightRange()
            mass_input.error = getString(R.string.mass_range_error, range.start, range.endInclusive)
            return false
        }
        return true
    }

    private fun verifyHeight(): Boolean {
        if (height_input.text.isBlank()) {
            height_input.error = getString(R.string.bmi_empty_height_error)
            return false
        } else if (!bmi.getHeightRange().contains(height_input.text.toString().toInt())) {
            val range = bmi.getHeightRange()
            height_input.error = getString(R.string.height_range_error, range.start, range.endInclusive)
            return false
        }
        return true
    }

    private fun saveEntryToSharedPrefs(entry: BmiRecord) {
        val oldHistory = prefs.getStringSet(PREFS_KEY_HISTORY, mutableSetOf())
            .map { string -> BmiRecord.fromString(string) }
            .sortedByDescending { record -> record.date }
            .take(MAX_HISTORY_ENTRY_NUMBER - 1)
            .toMutableSet()

        oldHistory.add(entry)

        val newHistory = oldHistory
            .map { record -> record.toString() }
            .toSortedSet(naturalOrder())

        prefs.edit().putStringSet(PREFS_KEY_HISTORY, newHistory).apply()
    }

    private fun newHistoryEntry(): BmiRecord {
        return BmiRecord(
            "${mass_text_view.text} ${mass_input.text}",
            "${height_text_view.text} ${height_input.text}",
            bmi_value_text.text.toString(),
            bmi_short_desc.text.toString(),
            bmi_value_text.currentTextColor,
            Date()
        )
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

        const val PREFS_NAME = "prefs"
        const val PREFS_KEY_HISTORY = "bmiHistory"
        const val MAX_HISTORY_ENTRY_NUMBER = 10
    }
}
