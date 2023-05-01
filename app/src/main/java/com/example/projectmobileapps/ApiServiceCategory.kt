package com.example.projectmobileapps

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val category_id: Int,
    val description: String,
    val image: String
)

interface ApiServiceCategory {
    @GET("categories")
    fun getCategories(): Call<List<Category>>

    @GET("products/category/{id}")
    fun getProductsByCategory(@Path("id") categoryId: Int): Call<List<Product>>
}