package com.example.frogsocial.Movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frogsocial.databinding.AdapterMovieBinding
import javax.inject.Inject

class MoviesAdapter @Inject constructor() : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    var movies = mutableListOf<Movies>()
    private var clickInterface: ClickInterface<Movies>? = null

    fun updateMovies(movies: List<Movies>) {
        this.movies = movies.toMutableList()
        notifyItemRangeInserted(0, movies.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            AdapterMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.view.tvTitle.text = movie.title
        holder.view.rvYear.text = "Year : ${movie.year}"
        holder.view.rvRating.text = "Rated : ${movie.imdbRating}"
        Glide
            .with(holder.view.imgMovieImage.context)
            .load(movie.image)
            .centerCrop()
            .into(holder.view.imgMovieImage)
        holder.view.movieCard.setOnClickListener {
            clickInterface?.onClick(movie)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setItemClick(clickInterface: ClickInterface<Movies>) {
        this.clickInterface = clickInterface
    }

    class MovieViewHolder(val view: AdapterMovieBinding) : RecyclerView.ViewHolder(view.root)
}

interface ClickInterface<T> {
    fun onClick(data: T)
}