package com.example.laby2.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laby2.MainActivity
import com.example.laby2.R
import com.example.laby2.logic.BmiRecord
import kotlinx.android.synthetic.main.activity_history.*


class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.bmi_history_action_bar_title)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val sharedPrefs = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE)
        val entries = sharedPrefs.getStringSet(MainActivity.PREFS_KEY_HISTORY, mutableSetOf())
            .map { entry -> BmiRecord.fromString(entry) }
            .sortedByDescending { entry -> entry.date }


        bmi_historyView.addItemDecoration(
            DividerItemDecoration(
                bmi_historyView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        bmi_historyView.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = HistoryAdapter(entries)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
