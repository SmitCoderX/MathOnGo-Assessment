package com.smitcoderx.mathongoassignment.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.smitcoderx.mathongoassignment.databinding.LayoutSearchItemBinding
import com.smitcoderx.mathongoassignment.models.recipe.Recipe

class SearchRecipesAdapter : Adapter<SearchRecipesAdapter.SearchItemViewHolder>() {

    inner class SearchItemViewHolder(val binding: LayoutSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val binding = LayoutSearchItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchItemViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        currentItem.run {
            holder.binding.tvSearchItem.text = title
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}