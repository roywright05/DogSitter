package com.example.dogsitter.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogsitter.R
import com.example.dogsitter.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel : ListViewModel
    private val dogsListAdapter = DogListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        //-- dogList comes from Recyclerview widget id and adapter and layoutmanager are part of the
        //recycler view class
        dogsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogsListAdapter

        }

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.dogs.observe(this, Observer { dogs ->

            dogs?.let {
                dogsList.visibility = View.VISIBLE
                dogsListAdapter.updateDogList(dogs)
            }
        })

        viewModel.dogsLoadError.observe(this, Observer {isError ->
            isError?.let {
                    listError.visibility = if (it) View.VISIBLE else View.GONE
            }

        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE

                if(it){

                    listError.visibility = View.GONE
                    dogsList.visibility = View.GONE
                }
            }

        })
    }

}
