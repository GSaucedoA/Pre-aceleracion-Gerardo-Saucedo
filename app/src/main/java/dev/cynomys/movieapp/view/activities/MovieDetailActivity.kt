package dev.cynomys.movieapp.view.activities

import android.os.Bundle
import com.bumptech.glide.Glide
import dev.cynomys.movieapp.R
import dev.cynomys.movieapp.databinding.ActivityMovieDetailBinding
import dev.cynomys.movieapp.model.Movie
import java.text.SimpleDateFormat

class MovieDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun inflateMovie(movie: Movie) {
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
}