package com.anubhav.populate

import android.widget.ImageView
import com.anubhav.ui.FilterItem
import com.bumptech.glide.Glide

fun loadFilters(): List<FilterItem> {

    return listOf(
        FilterItem(
            1,
            "Filter 1"
        ),
        FilterItem(
            2,
            "Filter 2"
        ),
        FilterItem(
            3,
            "Filter 3"
        ),
        FilterItem(
            4,
            "Filter 4"
        ),
        FilterItem(
            5,
            "Filter 5"
        ),
    )
}


fun ImageView.loadWithGlide(url: String ){
    Glide.with(context)
        .load(url)
        .into(this)
}