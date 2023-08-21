package com.example.frogsocial.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.frogsocial.Database.AppDatabase
import com.example.frogsocial.User.RegisterRepository
import com.example.frogsocial.User.UserViewModel
import com.example.frogsocial.User.UserViewModelFactory
import com.example.frogsocial.User.Users
import com.example.frogsocial.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var registerViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val application = requireNotNull(this).application
        val dao = AppDatabase.getInstance(application).userDatabaseDao
        val repository = RegisterRepository(dao)
        val factory = UserViewModelFactory(repository, application)
        registerViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        binding.signup.setOnClickListener {
            submitBtn()
        }

        binding.back.setOnClickListener{
            finish()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerViewModel.navigateto.observe(this, Observer { hasFinished->
            if (hasFinished == true){
                Toast.makeText(this, "User registered Successfully", Toast.LENGTH_LONG).show()
                finish()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        })

            registerViewModel.errotoastUsername.observe(this, Observer { hasError->
                if(hasError==true){
                    Toast.makeText(this, "This user already exists", Toast.LENGTH_SHORT).show()
                    registerViewModel.donetoastUserName()
                }
            })

    }

    private fun submitBtn () {
        val username = binding.username.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val confirmPass = binding.passwordagain.text.toString().trim()

        if (username.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_LONG).show()
        } else if (password.length < 6) {
            Toast.makeText(this, "Password length should be greater than 6", Toast.LENGTH_LONG).show()

        } else if (password != confirmPass) {
            Toast.makeText(this, "Password and confirm password should be equal", Toast.LENGTH_LONG).show()
        } else {
            registerViewModel.submitButton(username, password)
        }

    }
}