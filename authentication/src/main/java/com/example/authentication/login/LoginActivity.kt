package com.example.authentication.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.authentication.MainActivity
import com.example.authentication.registration.RegistrationActivity
import com.example.core.actionprocessor.ActionProcessor
import com.example.core.actionprocessor.ActionType
import com.example.core.actionprocessor.model.ActionRequestSchema
import com.example.core.extension.gone
import com.example.core.extension.visible
import com.example.core.network.NetworkCallState

import com.example.splitwiseexpensemanager.authentication.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var emailAddress: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: TextView
    private lateinit var singUpBtn: TextView
    private lateinit var loader: View

    private lateinit var sEmailAddress: String
    private lateinit var sPassword: String

    private val viewModel:LoginViewModel by viewModels()

    @Inject
    lateinit var actionProcessor: ActionProcessor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        emailAddress = findViewById(R.id.et_Email)
        password = findViewById(R.id.et_Password)
        loginBtn = findViewById(R.id.tv_login)
        singUpBtn = findViewById(R.id.tv_sing_up)
        loader = findViewById(R.id.loader_login)

        loginBtn.setOnClickListener {
            sEmailAddress = emailAddress.text.toString().trim()
            sPassword = password.text.toString().trim()
            viewModel.login(sEmailAddress, sPassword)
        }

        lifecycleScope.launch {
            viewModel.networkState.collect {
                when(it){
                    is NetworkCallState.Error -> {
                        Log.i("hgk", "onCreate:Error ${it.errorMsg}")
                        loader.gone()
                    }
                    NetworkCallState.Init -> {
                        Log.i("hgk", "onCreate:init ")

                    }
                    NetworkCallState.Loading -> {
                        loader.visible()
                        Log.i("hgk", "onCreate:Loading")
                    }
                    NetworkCallState.Success -> {
                        Log.i("hgk", "onCreate:Success")
                        loader.gone()

                        actionProcessor.process(ActionRequestSchema(ActionType.DASH_BOARD.name,))
                    }
                }
            }
        }

        singUpBtn.setOnClickListener {
            actionProcessor.process(ActionRequestSchema(ActionType.REGISTRATION.name,))
        }

    }


}