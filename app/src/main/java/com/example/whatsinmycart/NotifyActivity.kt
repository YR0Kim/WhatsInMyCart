package com.example.whatsinmycart

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whatsinmycart.databinding.ActivityNotifyBinding

class NotifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNotifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "알림"

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.navigationIcon?.setTint(Color.WHITE)
    }
}