package com.brymher.gmail.smile


import android.content.Context
import android.content.SharedPreferences
import android.preference.Preference
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType

import com.brymher.gmail.smile.adapters.ProfileDetails
import com.brymher.gmail.smile.models.Details

import kotlinx.android.synthetic.main.activity_profile2.*
import kotlinx.android.synthetic.main.content_profile.*
import java.lang.Exception


class ProfileActivity : BaseActivity(R.layout.activity_profile2, "Profile") {


    lateinit var name: String
    lateinit var track: String
    lateinit var country: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var slack_name: String


    val details: Details
        get() = Details(name, email, country, track, phone, slack_name)

    var prefs: SharedPreferences? = null

    override fun init() {
        setSupportActionBar(toolbar)

        initPreference()

        initDetails()

    }

    private fun initPreference() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        name = prefs?.getString(Details.labels[0], "Breimer Radido John") as String
        track = prefs?.getString(Details.labels[1], "Android") as String
        email = prefs?.getString(Details.labels[2], "brymher@gmail.com") as String
        phone = prefs?.getString(Details.labels[3], "+254-725124606") as String
        country = prefs?.getString(Details.labels[4], "Kenya") as String
        slack_name = prefs?.getString(Details.labels[5], "Breimer") as String
    }

    private fun initDetails() {

        val context = this

        user_details.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ProfileDetails(this@ProfileActivity, details)
        }

    }
}
