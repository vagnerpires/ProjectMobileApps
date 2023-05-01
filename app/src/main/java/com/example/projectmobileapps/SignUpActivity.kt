package com.example.projectmobileapps

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmobileapps.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = binding.editTextUsername.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (TextUtils.isEmpty(username)) {
            binding.editTextUsername.error = "Please enter a username."
            return
        }

        if (TextUtils.isEmpty(password)) {
            binding.editTextPassword.error = "Please enter a password."
            return
        }

        Toast.makeText(this, "User successfully registered!", Toast.LENGTH_SHORT).show()

        finish()
    }
}