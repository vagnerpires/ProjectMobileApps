package com.example.projectmobileapps

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceProduct {
    @GET("/products")
    fun getAllProducts(): Call<List<Product>>

    @GET("/products/category/{categoryId}")
    fun getProductsByCategory(@Path("categoryId") categoryId: Int): Call<List<Product>>
}