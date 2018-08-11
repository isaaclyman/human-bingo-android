package com.isaaclyman.humanbingo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView

class CreditsScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits_screen)

        findViewById<TextView>(R.id.creditsText).movementMethod = LinkMovementMethod.getInstance()
    }
}
