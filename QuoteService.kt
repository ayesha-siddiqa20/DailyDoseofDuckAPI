package com.example.dailyduck

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface QuoteService {
    @Headers(
        "Accept: application/json",
        "User-Agent: DailyDoseOfDuck (https://yourappwebsite.com or your@email.com)"
    )

    @GET("/")
    fun getRandomQuote(): Call<Quote>

}