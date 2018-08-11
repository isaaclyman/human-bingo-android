package com.isaaclyman.humanbingo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var chooseGameModeButton: Button? = null
    private var enterCodeButton: Button? = null
    private var creditsButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chooseGameModeButton = findViewById(R.id.chooseGameMode)
        chooseGameModeButton?.setOnClickListener(View.OnClickListener { _ ->
            val newGameActivity = Intent(applicationContext, NewGame::class.java)
            startActivity(newGameActivity)
        })

        enterCodeButton = findViewById(R.id.enterCode)
        enterCodeButton?.setOnClickListener(View.OnClickListener { _ ->
            val enterCodeActivity = Intent(applicationContext, EnterCode::class.java)
            startActivity(enterCodeActivity)
        })

        creditsButton = findViewById(R.id.creditsButton)
        creditsButton?.setOnClickListener(View.OnClickListener {
            val creditsActivity = Intent(applicationContext, CreditsScreen::class.java)
            startActivity(creditsActivity)
        })

        findViewById<TextView>(R.id.sponsoredBy).movementMethod = LinkMovementMethod.getInstance()
    }
}
