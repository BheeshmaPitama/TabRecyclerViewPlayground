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

    private var selectedItem: FilterItem? = null
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = FilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding, onItemClicked,::onItemSelected)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        context = holder.itemView.context
        val filterItem = getItem(position)
        val isSelected = filterItem == selectedItem
        holder.bind(filterItem, isSelected)
    }

    private fun onItemSelected(item: FilterItem) {
        val previousSelected = selectedItem
        selectedItem = item
        previousSelected?.let { notifyItemChanged(currentList.indexOf(it)) }
        notifyItemChanged(currentList.indexOf(item))
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

        fun bind(filterItem: FilterItem, isSelected: Boolean) {
            currentFilterItem = filterItem
            with(binding) {
                filterText.text = filterItem.title
                filterImage.loadWithGlide(filterItem.imagePath)
            }

            if (isSelected) {
                bindSelectedState()
            } else {
                bindUnselectedState()
            }
        }

        private fun bindUnselectedState() {
            binding.apply {
                filterImage.foreground = null
                context?.let {
                    filterText.setTextColor(ContextCompat.getColor(it,R.color.white))
                }
            }
        }

        private fun bindSelectedState() {
            binding.apply {
                context?.let {
                    filterImage.foreground =
                        ContextCompat.getDrawable(it, R.drawable.filter_selected)

                    filterText.setTextColor(ContextCompat.getColor(it,R.color.selected_yellow))
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
