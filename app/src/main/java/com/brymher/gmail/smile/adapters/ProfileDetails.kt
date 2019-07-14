package com.brymher.gmail.smile.adapters

import android.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.Context
import android.preference.PreferenceManager
import com.brymher.gmail.smile.R
import android.view.LayoutInflater
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.brymher.gmail.smile.BaseActivity
import com.brymher.gmail.smile.models.Details



class ProfileDetails(val context: BaseActivity, val details: Details) :
    RecyclerView.Adapter<ProfileDetails.ViewHolder>() {

    val contents = mapOf(
        Pair("Track", "Android"),
        Pair("Track", "Android"),
        Pair("Country", "Android"),
        Pair("Email", "Android"),
        Pair("Phone Number", "Android"),
        Pair("Slack Username", "Android")

    )


    private val INFLATER = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(INFLATER.inflate(R.layout.profile_item, parent, false))
    }

    override fun getItemCount(): Int = Details.labels.size

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val label = Details.labels[i]
        var content = details[label]
        val hint = Details.hints[label]

        viewHolder.icon.setImageDrawable(context.resources.getDrawable(Details.icons[label] as Int))

        viewHolder.item_name.text = label
        val contentView = viewHolder.content_item
        val edit = viewHolder.edit
        contentView.text = content
        contentView.hint = hint
        val type = Details.types[label] as Int
        contentView.inputType = type

        viewHolder.edit.setOnClickListener {

            val builder = AlertDialog.Builder(context)

            val dialog = builder.create()

            dialog.apply {
                val view = INFLATER.inflate(R.layout.edit_profile, null, false)

                setView(view)

                val editItem = view.findViewById<EditText>(R.id.edit_item)
                val editItemName = view.findViewById<TextView>(R.id.edit_item_name)

                editItemName.text = label

                editItem.inputType = type

                view.findViewById<Button>(R.id.cancel_detail).setOnClickListener {
                    editItem.clearFocus()
                    dismiss()
                }


                val save = view.findViewById<Button>(R.id.save_detail)

                save.setOnClickListener {
                    val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()

                    content = editItem.text.toString()

                    editor.putString(label, content)

                    editor.apply()

                    contentView.text = content

                    dismiss()
                }

                show()

                editItem.requestFocus()
            }
        }


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val content_item = view.findViewById<TextView>(R.id.content_item)
        val item_name = view.findViewById<TextView>(R.id.item_name)
        val icon = view.findViewById<ImageView>(R.id.item_icon)
        val edit = view.findViewById<ImageView>(R.id.item_edit)
    }
}