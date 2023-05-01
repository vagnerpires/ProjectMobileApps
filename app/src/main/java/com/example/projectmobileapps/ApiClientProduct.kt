package com.example.projectmobileapps

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClientProduct {
    private val BASE_URL = "https://fakestoreapi.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val productService: ApiServiceProduct = retrofit.create(ApiServiceProduct::class.java)
}