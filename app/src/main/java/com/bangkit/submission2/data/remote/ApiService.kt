package com.bangkit.submission2.data.remote

import com.bangkit.submission2.data.model.DetailUserResponse
import com.bangkit.submission2.data.model.User
import com.bangkit.submission2.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: Token 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")
    @GET("search/users")
    fun searchuser(
        @Query("q") query: String
    ): Call<UserResponse>

    @Headers("Authorization: Token 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")
    @GET("users/{username}")
    fun detailuser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @Headers("Authorization: Token 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")
    @GET("users/{username}/followers")
    fun getfollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @Headers("Authorization: Token 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")
    @GET("users/{username}/following")
    fun getfollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}