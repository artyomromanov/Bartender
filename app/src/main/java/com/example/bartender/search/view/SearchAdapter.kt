package com.example.bartender.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bartender.search.model.Drink
import com.example.bartender.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_item.view.*

class SearchAdapter(private val list : List<Drink>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(parent.populateList(R.layout.search_item))

    }

    override fun getItemCount(): Int {

        return list.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.bind(position)

    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int){

                itemView.tv_name.text = list[position].strDrink

                val fullDrinkUrl = list[position].strDrinkThumb
                println(fullDrinkUrl)
                Picasso.get().load(fullDrinkUrl).into(itemView.iv_thumb)

        }

    }
    private fun ViewGroup.populateList(resourceId : Int) : View {

        return this.let { LayoutInflater.from(context).inflate(resourceId, it, false) }

    }
}