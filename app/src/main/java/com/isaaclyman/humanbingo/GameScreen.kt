package com.isaaclyman.humanbingo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu

class GameScreen : AppCompatActivity() {
    private val extras = intent.extras

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        // Set up toolbar
        setSupportActionBar(findViewById(R.id.game_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize based on extras
        val gameCode = extras.getString("gameCode")
        val gameMode = extras.getInt("gameMode") as GameMode
        if (gameCode != null) {
            initSharedGame(gameCode)
        } else if (gameMode != null){
            initNewGame(gameMode)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_screen, menu)
        return true
    }

    private fun initSharedGame(code: String) {

    }

    private fun initNewGame(mode: GameMode) {

    }
}
