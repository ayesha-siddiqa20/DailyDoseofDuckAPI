package com.example.dailyduck

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var quoteText: TextView
    private lateinit var refreshButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        quoteText = findViewById(R.id.quoteText)
        refreshButton = findViewById(R.id.refreshButton)

        fetchQuote()

        refreshButton.setOnClickListener {
            fetchQuote()
        }
    }

    private fun fetchQuote() {
        RetrofitClient.instance.getRandomQuote().enqueue(object : Callback<Quote> {
            override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                if (response.isSuccessful && response.body() != null) {
                    val quote = response.body()!!
                    quoteText.text = "\"${quote.joke}\""
                } else if (response.code() == 429)
                {
                    quoteText.text = "Slow down! Too many requests 🐢"
                } else {
                    quoteText.text = "Failed to load quote"
                }

            }

            override fun onFailure(call: Call<Quote>, t: Throwable) {
                quoteText.text = "Error loading quote"
            }
        })


    }
}