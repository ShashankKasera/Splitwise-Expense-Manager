package com.shashank.splitterexpensemanager.feature.createfriens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.localdb.model.Person
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateFriendsActivity : AppCompatActivity() {
    private val viewModel: CreateFriendsViewModel by viewModels()

    lateinit var etName: EditText
    lateinit var etNumber: EditText
    lateinit var tvSave: TextView
    lateinit var sName: String
    lateinit var sNumber: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_friends)
        etName = findViewById(R.id.et_name)
        etNumber = findViewById(R.id.et_number)
        tvSave = findViewById(R.id.tv_save_User)
        tvSave.setOnClickListener {
            sName = etName.text.toString().trim()
            sNumber = etNumber.text.toString().trim()

            lifecycleScope.launch {
                viewModel.insertPerson(Person(null, sName, null, sNumber))
            }
            finish()
        }
    }
}