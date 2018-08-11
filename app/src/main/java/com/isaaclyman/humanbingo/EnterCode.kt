package com.isaaclyman.humanbingo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText

class EnterCode : AppCompatActivity() {

    private var codeTextBox: EditText? = null
    private var startGame: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_code)

        codeTextBox = findViewById(R.id.codeTextBox)
        codeTextBox?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (codeTextBox?.text.toString().trim().length > 0) {
                    startGame?.isEnabled = true
                } else {
                    startGame?.isEnabled = false
                }
            }
        })

        startGame = findViewById(R.id.startGameWithCode)
        startGame?.setOnClickListener(View.OnClickListener { _ ->
            val startGame = Intent(applicationContext, GameScreen::class.java)
            startGame.putExtra("gameCode", codeTextBox?.text.toString())
            startActivity(startGame)
        })
    }
}
