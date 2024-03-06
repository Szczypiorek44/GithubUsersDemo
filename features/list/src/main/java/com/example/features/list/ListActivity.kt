package com.example.features.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.features.list.databinding.ActivityListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels<ListViewModel>()

    private val adapter by lazy { UserAdapter({ }) }

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        val view = binding.root

        binding.recyclerView.adapter = adapter

        observeEvents()

        setContentView(view)
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.userListState.collect {
                    adapter.setUsers(it)
                }
            }
        }
    }
}