package com.example.frogsocial.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.frogsocial.Movies.MoviesAdapter
import com.example.frogsocial.Movies.MoviesViewModel
import com.example.frogsocial.Movies.NetworkResult
import com.example.frogsocial.databinding.ActivityMoviesListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviesListBinding

    private val mainViewModel: MoviesViewModel by viewModels()
    @Inject
    lateinit var movieAdapter: MoviesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMovies.adapter = movieAdapter

        mainViewModel.movieResponse.observe(this) {
            when(it) {
                is NetworkResult.Loading -> {
                    binding.progressbar.isVisible = it.isLoading
                }

                is NetworkResult.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.progressbar.isVisible = false
                }

                is  NetworkResult.Success -> {
                    movieAdapter.updateMovies(it.data)
                    binding.progressbar.isVisible = false
                }
            }
        }

    }
}