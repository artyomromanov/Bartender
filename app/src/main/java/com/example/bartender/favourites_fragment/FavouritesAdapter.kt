package com.example.bartender.favourites_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bartender.R
import com.example.bartender.model.Drink
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favourites_item.view.*

class FavouritesAdapter(private val list: List<Drink>, private val listener: FavouritesRecyclerViewClickListener) : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.favourites_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return list.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int){

            itemView.favourites_tv_name.text = list[position].strDrink
            itemView.favourites_tv_description.text = list[position].strInstructions

            Picasso.get().load(list[position].strDrinkThumb).into(itemView.favourites_iv_thumb)

            itemView.setOnClickListener {
                listener.onFavouritesItemClicked(it)
            }
        }
    }
}
