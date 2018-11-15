package com.morphiumdeus.livelog.popularmovies


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.morphiumdeus.livelog.R
import com.morphiumdeus.livelog.common.ImageLoader
import com.morphiumdeus.livelog.entities.Category
import kotlinx.android.synthetic.main.popular_movies_adapter_cell.view.*

/**
 * Created by Yossi Segev on 14/11/2017.
 */
class CategoryListAdapter constructor(private val imageLoader: ImageLoader,
                                      private val onMovieSelected: (Category, View) -> Unit) : RecyclerView.Adapter<CategoryListAdapter.MovieCellViewHolder>() {

    private val categories: MutableList<Category> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieCellViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.popular_movies_adapter_cell,
                parent,
                false)
        return MovieCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: MovieCellViewHolder?, position: Int) {
        val movie = categories[position]
        holder?.bind(movie, imageLoader, onMovieSelected)
    }

    fun addMovies(categories: List<Category>) {
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }

    class MovieCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category, imageLoader: ImageLoader, listener: (Category, View) -> Unit) = with(itemView) {
            title.text = category.name
            setOnClickListener { listener(category, itemView) }
        }

    }
}