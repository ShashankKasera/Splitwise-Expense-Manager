package com.shashank.splitterexpensemanager.authentication.registration

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.shashank.splitterexpensemanager.authentication.R
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.core.network.NetworkCallState
import com.shashank.splitterexpensemanager.localdb.model.Category
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.shashank.splitterexpensemanager.core.R as RCore

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

    @Inject
    lateinit var actionProcessor: ActionProcessor
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
            if (sUserName.isEmpty() && sEmailAddress.isEmpty() && sPassword.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_LONG)
                    .show()
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
                        insertAllCategory()
                        actionProcessor.process(ActionRequestSchema(ActionType.DASH_BOARD.name))
                    }
                }
            }
        }
        logInUpBtn.setOnClickListener {
            actionProcessor.process(ActionRequestSchema(ActionType.LOGIN.name))
        }
    }

    private fun insertAllCategory() {
        lifecycleScope.launch {
            viewModel.insertAllCategory(
                Category(
                    null,
                    getString(RCore.string.Game),
                    R.drawable.game_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Movie),
                    R.drawable.movie_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Music),
                    R.drawable.music_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Sports),
                    R.drawable.sport_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Dining_Out),
                    R.drawable.dining_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Groceries),
                    R.drawable.groceries_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Liquor),
                    R.drawable.liquor_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Electronics),
                    R.drawable.electronics_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.furniture),
                    R.drawable.furniture_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Household_supplies),
                    R.drawable.household_supplies_png
                ),
                Category(
                    null,
                    getString(RCore.string.Maintenance),
                    R.drawable.maintenance_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Mortgage),
                    R.drawable.mortgage_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Pets),
                    R.drawable.pets_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Rent),
                    R.drawable.home_rent_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Childcare),
                    R.drawable.childcare_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Clothing),
                    R.drawable.clothing_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Education),
                    R.drawable.education_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Gift),
                    R.drawable.gift_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Insurance),
                    R.drawable.insurance_icon_ing
                ),
                Category(
                    null,
                    getString(RCore.string.Medical_Expenses),
                    R.drawable.medical_expenses_icon_png
                ),
                Category(
                    null,
                    getString(RCore.string.Taxes),
                    R.drawable.taxes_icon_png
                )
            )
        }
    }
}