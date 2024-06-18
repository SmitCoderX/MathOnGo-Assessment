package com.smitcoderx.mathongoassignment.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import coil.load
import coil.transform.CircleCropTransformation
import com.smitcoderx.mathongoassignment.databinding.LayoutPopularRecipeItemBinding
import com.smitcoderx.mathongoassignment.models.recipe.Recipe

class PopularRecipesAdapter : Adapter<PopularRecipesAdapter.PopularRecipesViewHolder>() {

    inner class PopularRecipesViewHolder(val binding: LayoutPopularRecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularRecipesViewHolder {
        val binding = LayoutPopularRecipeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PopularRecipesViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PopularRecipesViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        currentItem.run {
            holder.binding.ivRecipe.load(image) {
                crossfade(true)
            }
            holder.binding.tvRecipe.text = title
            holder.binding.tvRecipeReady.text = "Ready in $readyInMinutes min"
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