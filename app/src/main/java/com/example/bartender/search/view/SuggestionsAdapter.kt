package com.example.bartender.search.view

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bartender.R
import kotlinx.android.synthetic.main.suggestion_item.view.*

class SuggestionsAdapter(private val list: List<SpannableString>, private val listener: RecyclerViewClickListener) :
    RecyclerView.Adapter<SuggestionsAdapter.ViewHolder>() {

    fun getListSize() : Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(parent.populateList(R.layout.suggestion_item))

    }

    override fun getItemCount(): Int {

        return list.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(position)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {

            itemView.tv_suggestion.text = list[position]

            itemView.setOnClickListener {

                listener.onSuggestionItemClicked(list[position].toString())

            }

        }

    }

    private fun ViewGroup.populateList(resourceId: Int): View {

        return this.let { LayoutInflater.from(context).inflate(resourceId, it, false) }

    }
}