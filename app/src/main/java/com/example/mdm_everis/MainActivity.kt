package com.example.mdm_everis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showToolbar(show: Boolean) {
        if (show) {
            main_toolbar.visibility = View.VISIBLE
            setSupportActionBar(main_toolbar)
            main_toolbar.setNavigationOnClickListener { onBackPressed() }
        } else {
            main_toolbar.visibility = View.GONE
            supportActionBar?.hide()
        }
    }

    fun setTitle(title: String){ supportActionBar?.title = title }
}
