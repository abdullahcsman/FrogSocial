package com.example.frogsocial.User

import android.util.Log
import com.example.frogsocial.Database.UsersDAO

class RegisterRepository(private val dao: UsersDAO) {
    val users = dao.getAllUsers()
    suspend fun insert(user: Users) {
        return dao.insert(user)
    }

    suspend fun getUserName(userName: String): Users?{
        Log.d("FrogApplication", "Getting Users")
        return dao.getUsername(userName)
    }
}