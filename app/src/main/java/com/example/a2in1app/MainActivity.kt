package com.example.a2in1app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var numbersButton: Button
    lateinit var guessThePhraseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numbersButton = findViewById(R.id.nButton)
        guessThePhraseButton = findViewById(R.id.gButton)

        numbersButton.setOnClickListener {
            val intent = Intent(this, NumbersGameActivity::class.java)
            startActivity(intent)
        }

        guessThePhraseButton.setOnClickListener {
            val intent = Intent(this, GuessThePhraseActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_n -> {
                val intent = Intent(this, NumbersGameActivity::class.java)
                startActivity(intent)
            }
            R.id.item_g -> {
                val intent = Intent(this, GuessThePhraseActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}