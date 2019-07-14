package com.brymher.gmail.smile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.brymher.gmail.smile.adapters.ProfileDetails
import kotlinx.android.synthetic.main.activity_profile.*


/*data class Details(val name: String, val email: String, private val trackIndex: Int) {
    val track: String
        get() {
            return tracks[trackIndex]
        }

    companion object {
        val tracks = arrayOf(
            "Android",
            "Web",
            "Google Cloud"
        )
    }
}*/

class Profile : BaseActivity(R.layout.activity_profile, "Profile") {
    override fun init() {
        initDetails()
    }

    private fun initDetails() {

        val context = this


    }


}
