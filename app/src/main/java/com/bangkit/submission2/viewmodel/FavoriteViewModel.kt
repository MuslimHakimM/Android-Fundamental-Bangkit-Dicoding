package com.bangkit.submission2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bangkit.submission2.data.local.UserDatabase
import com.bangkit.submission2.data.local.UserFavorite
import com.bangkit.submission2.data.local.UserFavoriteDao

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao : UserFavoriteDao?
    private var userDb : UserDatabase?

    init {
        userDb = UserDatabase.getDb(application)
        userDao = userDb?.userFavoriteDao()
    }

    fun getFavoriteUser() : LiveData<List<UserFavorite>>?{
        return userDao?.getUserFavorite()
    }
}