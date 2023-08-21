package com.example.frogsocial.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.frogsocial.User.Users

@Dao
interface UsersDAO {
    @Insert
    suspend fun insert(register: Users)

    @Query("SELECT * FROM frog_users_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<Users>>

    @Query("DELETE FROM frog_users_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM frog_users_table WHERE user_name LIKE :userName")
    suspend fun getUsername(userName: String): Users?
}