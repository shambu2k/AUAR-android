package com.example.auar.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.auar.databinding.ActivityMainBinding
import com.example.auar.utils.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewLifecycle(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}