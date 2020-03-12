package com.example.bartender.shake.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.bartender.*
import com.example.bartender.shake.model.Ingredient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ingredient_item.view.*
import kotlinx.android.synthetic.main.search_item.view.*

class IngredientsAdapter(private val list: List<Ingredient>, private val listener: OnIngredientClickListener) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(parent.populateList(R.layout.ingredient_item))

    }

    override fun getItemCount(): Int {

        return list.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(position)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {

            itemView.shake_tv_name.text = list[position].strIngredient1

            val fullIngredientUrl = INGREDIENT_IMAGE_ENDPOINT + list[position].strIngredient1 + MEDIUM_IMAGE_INGREDIENT
            Picasso.get().load(fullIngredientUrl).into(itemView.shake_iv_thumb)
            println(fullIngredientUrl)

            itemView.setOnClickListener {
                val animation = AnimationUtils.loadAnimation(itemView.context, R.anim.ingredient_animation_add)
                itemView.shake_iv_thumb.startAnimation(animation)
                listener.onIngredientClicked(itemView.shake_iv_thumb)
            }
        }
    }

    private fun ViewGroup.populateList(resourceId: Int): View {

        return this.let { LayoutInflater.from(context).inflate(resourceId, it, false) }

    }
}