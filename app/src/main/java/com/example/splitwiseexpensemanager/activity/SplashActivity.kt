package com.example.splitwiseexpensemanager.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.core.actionprocessor.ActionProcessor
import com.example.core.actionprocessor.ActionType
import com.example.core.actionprocessor.model.ActionRequestSchema
import com.example.splitwiseexpensemanager.R
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val SPLASH_DELAY: Long = 3000

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    @Inject
    lateinit var actionProcessor: ActionProcessor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        Handler().postDelayed({
            val currentUser = auth.currentUser
            if (currentUser != null) {
                actionProcessor.process(ActionRequestSchema(ActionType.DASH_BOARD.name))
            } else {
                actionProcessor.process(ActionRequestSchema(ActionType.LOGIN.name))
            }
            finish()
        }, SPLASH_DELAY)
    }
}