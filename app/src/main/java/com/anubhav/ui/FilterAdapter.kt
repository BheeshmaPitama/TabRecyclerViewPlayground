package com.anubhav.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anubhav.populate.loadWithGlide
import com.anubhav.tabrecyclerviewplayground.databinding.FilterItemBinding

class FilterAdapter(private val onItemClicked: (FilterItem) -> Unit) :
    ListAdapter<FilterItem, FilterAdapter.FilterViewHolder>(FilterDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = FilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filterItem = getItem(position)
        holder.bind(filterItem)
    }

    class FilterViewHolder(
        private val binding: FilterItemBinding,
        onItemClicked: (FilterItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentFilterItem: FilterItem? = null

        init {
            itemView.setOnClickListener {
                currentFilterItem?.let { filterItem ->
                    onItemClicked(filterItem)
                }
            }
        }

        fun bind(filterItem: FilterItem) {
            currentFilterItem = filterItem
            with(binding) {
                filterText.text = filterItem.title
                filterImage.loadWithGlide(filterItem.imagePath)
            }
        }
    }

    object FilterDiffCallback : DiffUtil.ItemCallback<FilterItem>() {
        override fun areItemsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean {
            return oldItem == newItem
        }
    }
}
