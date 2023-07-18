package com.bangkit.submission2.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bangkit.submission2.R
import com.bangkit.submission2.adapter.SectionPagerAdapter
import com.bangkit.submission2.databinding.ActivityDetailBinding
import com.bangkit.submission2.ui.main.MainActivity
import com.bangkit.submission2.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var binding : ActivityDetailBinding
    private lateinit var viewModel : DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.icBack.setOnClickListener {
            val mainPage = Intent(this@DetailActivity, MainActivity::class.java)
            startActivity(mainPage)
        }


        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        if (username != null) {
            viewModel.setDetailUser(username)
        }
        viewModel.getDetailUser().observe(this, {
            if (it !=null){
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(imgProfile)
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val check = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (check != null) {
                    if (check > 0) {
                        binding.toggleButton.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleButton.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleButton.setOnClickListener {
            _isChecked = ! _isChecked
            if (_isChecked){
                if (username != null && avatarUrl != null){
                        viewModel.addToFavorite(username, id, avatarUrl)

                }
            }else{
                viewModel.removeFavorite(id)
            }
            binding.toggleButton.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            vpProfile.adapter = sectionPagerAdapter
            tlProfile.setupWithViewPager(vpProfile)
        }

    }
    private fun showLoading(isLoading: Boolean) {
        binding.pbProfile.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}