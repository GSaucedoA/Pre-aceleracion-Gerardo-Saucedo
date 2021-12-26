package dev.cynomys.movieapp.view.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.cynomys.movieapp.BuildConfig

import dev.cynomys.movieapp.databinding.CustomRecyclerViewItemBinding
import dev.cynomys.movieapp.model.Movie
import dev.cynomys.movieapp.view.activities.MovieDetailActivity

class MainAdapter(private val movieList: List<Movie>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>(), Filterable {
    private var movieFilteredList: List<Movie>

    init {
        movieFilteredList = movieList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CustomRecyclerViewItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieFilteredList.get(position))
    }

    override fun getItemCount(): Int = movieFilteredList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    movieFilteredList = movieList
                } else {
                    val resultList = ArrayList<Movie>()
                    movieList.forEach {
                        if (it.title.lowercase().contains(charSearch.lowercase())) {
                            resultList.add(it)
                        }
                    }
                    movieFilteredList = resultList
                }
                val filterResult = FilterResults()
                filterResult.values = movieFilteredList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                movieFilteredList = results?.values as ArrayList<Movie>
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(private val binding: CustomRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                root.setOnClickListener {
                    val intent = Intent(root.context, MovieDetailActivity::class.java)
                    intent.putExtra("id", movie.id)
                    root.context.startActivity(intent)
                }
                title.text = movie.title
                Glide.with(root)
                    .load(BuildConfig.API_BANNER_BASE_URL + BuildConfig.API_BANNER_POSTER_SIZE_W500 + poster)
                    .into(poster)
            }
        }
    }
}