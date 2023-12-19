package com.anubhav.populate

import android.widget.ImageView
import com.anubhav.ui.FilterItem
import com.bumptech.glide.Glide

fun loadQualityFilters(): List<FilterItem> {

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

fun loadBeautyFilters(): List<FilterItem> {

    val imagePath = "file:///android_asset/clear.webp"

    return listOf(
        FilterItem(
            1,
            "Filter 1",
            imagePath
        ),
        FilterItem(
            2,
            "Filter 2",
            imagePath
        ),
        FilterItem(
            3,
            "Filter 3",
            imagePath
        ),
        FilterItem(
            4,
            "Filter 4",
            imagePath
        ),
        FilterItem(
            5,
            "Filter 5",
            imagePath
        ),
    )
}


fun ImageView.loadWithGlide(url: String ){
    Glide.with(context)
        .load(url)
        .into(this)
}