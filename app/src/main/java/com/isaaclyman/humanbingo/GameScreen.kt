package com.isaaclyman.humanbingo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.support.v7.widget.GridLayout

class GameScreen : AppCompatActivity() {
    private var grid: GridLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        // Set up toolbar
        setSupportActionBar(findViewById(R.id.game_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get elements
        grid = findViewById(R.id.grid)

        // Initialize based on extras
        val extras = intent.extras
        val gameCode = extras.getString("gameCode")
        val gameMode = extras.getSerializable("gameMode")
        if (gameCode != null) {
            initSharedGame(gameCode)
        } else if (gameMode != null){
            initNewGame(gameMode as GameMode)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_menu, menu)
        return true
    }

    private fun initSharedGame(code: String) {
        GameBoard(this, grid!!, null, code)
    }

    private fun initNewGame(mode: GameMode) {
        GameBoard(this, grid!!, mode, null)
    }
}
