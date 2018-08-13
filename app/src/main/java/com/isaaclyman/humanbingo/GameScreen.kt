package com.isaaclyman.humanbingo

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.support.v7.widget.GridLayout
import android.view.MenuItem
import android.widget.TextView
import kotlin.math.sqrt

class GameScreen : AppCompatActivity() {
    private var grid: GridLayout? = null
    private var board: GameBoard? = null
    private var boardSize: Int? = null
    private var bingoAnnounce: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        // Set up toolbar
        setSupportActionBar(findViewById(R.id.game_toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Get elements
        grid = findViewById(R.id.grid)
        bingoAnnounce = findViewById(R.id.bingoAnnounce)

        // Initialize based on extras
        val extras = intent.extras
        val gameCode = extras?.getString("gameCode")
        val gameMode = extras?.getSerializable("gameMode")
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when(item?.itemId) {
        R.id.action_help -> {
            HelpDialog().show(supportFragmentManager, "help")
            true
        }
        R.id.action_invite -> {
            val dialog = InviteDialog.newInstance(board?.getCode() ?: "")
            dialog.show(supportFragmentManager, "invite")
            true
        }
        R.id.action_new_board -> {
            board?.newBoard(boardSize ?: 3)
            true
        }
        android.R.id.home -> {
            NavUtils.navigateUpFromSameTask(this)
            true
        }
        else -> {
            true
        }
    }

    private fun initSharedGame(code: String) {
        board = GameBoard(this, grid!!, bingoAnnounce!!, null, code)
        boardSize = sqrt(board?.peopleIndexes?.size?.toDouble() ?: 9.0).toInt()
    }

    private fun initNewGame(mode: GameMode) {
        boardSize = mode.value
        board = GameBoard(this, grid!!, bingoAnnounce!!, mode, null)
    }
}
