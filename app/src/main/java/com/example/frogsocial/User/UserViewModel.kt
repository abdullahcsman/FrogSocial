package com.example.frogsocial.User

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.example.frogsocial.FrogApplication
import com.example.frogsocial.User.Users

class UserViewModel (private val repository: RegisterRepository, application: Application) :
    AndroidViewModel(application), Observable {

    init {
        Log.d("FrogApplication", "init")
    }

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _navigateto = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    private val _errorToast = MutableLiveData<Boolean>()

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errotoastUsername: LiveData<Boolean>
        get() = _errorToastUsername


    fun submitButton(user: String, password: String) {
        uiScope.launch {
            val usersNames = repository.getUserName(user)
            Log.d("FrogApplication", usersNames.toString())
            if (usersNames != null) {
                _errorToastUsername.value = true
                Log.d("FrogApplication", "Inside if Not null")
            } else {
                Log.d("FrogApplication", "User Successful")
                insert(Users(0, user, password))
                _navigateto.value = true
            }
        }

    }


    override fun onCleared() {
        super.onCleared()
    }

    fun donetoastUserName() {
        _errorToast.value = false
        Log.d("FrogApplication", "Username issue")
    }

    private fun insert(user: Users): Job = viewModelScope.launch {
        repository.insert(user)
    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}