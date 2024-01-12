package com.example.authentication.registration
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.authentication.login.LoginActivity
import com.example.core.extension.gone
import com.example.core.extension.visible
import com.example.core.network.NetworkCallState
import com.example.splitwiseexpensemanager.authentication.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
class RegistrationActivity : AppCompatActivity() {
    private lateinit var userName: EditText
    private lateinit var emailAddress: EditText
    private lateinit var password: EditText
    private lateinit var singUpBtn: TextView
    private lateinit var logInUpBtn: TextView
    private lateinit var loader: View
    private lateinit var sUserName: String
    private lateinit var sEmailAddress: String
    private lateinit var sPassword: String
    private lateinit var auth: FirebaseAuth
    private val viewModel: RegistrationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.hide()
        userName = findViewById(R.id.et_User)
        emailAddress = findViewById(R.id.et_Email)
        password = findViewById(R.id.et_Password)
        singUpBtn = findViewById(R.id.tv_Registration)
        logInUpBtn = findViewById(R.id.tv_Login)
        loader = findViewById(R.id.loader_Registration)
        auth = FirebaseAuth.getInstance()
        singUpBtn.setOnClickListener {
            sUserName = userName.text.toString().trim()
            sEmailAddress = emailAddress.text.toString().trim()
            sPassword = password.text.toString().trim()
            viewModel.registration(sEmailAddress, sPassword)
        }
        lifecycleScope.launch {
            viewModel.networkState.collect {
                when (it) {
                    is NetworkCallState.Error -> {
                        loader.gone()
                    }
                    NetworkCallState.Init -> {
                    }
                    NetworkCallState.Loading -> {
                        loader.visible()
                    }
                    NetworkCallState.Success -> {
                        loader.gone()
                        val myIntent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                        startActivity(myIntent)
                    }
                }
            }
        }
        logInUpBtn.setOnClickListener {
            val myIntent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(myIntent)
        }
    }
}