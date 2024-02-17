package com.shashank.splitterexpensemanager.authentication.registration

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shashank.splitterexpensemanager.authentication.R
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.core.network.NetworkCallState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
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
    private lateinit var databaseReference: DatabaseReference

    @Inject
    lateinit var actionProcessor: ActionProcessor

    private val viewModel: RegistrationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        databaseReference = FirebaseDatabase.getInstance().reference
        supportActionBar?.hide()
        userName = findViewById(R.id.et_User)
        emailAddress = findViewById(R.id.et_Email)
        password = findViewById(R.id.et_Password)
        singUpBtn = findViewById(R.id.tv_Registration)
        logInUpBtn = findViewById(R.id.tv_Login)
        loader = findViewById(R.id.loader_Registration)
        singUpBtn.setOnClickListener {
            sUserName = userName.text.toString().trim()
            sEmailAddress = emailAddress.text.toString().trim()
            sPassword = password.text.toString().trim()
            if (sUserName.isEmpty() && sEmailAddress.isEmpty() && sPassword.isEmpty()) {
                Toast.makeText(this, "${getString(R.string.fill_all_fields)}", Toast.LENGTH_LONG).show()
            } else {
                viewModel.registration(sUserName, sEmailAddress, sPassword)
            }
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
                        actionProcessor.process(ActionRequestSchema(ActionType.DASH_BOARD.name))
                    }
                }
            }
        }
        logInUpBtn.setOnClickListener {
            actionProcessor.process(ActionRequestSchema(ActionType.LOGIN.name))
        }
    }
}