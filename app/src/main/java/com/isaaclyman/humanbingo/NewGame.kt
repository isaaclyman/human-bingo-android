package com.isaaclyman.humanbingo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup

class NewGame : AppCompatActivity() {
    private var gameModeRadioGroup: RadioGroup? = null
    private var startNewGame: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        gameModeRadioGroup = findViewById(R.id.gameModeRadioGroup)
        gameModeRadioGroup?.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                startNewGame?.isEnabled = true
            } else {
                startNewGame?.isEnabled = false
            }
        })

        startNewGame = findViewById(R.id.startNewGame)
        startNewGame?.setOnClickListener(View.OnClickListener { _ ->
            val startGame = Intent(applicationContext, GameScreen::class.java)
            startGame.putExtra("gameType", getGameMode())
            startActivity(startGame)
        })
    }

    private fun getGameMode(): Int {
        val checkedId = gameModeRadioGroup?.checkedRadioButtonId
        val three = findViewById<RadioButton>(R.id.threeGameRadio).id
        val four = findViewById<RadioButton>(R.id.fourGameRadio).id
        val five = findViewById<RadioButton>(R.id.fiveGameRadio).id

        return when (checkedId) {
            three -> 3
            four -> 4
            five -> 5
            else -> throw Error("Invalid game mode")
        }
    }
}
