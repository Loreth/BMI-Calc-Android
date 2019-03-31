package com.example.laby2.aboutme

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.laby2.R
import kotlinx.android.synthetic.main.activity_about_me.*

class AboutMeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.bmi_about_me_actionbar_title)
        actionbar.setDisplayHomeAsUpEnabled(true)

        dare_button.setOnClickListener {
            Toast.makeText(applicationContext, getString(R.string.bmi_about_me_button_toast_text), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
