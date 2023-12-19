package com.anubhav.ui

data class FilterItem(
    val id: Int,
    val title: String,
    val imagePath: String = "file:///android_asset/bubble_tea.webp",
    var isSelected: Boolean = false
)

