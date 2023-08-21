package com.example.frogsocial.User

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class LoginViewModel (private val repository: RegisterRepository, application: Application) :
    AndroidViewModel(application), Observable{

    val users = repository.users

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _navigatetoMovieDetails = MutableLiveData<Boolean>()
    val navigatetoMovieDetails: LiveData<Boolean>
        get() = _navigatetoMovieDetails

    private val _errorToastUsername = MutableLiveData<Boolean>()
    val errortoastUsername: LiveData<Boolean>
        get() = _errorToastUsername

    private val _errorToastInvalidPassword = MutableLiveData<Boolean>()
    val errorToastInvalidPassword: LiveData<Boolean>
        get() = _errorToastInvalidPassword

    fun loginButton(user: String, inputPassword: String) {
            uiScope.launch {
                val usersNames = repository.getUserName(user)
                if (usersNames != null) {
                    if(usersNames.password == inputPassword){
                        _navigatetoMovieDetails.value = true
                    }else{
                        _errorToastInvalidPassword.value = true
                    }
                } else {
                    _errorToastUsername.value = true
                }
            }
        }

    fun doneNavigatingUserDetails() {
        _navigatetoMovieDetails.value = false
    }

    fun donetoastErrorUsername() {
        _errorToastUsername .value = false
    }

    fun donetoastInvalidPassword() {
        _errorToastInvalidPassword .value = false
    }



    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}