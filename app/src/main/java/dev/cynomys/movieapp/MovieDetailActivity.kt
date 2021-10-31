package dev.cynomys.movieapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import dev.cynomys.movieapp.databinding.ActivityMovieDetailBinding
import dev.cynomys.movieapp.movie.Movie
import dev.cynomys.movieapp.movie.MovieService
import dev.cynomys.movieapp.movie.NetworkStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import kotlin.reflect.typeOf

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var movie = intent.getSerializableExtra("movie") as Movie

        val service = RetrofitService.instance.create(MovieService::class.java)
        val call = service.getMovie(movie.id, BuildConfig.API_KEY)
        call.enqueue(object : Callback<Movie> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                movie = response.body()!!
                with(binding) {
                    Glide.with(applicationContext)
                        .load("https://image.tmdb.org/t/p/w1280/${movie.backdropImage}")
                        .into(banner)
                    title.text = movie.title

                    val userScore = movie.voteAverage.times(10).toInt()
                    progressBar.progress = userScore
                    averageVotes.text = "$userScore%"

                    genres.text =
                        "${getString(R.string.genres)} ${
                            movie.genres.toString().replace("[", "")
                                .replace("]", "")
                        }"

                    originalLanguage.text =
                        "${getString(R.string.original_language)} ${movie.originalLanguage}"

                    releaseDate.text =
                        getString(R.string.realease_date) + " " + SimpleDateFormat("yyyy MMM d").format(
                            movie.releaseDate
                        )
                    overview.text = movie.overview
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.i("data", t.message.toString() + "adadasd")
            }

        })


    }
}