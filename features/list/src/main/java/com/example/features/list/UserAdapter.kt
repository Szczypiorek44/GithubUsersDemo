package com.example.features.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.User
import com.example.features.list.databinding.ItemUserBinding

class UserAdapter(private val onUserClick: (User) -> Unit) :
    RecyclerView.Adapter<UserViewHolder>() {

    private var users = ArrayList<User>()

    fun setUsers(users: List<User>) {
        this.users = ArrayList(users)
        notifyDataSetChanged()
    }

    fun getUsers(): List<User> {
        return users
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserViewHolder {
        return UserViewHolder(
            binding = ItemUserBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            ),
            onUserClick = onUserClick
        )
    }//

    override fun onBindViewHolder(viewHolder: UserViewHolder, i: Int) {
        viewHolder.bind(users[i])
    }

    override fun getItemCount(): Int {
        return users.size
    }
}