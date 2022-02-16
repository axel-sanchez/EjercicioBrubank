package com.example.ejerciciobrubank.presentation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejerciciobrubank.R
import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.helpers.Constants
import com.example.ejerciciobrubank.helpers.Constants.BASE_URL_IMAGEN

/**
 * @author Axel Sanchez
 */
class MovieAdapter(
    private val values: List<Movie?>,
    private val itemClick: (Movie?) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        item?.let { movie ->

            with(holder){
                tvTitle.text = movie.title

                Glide
                    .with(itemView)
                    .load("${BASE_URL_IMAGEN}${movie.poster_path}")
                    .into(ivImage)

                val firstId = movie.genre_ids?.firstOrNull()

                firstId?.let { id ->
                    tvGenre.text = Constants.Genres.values().find {
                        it.id == id
                    }?.nameGenre?:"Other"
                }

                itemView.setOnClickListener { itemClick(movie) }
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivImage)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvGenre: TextView = view.findViewById(R.id.tvGenre)
    }
}