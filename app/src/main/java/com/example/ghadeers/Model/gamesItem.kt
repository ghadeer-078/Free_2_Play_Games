package com.example.ghadeers.Model

import com.google.gson.annotations.SerializedName

data class gamesItem(
    var genre: String= "", // Moba
    var platform: String = "", // PC (Windows)
    @SerializedName("short_description")
    var shortDescription : String = "",  //"A free-to-play, co-op action RPG with gameplay similar to Monster Hunter."
    var thumbnail : String = "", //"https://www.freetogame.com/g/1/thumbnail.jpg"
    var title: String = "" // Skydome
)
