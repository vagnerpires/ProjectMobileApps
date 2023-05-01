package com.example.projectmobileapps

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import com.example.projectmobileapps.databinding.ActivityMainBinding
import kotlinx.android.parcel.Parcelize
import retrofit2.Call


@Parcelize
data class User(
    val username: String,
    val password: String,
    val id: Int = 0
) : Parcelable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("myapp", Context.MODE_PRIVATE)

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            } else {
                authenticate(username, password)
            }
        }

        val isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN", false)

        if (isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun authenticate(username: String, password: String) {
        val apiClient = ApiClient()
        val call = apiClient.getService().authenticateUser(username, password)

        call.enqueue(object : retrofit2.Callback<User> {
            override fun onResponse(call: retrofit2.Call<User>, response: retrofit2.Response<User>) {
                val user = response.body()

                if (user != null) {
                    saveUserSession(user.id)
                    val intent = Intent(applicationContext, ProductCategoriesActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Login failed.", Toast.LENGTH_SHORT).show()
            }
        })

        }

    private fun saveUserSession(userId: Int) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("IS_LOGGED_IN", true)
        editor.putInt("USER_ID", userId)
        editor.apply()
        }
}