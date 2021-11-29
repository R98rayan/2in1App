package com.example.a2in1app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView

class GuessThePhraseActivity : AppCompatActivity() {

    lateinit var myButton: Button
    lateinit var textInput: TextView
    lateinit var blackText: TextView
    lateinit var outText: TextView
    lateinit var wonText: TextView

    private lateinit var sharedPreferences: SharedPreferences

    var phrase = "Hello World"
    var stars = ""
    var chars = ArrayList<Char>()
    var guess = 10
    var wins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_the_phrase)

        setTitle("Guess the Phrase")

        myButton = findViewById(R.id.submitButton)
        myButton.setOnClickListener{ guessText() }

        textInput = findViewById(R.id.textInput)
        blackText = findViewById(R.id.blackText)
        outText = findViewById(R.id.outText)
        wonText = findViewById(R.id.wonText)
        outText.visibility = View.INVISIBLE

        createStars()

        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        wins = sharedPreferences.getInt("wins", 0).toInt()  // --> retrieves data from Shared Preferences
        wonText.text = "your wins score is $wins"

    }

    private fun guessText() {
        if(textInput.text.toString() == "") return
        if(textInput.text.toString().length == 1){
            showLetter(textInput.text.toString().toCharArray()[0])
        }
        else {
            if(textInput.text.toString().trim().equals(phrase, true)){
                wins++
                restart("You won!\ntry again?")
                wonText.text = "your wins score is $wins"
            }
            else {
                guess--
                if(guess == 0) {
                    restart("You lose!\ntry again")
                }
                outText.text = "$guess guesses remaining"
            }
        }
        outText.visibility = View.VISIBLE
    }

    private fun restart(outputText: String){
        createStars()
        chars.clear()
        guess == 10
        outText.text = outputText
        updateBlackText()
        textInput.text = ""

        // We can save data with the following code
        with(sharedPreferences.edit()) {
            putInt("wins", wins)
            apply()
        }
    }

    private fun showLetter(letter: Char){
        val realChars = phrase.toCharArray()

        var i = 0
        for (char in realChars) {
            if(char.equals(letter, true)) {
                val starChars = stars.toCharArray()
                starChars[i] = char
                stars = String(starChars)

                val fixedChar = char.toUpperCase()
                if(!chars.contains(fixedChar)) chars.add(fixedChar)
            }
            i++
        }

        updateBlackText()

        guess--
        if(guess == 0) {
            restart("You lose!\ntry again")
        }
        else outText.text = "$guess guesses remaining"
    }

    private fun updateBlackText(){
        blackText.text = "$stars\n Guessed Letters:"
        for (char in chars) {
            blackText.text = "${blackText.text} $char"
        }
    }

    private fun createStars(){
        val chars = phrase.toCharArray()
        var i = 0
        for (index in phrase.indices) {
            if(chars[index] != ' ') chars[index] = '*'
        }
        stars = String(chars)
        updateBlackText()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_g, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_g_newGame -> {

            }
            R.id.menu_g_numbersGame -> {
                val intent = Intent(this, NumbersGameActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_g_mainMenu -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}