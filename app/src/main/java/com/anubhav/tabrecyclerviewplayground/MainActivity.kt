package com.anubhav.tabrecyclerviewplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.anubhav.tabrecyclerviewplayground.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isViewVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
    }

    private fun setListener() {
        binding.filterButton.setOnClickListener {
            if (isViewVisible) {
                // Slide down and hide the view
                val slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)
                binding.customFilterView.startAnimation(slideDown)
                binding.customFilterView.visibility = View.GONE
            } else {
                // Slide up and show the view
                val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
                binding.customFilterView.startAnimation(slideUp)
                binding.customFilterView.visibility = View.VISIBLE
            }
            isViewVisible = !isViewVisible
        }
        }

}