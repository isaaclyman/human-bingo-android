package com.isaaclyman.humanbingo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu

class GameScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)
        setSupportActionBar(findViewById(R.id.game_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_screen, menu)
        return true
    }
}
