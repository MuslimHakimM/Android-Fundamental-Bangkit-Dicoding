package com.bangkit.submission2.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.bangkit.submission2.data.local.SettingPreferences
import com.bangkit.submission2.databinding.ActivitySettingBinding
import com.bangkit.submission2.viewmodel.SettingViewModel

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val viewModel by viewModels<SettingViewModel>{
        SettingViewModel.factory(SettingPreferences(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.getTheme().observe(this) {
            if (it) {
                binding.smTheme.text = "On"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else {
                binding.smTheme.text = "Off"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.smTheme.isChecked = it
        }
        binding.smTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTheme(isChecked)
        }

        binding.icBack.setOnClickListener{ finish()}
    }
}