package com.example.roombasicapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roombasicapplication.databinding.ActivityUserListBinding
import com.example.roombasicapplication.services.LocalDatabase
import com.example.roombasicapplication.services.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserListActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityUserListBinding
    private lateinit var _adapter : UserListAdapter

    private val userList: MutableList<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserListBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)


        with(_binding){
            _adapter = UserListAdapter(this@UserListActivity)
            rvContent.adapter = _adapter

            GlobalScope.launch(Dispatchers.IO) {
                userList.addAll(LocalDatabase(this@UserListActivity).getUserDao().getAll())
                _adapter.setArticleList(userList)
            }
        }
    }
}