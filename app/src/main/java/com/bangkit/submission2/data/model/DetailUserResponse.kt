package com.bangkit.submission2.data.model

data class DetailUserResponse (
    val login : String,
    val id : Int,
    val name : String,
    val avatar_url : String,
    val followers_url : String,
    val following_url :  String,
    val following : Int,
    val followers : Int
)