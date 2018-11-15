package com.morphiumdeus.livelog.search


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.morphiumdeus.livelog.R
import com.morphiumdeus.livelog.common.ImageLoader
import com.morphiumdeus.livelog.entities.Category

/**
 * Created by Yossi Segev on 14/11/2017.
 */
class SearchResultsAdapter constructor(private val imageLoader: ImageLoader,
                                       private val onMovieSelected: (Category, View) -> Unit) : RecyclerView.Adapter<SearchResultsAdapter.MovieCellViewHolder>() {

    private var categories: List<Category> = listOf()
    var query: String? = null

    fun setResults(categories: List<Category>, query: String?) {
        this.categories = categories
        this.query = query
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieCellViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.search_results_adapter_row,
                parent,
                false)
        return MovieCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: MovieCellViewHolder?, position: Int) {
        val movie = categories[position]
        holder?.bind(movie, onMovieSelected)
    }

    class MovieCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category, listener: (Category, View) -> Unit) = with(itemView) {

            setOnClickListener { listener(category, itemView) }
        }

    }
}