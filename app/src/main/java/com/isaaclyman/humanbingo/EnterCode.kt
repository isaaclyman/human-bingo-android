package com.isaaclyman.humanbingo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class EnterCode : AppCompatActivity() {

    private var codeTextBox: EditText? = null
    private var startGame: Button? = null
    private var codeWarning: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_code)

        codeTextBox = findViewById(R.id.codeTextBox)
        codeTextBox?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = codeTextBox?.text.toString().trim()
                if (text.length > 0) {
                    val isValid = validateCode(text)
                    if (!isValid) {
                        codeWarning?.text = "Invalid code"
                    } else {
                        codeWarning?.text = ""
                    }
                    startGame?.isEnabled = isValid
                } else {
                    codeWarning?.text = ""
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

        codeWarning = findViewById(R.id.codeWarningText)
    }

    private fun validateCode(code: String): Boolean {
        val split = code.split(GameBoard.codeSeparator)
        if (!arrayOf(9, 16, 25).contains(split.size)) {
            return false
        }

        val numsOrNull = split.map { it.toIntOrNull() }
        if (numsOrNull.contains(null)) {
            return false
        }

        val nums = numsOrNull.filterNotNull()
        val outOfBounds = nums.any { it > PeopleSquares.squares.lastIndex }
        if (outOfBounds) {
            return false
        }

        val distinctNums = nums.distinct()
        if (distinctNums.size != nums.size) {
            return false
        }

        return true
    }
}
