package com.isaaclyman.humanbingo

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.content.DialogInterface
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView

class InviteDialog : DialogFragment() {
    companion object {
        val INVITE_CODE = "inviteCode"

        fun newInstance(inviteCode: String): InviteDialog {
            val instance = InviteDialog()
            val args = Bundle()
            args.putString(INVITE_CODE, inviteCode)
            instance.arguments = args
            return instance
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflator = activity?.layoutInflater
        val dialogView = inflator!!.inflate(R.layout.invite_dialog, null)
        val inviteCode = arguments?.getString(INVITE_CODE)

        dialogView.findViewById<TextView>(R.id.invite_code).text = inviteCode

        val copyButton = dialogView.findViewById<Button>(R.id.copy_button)
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val copiedNotification = dialogView.findViewById<TextView>(R.id.copied_notification)
        copyButton.setOnClickListener(View.OnClickListener {
            val clip = ClipData.newPlainText("InviteCode", inviteCode)
            clipboard.primaryClip = clip
            copiedNotification.visibility = View.VISIBLE
        })

        builder.setTitle("Invite a friend")
                .setView(dialogView)
                .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { _, _ ->
                    copiedNotification.visibility = View.GONE
                    // close dialog
                })
        return builder.create()
    }
}