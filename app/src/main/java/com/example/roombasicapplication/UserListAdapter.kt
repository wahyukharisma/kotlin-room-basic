package com.example.roombasicapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roombasicapplication.databinding.ItemListUserBinding
import com.example.roombasicapplication.services.entities.User

class UserListAdapter(private val context: Context) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.item_list_user, parent, false))

    private var items: MutableList<User> = ArrayList()

    fun setArticleList(list: List<User>) {
        this.items.addAll(list)
        notifyDataSetChanged()
    }

    fun removeAllData() {
        this.items.clear()
        notifyDataSetChanged()
    }

    fun removeSingleData(user: User){
        this.items.remove(user)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder){
            binding.tvNumber.text = item.uid.toString()
            binding.tvName.text = context.getString(R.string.concate_name, item.firstName, item.lastName)
        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemListUserBinding.bind(view)
    }
}