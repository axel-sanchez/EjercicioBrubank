package com.example.ejerciciobrubank.presentation.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ejerciciobrubank.R
import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.databinding.FragmentListMoviesBinding
import com.example.ejerciciobrubank.domain.usecase.GetMoviesBySearchUseCase
import com.example.ejerciciobrubank.domain.usecase.GetListMoviesUseCase
import com.example.ejerciciobrubank.helpers.Constants
import com.example.ejerciciobrubank.helpers.Constants.ID_MOVIE
import com.example.ejerciciobrubank.helpers.Constants.SHARED_ELEMENTS_TRANSITION
import com.example.ejerciciobrubank.helpers.Constants.THERE_IS_NOT_MOVIES
import com.example.ejerciciobrubank.helpers.Constants.THE_MOVIE_IS_NOT_EXISTS
import com.example.ejerciciobrubank.helpers.Either
import com.example.ejerciciobrubank.helpers.hide
import com.example.ejerciciobrubank.helpers.show
import com.example.ejerciciobrubank.presentation.view.adapters.MovieAdapter
import com.example.ejerciciobrubank.presentation.viewmodel.SearchViewModel
import com.example.ejerciciobrubank.presentation.viewmodel.MoviesViewModel
import com.stfalcon.imageviewer.StfalconImageViewer
import org.koin.android.ext.android.inject

/**
 * @author Axel Sanchez
 * */
class ListMoviesFragment : Fragment() {

    private val getListMoviesUseCase: GetListMoviesUseCase by inject()
    private val viewModelMovies: MoviesViewModel by viewModels(
        factoryProducer = { MoviesViewModel.MovieViewModelFactory(getListMoviesUseCase) }
    )

    private val getMoviesBySearchUseCase: GetMoviesBySearchUseCase by inject()
    private val viewModelSearch: SearchViewModel by viewModels(
        factoryProducer = { SearchViewModel.MoviesBySearchViewModelFactory(getMoviesBySearchUseCase) }
    )

    private var fragmentListMoviesBinding: FragmentListMoviesBinding? = null
    private val binding get() = fragmentListMoviesBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentListMoviesBinding = FragmentListMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentListMoviesBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            ivSearch.setOnClickListener {
                ivSearch.hide()
                tvTittle.hide()
                llSearch.show()
            }

            tvCancel.setOnClickListener {
                hideKeyboard()
                etSearch.setText("")
                progress.show()
                ivSearch.show()
                tvTittle.show()
                llSearch.hide()
                viewModelMovies.getMovies()
            }

            ivSend.setOnClickListener {
                hideKeyboard()
                progress.show()
                viewModelSearch.getMoviesBySearch(etSearch.text.toString())
            }
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = requireActivity().currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setupObservers() {
        viewModelMovies.getMovieLiveData().observe(viewLifecycleOwner, { response ->
            updateView(response)
        })

        viewModelSearch.getMoviesLiveData().observe(viewLifecycleOwner, { response ->
            updateView(response)
        })
    }

    private fun updateView(response: Either<Constants.ApiError, List<Movie?>>?) {
        with(binding) {
            response?.fold(
                left = {
                    Log.e("error", it.error)
                    errorText.text = it.error
                    emptyState.show()
                    rvListMovies.hide()
                }, right = {
                    if ((response as Either.Right).r.isEmpty()) {
                        rvListMovies.hide()
                        errorText.text = THERE_IS_NOT_MOVIES
                        emptyState.show()
                    } else {
                        rvListMovies.show()
                        emptyState.hide()
                        setAdapter(it)
                    }
                }
            )
            progress.hide()
        }
    }

    private fun setAdapter(products: List<Movie?>) {
        with(binding.rvListMovies) {
            layoutManager = LinearLayoutManager(context)
            adapter = MovieAdapter(products, itemClick)
        }
    }

    private val itemClick = { movie: Movie? ->
        movie?.let {
            val bundle = bundleOf(
                ID_MOVIE to it.id
            )

            findNavController().navigate(R.id.action_listMoviesFragment_to_detailsMovieFragment, bundle)
        }?: kotlin.run {
            Toast.makeText(requireContext(), THE_MOVIE_IS_NOT_EXISTS, Toast.LENGTH_SHORT).show()
        }
    }
}