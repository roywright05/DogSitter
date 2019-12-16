package com.example.dogsitter.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsitter.R
import com.example.dogsitter.model.DogBreed
import com.example.dogsitter.util.getProgressDrawable
import com.example.dogsitter.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.item_dog.view.*

class DogListAdapter (val dogsList: ArrayList<DogBreed>): RecyclerView.Adapter<DogListAdapter.DogViewHolder>(){

    class DogViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    fun updateDogList(newDogList: List<DogBreed>){

        dogsList.clear()
        dogsList.addAll(newDogList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_dog, parent, false)
        return DogViewHolder(view)

    }

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {

        holder.view.tvName.text = dogsList[position].dogBreed
        holder.view.tvLifespan.text = dogsList[position].lifeSpan

        holder.view.setOnClickListener{
            Navigation.findNavController(it)
                .navigate(ListFragmentDirections.actionListFragmentToDetailFragment())
        }

        holder.view.imageView.loadImage(dogsList[position].imageUrl,
            getProgressDrawable(holder.view.imageView.context))


    }

}