package com.example.dogsitter.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.example.dogsitter.R
import com.example.dogsitter.util.getProgressDrawable
import com.example.dogsitter.util.loadImage
import com.example.dogsitter.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_list.view.*


class DetailFragment : Fragment() {

    private var dogUuid = 0
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid

        }
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(dogUuid)

        observeViewModel()
    }

    private fun observeViewModel(){

        viewModel.dogLiveData.observe(this, Observer { dog ->
            dog?.let {

            //here use all your widget ids to retrieve the values from model class

            tv_dogName.text = dog.dogBreed
            tv_dogPurpose.text = dog.bredFor
            tv_dogTemperament.text = dog.temperament
            tv_dogLifespan.text = dog.lifeSpan

            context?.let{
                iv_dogImage.loadImage(dog.imageUrl, getProgressDrawable(it))
            }

        }

        })
    }
}
