package com.anubhav.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.anubhav.populate.loadBeautyFilters
import com.anubhav.populate.loadQualityFilters
import com.anubhav.tabrecyclerviewplayground.R
import com.anubhav.tabrecyclerviewplayground.databinding.NewFiltersUiLayoutBinding
import com.google.android.material.tabs.TabLayout

class CustomFilterView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding by lazy { NewFiltersUiLayoutBinding.inflate(LayoutInflater.from(context), this, true) }

    private val imageQualityList = mutableListOf<FilterItem>().also { it.addAll(loadQualityFilters()) }
    private val beautyFiltersList = mutableListOf<FilterItem>().also { it.addAll(loadBeautyFilters()) }
    private val maskList = mutableListOf<FilterItem>()
    private val backgroundList = mutableListOf<FilterItem>()

    private val lastSelectedImageQualityStateList = mutableListOf<FilterItem>()
    private val lastSelectedBeautyFiltersStateList = mutableListOf<FilterItem>()
    private val lastSelectedMaskListStateList = mutableListOf<FilterItem>()
    private val lastSelectedBackgroundListStateList = mutableListOf<FilterItem>()

    private val adapter = FilterAdapter{}
    private var lastSelectedCategory : FilterCategories = FilterCategories.ImageQuality

    companion object {
        const val TAB_IMAGE_QUALITY = "TAB_IMAGE_QUALITY"
        const val TAB_BEAUTY_FILTERS = "TAB_BEAUTY_FILTERS"
        const val TAB_MASK = "TAB_MASK"
        const val TAB_BACKGROUND = "TAB_BACKGROUND"
    }

    init {
        binding.apply {
            setupTabs()
            setupRecyclerView()
            setupActionButtons()
            updateRecyclerView(imageQualityList)
        }
    }

    private fun setupTabs() {
        with(binding.tabLayout) {
            addTab(newTab().setText(context.getString(R.string.image_quality)).setTag(TAB_IMAGE_QUALITY))
            addTab(newTab().setText(context.getString(R.string.beauty_filters)).setTag(TAB_BEAUTY_FILTERS))
            addTab(newTab().setText(context.getString(R.string.mask)).setTag(TAB_MASK))
            addTab(newTab().setText(context.getString(R.string.background)).setTag(TAB_BACKGROUND))

            for (i in 1 until binding.tabLayout.tabCount) {
                val tab = getTabAt(i)
                tab?.view?.alpha = 0.5f
            }

            selectTab(getTabAt(0))

                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {

                    tab.view.alpha = 1f

                    updateLastSelectedCategory()

                    when (tab.tag) {
                        TAB_IMAGE_QUALITY -> {
                            updateRecyclerView(imageQualityList)
                            lastSelectedCategory = FilterCategories.ImageQuality
                        }

                        TAB_BEAUTY_FILTERS -> {
                            updateRecyclerView(beautyFiltersList)
                            lastSelectedCategory = FilterCategories.BeautyFilters
                        }

                        TAB_MASK -> {
                            updateRecyclerView(maskList)
                            lastSelectedCategory = FilterCategories.Mask
                        }

                        TAB_BACKGROUND -> {
                            updateRecyclerView(backgroundList)
                            lastSelectedCategory = FilterCategories.Background
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    tab.view.alpha = 0.5f
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

        }
    }

    private fun updateLastSelectedCategory() {
        when(lastSelectedCategory){
            FilterCategories.ImageQuality -> {
                lastSelectedImageQualityStateList.clear()
                lastSelectedImageQualityStateList.addAll(adapter.currentList)
                imageQualityList.clear()
                imageQualityList.addAll(lastSelectedImageQualityStateList)
            }
            FilterCategories.BeautyFilters -> {
                lastSelectedBeautyFiltersStateList.clear()
                lastSelectedBeautyFiltersStateList.addAll(adapter.currentList)
                beautyFiltersList.clear()
                beautyFiltersList.addAll(lastSelectedBeautyFiltersStateList)
            }
            FilterCategories.Background -> {}
            FilterCategories.Mask -> {}
        }
    }

    private fun updateRecyclerView(list: List<FilterItem>) {
        adapter.submitList(list)
        adapter.notifyItemRangeRemoved(0,list.size)
        adapter.notifyItemRangeInserted(0,list.size)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter
    }

    private fun setupActionButtons() {
    }

}

sealed class FilterCategories{
    data object ImageQuality : FilterCategories()
    data object BeautyFilters : FilterCategories()
    data object Mask : FilterCategories()
    data object Background : FilterCategories()

}