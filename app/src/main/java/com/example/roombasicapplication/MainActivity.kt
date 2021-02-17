package com.example.roombasicapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.roombasicapplication.databinding.ActivityMainBinding
import com.example.roombasicapplication.services.LocalDatabase
import com.example.roombasicapplication.services.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        with(_binding){
            var firstName: String
            var lastName: String

            btnSave.setOnClickListener {
                firstName = etFirstName.text.toString()
                lastName = etLastName.text.toString()

                if(firstName == ""){
                    etFirstName.error = getString(R.string.empty_state_first_name)
                    etFirstName.requestFocus()
                }else if(lastName == ""){
                    etLastName.error = getString(R.string.empty_state_last_name)
                    etLastName.requestFocus()
                }else{
                    val user = User(firstName = firstName, lastName = lastName)

                    try{
                        GlobalScope.launch(Dispatchers.IO) {
                            LocalDatabase(this@MainActivity).getUserDao().insertAll(user)
                        }

                        etFirstName.text?.clear()
                        etLastName.text?.clear()
                        Toast.makeText(this@MainActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    }catch (ex: Exception){
                        Toast.makeText(this@MainActivity, "Error - ${ex.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            btnShow.setOnClickListener {
                startActivity(Intent(this@MainActivity, UserListActivity::class.java))
            }
        }
    }
}