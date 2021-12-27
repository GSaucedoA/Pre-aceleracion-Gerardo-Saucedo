package dev.cynomys.movieapp.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.cynomys.movieapp.BuildConfig
import dev.cynomys.movieapp.R
import dev.cynomys.movieapp.databinding.ActivityMovieDetailBinding
import dev.cynomys.movieapp.model.FullMovie
import dev.cynomys.movieapp.viewmodel.MovieDetailViewModel
import java.text.DateFormat

@AndroidEntryPoint
class MovieDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        attachProgressBar(binding.rootView)

        setUpObservers()
        callViewModelMethods()
    }

    private fun setUpObservers() {
        viewModel.movie.observe(this) {
            inflateMovie(it)
        }
        viewModel.loadingState.observe(this) {
            if (it) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
        }
    }

    private fun callViewModelMethods() {
        val id = intent.getIntExtra("id", -1)
        viewModel.fetchMovie(id)
    }

    private fun inflateMovie(movie: FullMovie) {
        with(binding) {
            Glide.with(binding.root.context)
                .load(BuildConfig.API_IMAGE_BASE_URL + BuildConfig.API_BANNER_BACKDROP_SIZE_W780 + movie.backdropImage)
                .into(banner)
            val percentage = (movie.voteAverage * 10).toInt()
            progressBar.progress = percentage
            userScorePercentage.text =
                getString(R.string.user_score_percentage, percentage)
            movieTitle.text = movie.title

            genres.text = getString(
                R.string.genres,
                movie.genres.toString().replace("[", "").replace("]", "")
            )
            originalLanguage.text = getString(R.string.original_language, movie.originalLanguage)
            releaseDate.text = getString(
                R.string.realease_date,
                DateFormat.getDateInstance().format(movie.releaseDate)
            )

            overviewDescription.text = movie.overview
        }
    }
}