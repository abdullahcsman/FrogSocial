package com.example.frogsocial.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.frogsocial.Database.AppDatabase
import com.example.frogsocial.R
import com.example.frogsocial.User.LoginViewModel
import com.example.frogsocial.User.LoginViewModelFactory
import com.example.frogsocial.User.RegisterRepository
import com.example.frogsocial.databinding.ActivityLoginBinding
import com.example.frogsocial.utils.Functions
import com.example.frogsocial.utils.InternetOnOff
import com.example.frogsocial.utils.InternetOnOffListener

class LoginActivity : AppCompatActivity(), InternetOnOffListener {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        val application = requireNotNull(this).application
        val dao = AppDatabase.getInstance(application).userDatabaseDao
        val repository = RegisterRepository(dao)
        val factory = LoginViewModelFactory(repository, application)
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        loginBinding.navigate.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        loginBinding.login.setOnClickListener {
            val user = loginBinding.username.text.toString().trim()
            val password = loginBinding.password.text.toString().trim()

            if(user.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_LONG).show()
            } else {
                loginViewModel.loginButton(user, password)
            }
        }

        allObservers()
    }

    private fun allObservers() {
        loginViewModel.errortoastUsername .observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(this, "User doesn't exist, please Register!", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastErrorUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(this, "Please check your Password", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastInvalidPassword()
            }
        })

        loginViewModel.navigatetoMovieDetails.observe(this, Observer { hasFinished->
            if (hasFinished == true){
                finish()
                val intent = Intent(this, MoviesListActivity::class.java)
                startActivity(intent)
                loginViewModel.doneNavigatingUserDetails()
            }
        })
    }

    override fun onInternetChange(InternetState: String?) {
        if (InternetState == "connected") {
            Functions.hideSnackBar()
        } else {
            Functions.snackBarMessage(this, loginBinding.loginView)
        }
    }
}