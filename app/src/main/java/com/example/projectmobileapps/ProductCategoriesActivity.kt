package com.example.projectmobileapps

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectmobileapps.databinding.ActivityProductCategoriesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class Category(
    val id: Int,
    val name: String,
    val image: String
)

class ProductCategoriesActivity : AppCompatActivity(), CategoryAdapter.OnCategoryClickListener {
    private lateinit var binding: ActivityProductCategoriesBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryList: List<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryAdapter = CategoryAdapter(emptyList(), this)
        binding.recyclerViewCategories.adapter = categoryAdapter
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(this)

        fetchCategories()
    }

    private fun fetchCategories() {
        val apiServiceCategory = ApiClientCategory().getService()
        val call = apiServiceCategory.getCategories()

        call.enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {
                    categoryList = response.body() ?: emptyList()
                    categoryAdapter.setCategoryList(categoryList)
                } else {
                    Toast.makeText(this@ProductCategoriesActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Toast.makeText(this@ProductCategoriesActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCategoryClick(category: Category) {
        val intent = Intent(this, ProductListActivity::class.java)
        intent.putExtra("category_id", category.id)
        startActivity(intent)
    }
}
