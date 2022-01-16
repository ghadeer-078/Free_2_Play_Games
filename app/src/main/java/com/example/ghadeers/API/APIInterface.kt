package com.example.ghadeers.API

import com.example.ghadeers.Model.gamesItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("api/games")
    fun getGames(@Query("platform") text : String) : Call<ArrayList<gamesItem>>
}