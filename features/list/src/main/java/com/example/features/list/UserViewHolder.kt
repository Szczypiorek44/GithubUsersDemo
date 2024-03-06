package com.example.features.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.User
import com.example.features.list.databinding.ItemUserBinding

class UserViewHolder(
    private val binding: ItemUserBinding,
    private val onUserClick: (User) -> Unit
) :
    RecyclerView.ViewHolder(binding.root),
    View.OnClickListener {

    private lateinit var user: User

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onUserClick(user)
    }

    fun bind(user: User) {
        this.user = user

        binding.nameTextView.text = user.name

    }
}