package com.example.ejerciciobrubank.presentation.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.ejerciciobrubank.R
import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.databinding.FragmentDetailsMovieBinding
import com.example.ejerciciobrubank.domain.usecase.GetMovieUseCase
import com.example.ejerciciobrubank.helpers.Constants
import com.example.ejerciciobrubank.helpers.Constants.ID_MOVIE
import com.example.ejerciciobrubank.presentation.viewmodel.DetailsViewModel
import com.stfalcon.imageviewer.StfalconImageViewer
import org.koin.android.ext.android.inject

/**
 * @author Axel Sanchez
 */
class DetailsMovieFragment : Fragment() {

    private val getMovieUseCase: GetMovieUseCase by inject()
    private val viewModelDetails: DetailsViewModel by viewModels(
        factoryProducer = { DetailsViewModel.MovieViewModelFactory(getMovieUseCase) }
    )

    private var fragmentDetailsMovieBinding: FragmentDetailsMovieBinding? = null
    private val binding get() = fragmentDetailsMovieBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentDetailsMovieBinding = FragmentDetailsMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentDetailsMovieBinding = null
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()

        val idProduct = arguments?.getLong(ID_MOVIE)

        idProduct?.let {
            viewModelDetails.getMovie(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpObserver() {
        viewModelDetails.getMovieLiveData().observe(viewLifecycleOwner, { movie ->
            with(binding){
                Glide
                    .with(requireActivity())
                    .load("${Constants.BASE_URL_IMAGEN}${movie?.poster_path}")
                    .into(ivFrontPage)

                tvTitle.text = movie?.title

                tvYear.text = movie?.release_date?.substring(0, 4)

                tvOverviewContent.text = movie?.overview
            }
        })
    }
}