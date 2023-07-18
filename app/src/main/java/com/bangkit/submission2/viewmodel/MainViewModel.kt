package com.bangkit.submission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bangkit.submission2.data.local.SettingPreferences
import com.bangkit.submission2.data.model.User
import com.bangkit.submission2.data.model.UserResponse
import com.bangkit.submission2.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel (private val pref: SettingPreferences) : ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val listUser = MutableLiveData<ArrayList<User>>()

    fun getTheme() = pref.getThemeSettings().asLiveData()

    fun setSearchUser(query : String){
        _isLoading.value = true
        ApiConfig.apiInstance
            .searchuser(query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.d("Error", t.message.toString())
                }

            })
    }

    fun getUser() : LiveData<ArrayList<User>>{
        return listUser
    }

    class Factory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(pref) as T
    }
}