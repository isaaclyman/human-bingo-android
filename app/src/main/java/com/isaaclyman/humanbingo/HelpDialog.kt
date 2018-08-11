package com.isaaclyman.humanbingo

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.content.DialogInterface
import android.app.AlertDialog

class HelpDialog : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflator = activity?.layoutInflater
        builder.setTitle("Instructions")
                .setView(inflator!!.inflate(R.layout.help_dialog, null))
                .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { _, _ ->
                    // FIRE ZE MISSILES!
                })
        return builder.create()
    }
}