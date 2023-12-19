package com.anubhav.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anubhav.populate.loadWithGlide
import com.anubhav.tabrecyclerviewplayground.R
import com.anubhav.tabrecyclerviewplayground.databinding.FilterItemBinding

class FilterAdapter(private val onItemClicked: (FilterItem) -> Unit) :
    ListAdapter<FilterItem, FilterAdapter.FilterViewHolder>(FilterDiffCallback) {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = FilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding, onItemClicked, ::onItemSelected)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        context = holder.itemView.context
        val filterItem = getItem(position)
        holder.bind(filterItem)
    }

    private fun onItemSelected(item: FilterItem) {
        currentList.forEach { it.isSelected = false }
        item.isSelected = true
        notifyDataSetChanged()
    }

    inner class FilterViewHolder(
        private val binding: FilterItemBinding,
        onItemClicked: (FilterItem) -> Unit,
        onItemSelected: (FilterItem) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentFilterItem: FilterItem? = null

        init {
            itemView.setOnClickListener {
                currentFilterItem?.let { filterItem ->
                    onItemClicked(filterItem)
                    onItemSelected(filterItem)
                }
            }
        }

        fun bind(filterItem: FilterItem) {
            currentFilterItem = filterItem
            with(binding) {
                filterText.text = filterItem.title
                filterImage.loadWithGlide(filterItem.imagePath)
            }

            if (filterItem.isSelected) {
                bindSelectedState()
            } else {
                bindUnselectedState()
            }
        }

        private fun bindUnselectedState() {
            binding.apply {
                filterImage.foreground = null
                context?.let {
                    filterText.setTextColor(ContextCompat.getColor(it, R.color.white))
                }
            }
        }

        private fun bindSelectedState() {
            binding.apply {
                context?.let {
                    filterImage.foreground =
                        ContextCompat.getDrawable(it, R.drawable.filter_selected)

                    filterText.setTextColor(ContextCompat.getColor(it, R.color.selected_yellow))
                }
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

