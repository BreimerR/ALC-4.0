package com.brymher.gmail.smile

import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main, "Alc 4.0 Phase 1") {

    override fun init() {
        initNav()
    }


    private fun initNav() {
        about_alc.setOnClickListener {
            startActivity(Intent(this, AboutAlc::class.java))
        }

        profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

}
