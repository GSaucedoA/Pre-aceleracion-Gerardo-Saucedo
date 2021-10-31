package dev.cynomys.movieapp

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dev.cynomys.movieapp.databinding.ActivityMainBinding
import dev.cynomys.movieapp.movie.Movie
import dev.cynomys.movieapp.movie.NetworkStatus

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var page = 1
    private val viewModel: MainViewModel by viewModels(factoryProducer = { MainViewModelFactory(page) })
    private val movieList: ArrayList<Movie> = ArrayList()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateSpanCount()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()

        setUpInfiniteScroll()

        setUpSearchView()

        setObsevers()
    }

    private fun setObsevers() {
        viewModel.movieList.observe(this, { resource ->
            when (resource.status) {
                NetworkStatus.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                NetworkStatus.SUCCESS -> {
                    binding.customToolbar.backArrow.text = page.toString()
                    binding.progressBar.visibility = View.GONE
                    val data = resource.data ?: emptyList()
                    updateRecyclerViewData(data)
                }
                NetworkStatus.ERROR -> {
                    Toast.makeText(this, "Error, couldn't load movies", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = MainAdapter(movieList)
        updateSpanCount()
    }

    private fun updateSpanCount() {
        (binding.recyclerView.layoutManager as StaggeredGridLayoutManager).spanCount =
            getDynamicSpanCount()
    }

    private fun getDynamicSpanCount(): Int {
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()

        display.getMetrics(outMetrics)

        val density = resources.displayMetrics.density
        val dpWith = outMetrics.widthPixels / density

        return (dpWith / 173).toInt()
    }

    private fun updateRecyclerViewData(data: List<Movie>) {
        movieList.addAll(data)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setUpInfiniteScroll() {
        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight && page <= 500) {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.getMovieList(++page)
            }
        })
    }

    private fun setUpSearchView() {
        binding.customToolbar.search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (binding.recyclerView.adapter as MainAdapter).filter.filter(newText)
                return false
            }
        })
    }
}