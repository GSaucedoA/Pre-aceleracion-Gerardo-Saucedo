package dev.cynomys.movieapp.movie

import com.google.gson.annotations.SerializedName
import dev.cynomys.movieapp.BuildConfig
import dev.cynomys.movieapp.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class Genre(val id: Int, val name: String) {
    override fun toString(): String {
        return this.name
    }
}

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

data class MovieListResponse(@SerializedName("results") val results: List<Movie>)

interface MovieService {
    @GET("movie/popular")
    fun getMovieList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<MovieListResponse>

    @GET("movie/{id}")
    fun getMovie(@Path("id") id: Int, @Query("api_key") apiKey: String): Call<Movie>
}

interface NetworkResponse<T> {
    fun onResponse(value: Resource<T>)
}

enum class NetworkStatus {
    SUCCESS,
    LOADING,
    ERROR
}

data class Resource<out T>(
    val status: NetworkStatus,
    val data: T? = null,
    val message: String? = null
)

class MovieRemoteDataSource {
    fun getMovieList(networkResponse: NetworkResponse<List<Movie>>, page: Int) {
        val service = RetrofitService.instance.create(MovieService::class.java)
            .getMovieList(BuildConfig.API_KEY, page)

        service.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                val resource = response.body()?.run {
                    if (results.isNotEmpty())
                        Resource(NetworkStatus.SUCCESS, results)
                    else
                        Resource(NetworkStatus.ERROR)
                } ?: run {
                    Resource(NetworkStatus.ERROR)
                }
                networkResponse.onResponse(resource)
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                networkResponse.onResponse(Resource(NetworkStatus.ERROR, message = t.message))
            }
        })
    }
}

class MovieRepository(private val remoteDataSource: MovieRemoteDataSource) {
    fun getMovieList(networkResponse: NetworkResponse<List<Movie>>, page: Int) {
        return remoteDataSource.getMovieList(networkResponse, page)
    }
}