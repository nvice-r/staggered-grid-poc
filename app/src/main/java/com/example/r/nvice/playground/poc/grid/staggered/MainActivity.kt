package com.example.r.nvice.playground.poc.grid.staggered

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.r.nvice.playground.poc.grid.staggered.databinding.ActivityMainBinding
import com.example.r.nvice.playground.poc.grid.staggered.ui.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    binding.vContainer.id,
                    MainFragment.newInstance()
                )
                .commit()
        }
    }
}