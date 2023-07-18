package com.bangkit.submission2.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.submission2.data.local.UserDatabase
import com.bangkit.submission2.data.local.UserFavorite
import com.bangkit.submission2.data.local.UserFavoriteDao
import com.bangkit.submission2.data.model.DetailUserResponse
import com.bangkit.submission2.data.remote.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    private var userDao : UserFavoriteDao?
    private var userDb : UserDatabase?

    init {
        userDb = UserDatabase.getDb(application)
        userDao = userDb?.userFavoriteDao()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setDetailUser(username: String){
        _isLoading.value = true
        ApiConfig.apiInstance
            .detailuser(username)
            .enqueue(object : Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    t.message?.let { Log.d("Error", it) }
                }

            })
    }

    fun getDetailUser() : LiveData<DetailUserResponse>{
        return user
    }

    fun addToFavorite(username: String, id: Int, avatarUrl : String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = UserFavorite(
                username, id, avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userDao?.check(id)

    fun removeFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavorite(id)
        }
    }
}