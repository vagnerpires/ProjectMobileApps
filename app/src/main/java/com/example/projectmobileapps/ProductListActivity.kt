package com.example.projectmobileapps

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectmobileapps.databinding.ActivityProductListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListActivity : AppCompatActivity(), ProductAdapter.OnItemClickListener {
    private lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryId = intent.getIntExtra("category_id", -1)
        if (categoryId == -1) {
            finish()
        }

        val productAdapter = ProductAdapter(emptyList(), this)
        binding.recyclerViewProducts.adapter = productAdapter
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)

        fetchProducts(categoryId, productAdapter)
    }

    private fun fetchProducts(categoryId: Int, productAdapter: ProductAdapter) {
        val apiService = ApiClientProduct().productService
        val call = apiService.getProductsByCategory(categoryId)

        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val productList = response.body() ?: emptyList()
                    productAdapter.setProductList(productList)
                } else {
                    Toast.makeText(this@ProductListActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(this@ProductListActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClick(product: Product) {
        // TODO: Implementar o que deve ser feito ao clicar em um item da lista
    }
}