package com.anubhav.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.anubhav.populate.loadFilters
import com.anubhav.tabrecyclerviewplayground.R
import com.anubhav.tabrecyclerviewplayground.databinding.NewFiltersUiLayoutBinding
import com.google.android.material.tabs.TabLayout

class CustomFilterView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding by lazy { NewFiltersUiLayoutBinding.inflate(LayoutInflater.from(context), this, true) }

    private val imageQualityList = mutableListOf<FilterItem>().also { it.addAll(loadFilters()) }
    private val beautyFiltersList = mutableListOf<FilterItem>()
    private val maskList = mutableListOf<FilterItem>()
    private val backgroundList = mutableListOf<FilterItem>()
    private val adapter = FilterAdapter{}

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

                    when (tab.tag) {
                        TAB_IMAGE_QUALITY -> updateRecyclerView(imageQualityList)
                        TAB_BEAUTY_FILTERS -> updateRecyclerView(beautyFiltersList)
                        TAB_MASK -> updateRecyclerView(maskList)
                        TAB_BACKGROUND -> updateRecyclerView(backgroundList)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    tab.view.alpha = 0.5f
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

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