package com.shashank.splitterexpensemanager.feature.createfriens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import com.shashank.splitterexpensemanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateFriendsActivity : AppCompatActivity() {
    private val viewModel: CreateFriendsViewModel by viewModels()

    lateinit var etName: EditText
    lateinit var etNumber: EditText
    lateinit var tvSave: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_friends)
        inti()
        tvSave.setOnClickListener {
            insertPerson()
            finish()
        }
    }

    private fun inti() {
        etName = findViewById(R.id.et_name)
        etNumber = findViewById(R.id.et_number)
        tvSave = findViewById(R.id.tv_save_User)
    }

    private fun insertPerson() {
        var sName = etName.text.toString().trim()
        var sNumber = etNumber.text.toString().trim()
        viewModel.insertPerson(sName, sNumber)
    }
}