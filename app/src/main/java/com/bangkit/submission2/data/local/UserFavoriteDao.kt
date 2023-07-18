package com.bangkit.submission2.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface UserFavoriteDao {
    @Insert
    fun addToFavorite(favorite: UserFavorite)

    @Query("SELECT * FROM user_favorite")
    fun getUserFavorite() : LiveData<List<UserFavorite>>

    @Query("SELECT count(*) FROM user_favorite WHERE user_favorite.id = :id")
    fun check(id: Int): Int

    @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
    fun removeFavorite(id: Int): Int
}