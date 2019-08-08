package com.example.tikitestv2.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tikitestv2.viewmodel.ApiStatus
import com.example.tikitestv2.viewmodel.MainViewModel
import com.example.tikitestv2.R
import com.example.tikitestv2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this
        binding.list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.list.adapter = ListAdapter()
        binding.viewModel = viewModel
        observeDataChange(binding)
        viewModel.getKeys()
    }

    private fun observeDataChange(binding: ActivityMainBinding) {
        viewModel.status.observe(this, Observer {
            when (it) {
                ApiStatus.LOADING -> binding.loading.visibility = View.VISIBLE
                ApiStatus.DONE -> binding.loading.visibility = View.INVISIBLE
                ApiStatus.ERROR -> {
                    binding.tryAgain.visibility = View.VISIBLE
                    binding.loading.visibility = View.INVISIBLE
                }
            }
        })
        binding.tryAgain.setOnClickListener {
            it.visibility = View.INVISIBLE
            viewModel.getKeys()
        }
        viewModel.listKey.observe(this, Observer {
            (binding.list.adapter as ListAdapter).addList(it)
        })
    }


}
