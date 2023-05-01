package com.example.projectmobileapps

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClientCategory {

    private val BASE_URL = "https://fakestoreapi.com/"

    fun getService(): ApiServiceCategory {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiServiceCategory::class.java)
    }
}