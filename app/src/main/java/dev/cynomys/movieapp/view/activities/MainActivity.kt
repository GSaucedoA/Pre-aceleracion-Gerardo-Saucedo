package dev.cynomys.movieapp.view.activities

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.window.layout.WindowMetricsCalculator
import dagger.hilt.android.AndroidEntryPoint
import dev.cynomys.movieapp.databinding.ActivityMainBinding
import dev.cynomys.movieapp.model.Movie
import dev.cynomys.movieapp.view.adapters.MainAdapter
import dev.cynomys.movieapp.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    private var page = 1
    private val viewModel: MainViewModel by viewModels()
    private val movieList: MutableList<Movie> = mutableListOf()

    private val lastVisibleItemPosition: Int
        get() = (binding.recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateSpanCount()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        attachProgressBar(binding.root)

        showProgressBar()
        setUpRecyclerView()
        setUpInfiniteScroll()
        setUpSearchView()
        setObservers()
        callViewModelMethods()
    }

    private fun callViewModelMethods() {
        viewModel.getMovieList()
    }

    private fun setObservers() {
        viewModel.movieList.observe(this) {
            updateRecyclerViewData(it)
        }

        viewModel.loadingStatus.observe(this) {
            if (it) showProgressBar() else hideProgressBar()
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = MainAdapter(movieList)
        updateSpanCount()
    }

    private fun updateSpanCount() {
        (binding.recyclerView.layoutManager as GridLayoutManager).spanCount =
            getDynamicSpanCount()
    }

    private fun getDynamicSpanCount(): Int {
        val dpWith: Float
        val density = resources.displayMetrics.density
        val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val currentBounds = windowMetrics.bounds
        val width = currentBounds.width()

        dpWith = width / density

        return (dpWith / 173).toInt()
    }

    private fun updateRecyclerViewData(data: List<Movie>) {
        val lastSize = movieList.size
        movieList.addAll(data)
        binding.recyclerView.adapter?.notifyItemRangeChanged(lastSize, data.size)
        //binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setUpInfiniteScroll() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = binding.recyclerView.layoutManager?.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    viewModel.getMovieList(++page)
                }
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