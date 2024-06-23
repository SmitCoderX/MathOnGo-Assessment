package com.smitcoderx.mathongoassignment.ui.single.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import coil.load
import coil.transform.CircleCropTransformation
import com.smitcoderx.mathongoassignment.R
import com.smitcoderx.mathongoassignment.databinding.LayoutIngredientsBinding
import com.smitcoderx.mathongoassignment.models.recipe.Equipment
import com.smitcoderx.mathongoassignment.models.recipe.ExtendedIngredient
import java.util.Locale

class EquipmentAdapter() :
    Adapter<EquipmentAdapter.EquipmentViewHolder>() {

    inner class EquipmentViewHolder(val binding: LayoutIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val binding = LayoutIngredientsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EquipmentViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        currentItem.run {
            holder.binding.ivIngredients.load(currentItem.image) {
                crossfade(true)
                placeholder(R.drawable.placeholder)
                error(R.drawable.placeholder)
                transformations(CircleCropTransformation())

            }
            holder.binding.tvIngredients.text = currentItem.name?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<Equipment>() {
        override fun areItemsTheSame(
            oldItem: Equipment,
            newItem: Equipment
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Equipment,
            newItem: Equipment
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

}