package dev.cynomys.movieapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: Date,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("poster_path") val posterImage: String,
    @SerializedName("backdrop_path") val backdropImage: String,
    @SerializedName("genres") val genres: ArrayList<Genre>,
    @SerializedName("original_language") val originalLanguage: String
) : Serializable {
    override fun toString(): String {
        return "${this.id} ${this.title}"
    }
}