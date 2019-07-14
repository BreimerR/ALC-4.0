package com.brymher.gmail.smile.models

import android.text.InputType
import com.brymher.gmail.smile.R
import java.lang.Exception


class Details(
    val name: String,
    val email: String,
    val country: String,
    val track: String,
    val phone: String,
    val slack_name: String
) {

    companion object {
        public val labels = arrayOf(
            "Name",
            "Track",
            "Email",
            "Phone",
            "Country",
            "Slack @Username"
        )

        val hints = mapOf(
            Pair(labels[0], "Your Full Name"),
            Pair(labels[1], "Android,Cloud or Web"),
            Pair(labels[2], "user@domain.com"),
            Pair(labels[3], "+@254-700000000"),
            Pair(labels[4], "Kenya..."),
            Pair(labels[5], "@slack.username")
        )

        val types = mapOf(
            Pair(labels[0], InputType.TYPE_CLASS_TEXT),
            Pair(labels[1], InputType.TYPE_CLASS_TEXT),
            Pair(labels[2], InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS),
            Pair(labels[3], InputType.TYPE_CLASS_PHONE),
            Pair(labels[4], InputType.TYPE_CLASS_TEXT),
            Pair(labels[5], InputType.TYPE_CLASS_TEXT)
        )

        val icons = mapOf<String, Int>(
            Pair(labels[0], R.drawable.user),
            Pair(labels[1], R.drawable.path),
            Pair(labels[2], android.R.drawable.ic_dialog_email),
            Pair(labels[3], R.drawable.contacts_ico),
            Pair(labels[4], android.R.drawable.ic_dialog_map),
            Pair(labels[5], R.drawable.slack)
        )

    }

    operator fun get(key: String): String? = try {
        when (key) {
            labels[0] -> name
            labels[1] -> track
            labels[2] -> email
            labels[3] -> phone
            labels[4] -> country
            labels[5] -> slack_name
            else -> null
        }
    } catch (e: Exception) {
        null
    }

}
