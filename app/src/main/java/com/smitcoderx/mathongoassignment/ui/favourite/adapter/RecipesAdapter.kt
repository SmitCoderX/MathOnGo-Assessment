package com.smitcoderx.mathongoassignment.ui.favourite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.smitcoderx.mathongoassignment.databinding.LayoutAdViewBinding
import com.smitcoderx.mathongoassignment.databinding.LayoutAllRecipeItemBinding
import com.smitcoderx.mathongoassignment.models.recipe.Recipe
import com.smitcoderx.mathongoassignment.utils.Constants.AD_TYPE
import com.smitcoderx.mathongoassignment.utils.Constants.CONTENT_TYPE


class RecipesAdapter(private val listener: OnFavItemClick) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        if (position > 1 && (position + 1) % 6 == 0) {
            return AD_TYPE;
        }
        return CONTENT_TYPE;
    }

    inner class FavouritesViewHolder(val binding: LayoutAllRecipeItemBinding) :
        ViewHolder(binding.root) {

    }

    inner class AdsViewHolder(private val binding: LayoutAdViewBinding) :
        ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == AD_TYPE) {
            return AdsViewHolder(
                LayoutAdViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
        return FavouritesViewHolder(
            LayoutAllRecipeItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        if (holder is FavouritesViewHolder) {
            currentItem.let {
                holder.binding.ivRecipeItem.load(it.image) {
                    crossfade(true)
                }
                holder.binding.tvRecipeName.text = it.title
                holder.binding.tvRecipeReady.text = "Ready in ${it.readyInMinutes} min"

                holder.binding.root.setOnClickListener {
                    val adapterPosition = holder.bindingAdapterPosition
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        if (currentItem != null) {
                            listener.onFavClick(currentItem)
                        }
                    }
                }
            }
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

    interface OnFavItemClick {
        fun onFavClick(item: Recipe)
    }
}