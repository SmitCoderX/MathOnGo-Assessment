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
import com.smitcoderx.mathongoassignment.databinding.LayoutAllRecipeItemBinding
import com.smitcoderx.mathongoassignment.databinding.LayoutPopularRecipeItemBinding
import com.smitcoderx.mathongoassignment.models.recipe.Recipe

class AllRecipesAdapter : Adapter<AllRecipesAdapter.AllRecipesViewHolder>() {

    inner class AllRecipesViewHolder(val binding: LayoutAllRecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllRecipesViewHolder {
        val binding = LayoutAllRecipeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AllRecipesViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AllRecipesViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        currentItem.run {
            holder.binding.ivRecipeItem.load(image) {
                crossfade(true)
            }
            holder.binding.tvRecipeName.text = title
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