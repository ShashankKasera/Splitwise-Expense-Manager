package com.shashank.splitterexpensemanager.authentication.login

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.shashank.splitterexpensemanager.authentication.R
import com.shashank.splitterexpensemanager.core.CategoryImages
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.isValidGmail
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.core.network.NetworkCallState
import com.shashank.splitterexpensemanager.localdb.model.Category
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.shashank.splitterexpensemanager.core.R as RCore

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var emailAddress: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: TextView
    private lateinit var singUpBtn: TextView
    private lateinit var loader: View
    private lateinit var sEmailAddress: String
    private lateinit var sPassword: String
    private val viewModel: LoginViewModel by viewModels()

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
            if (validation()) {
                viewModel.login(sEmailAddress, sPassword)
            }
        }

        lifecycleScope.launch {
            viewModel.networkState.collect {
                when (it) {
                    is NetworkCallState.Error -> {
                        Toast.makeText(this@LoginActivity, it.errorMsg, Toast.LENGTH_LONG).show()
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
        singUpBtn.setOnClickListener {
            actionProcessor.process(ActionRequestSchema(ActionType.REGISTRATION.name))
        }
    }

    private fun insertAllCategory() {
        lifecycleScope.launch {
            viewModel.insertAllCategory(
                Category(
                    null,
                    getString(RCore.string.Game),
                    CategoryImages.GAME_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Movie),
                    CategoryImages.MOVIE_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Music),
                    CategoryImages.MUSIC_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Sports),
                    CategoryImages.SPORT_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Dining_Out),
                    CategoryImages.DINING_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Groceries),
                    CategoryImages.GROCERIES_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Liquor),
                    CategoryImages.LIQUOR_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Electronics),
                    CategoryImages.ELECTRONICS_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.furniture),
                    CategoryImages.FURNITURE_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Household_supplies),
                    CategoryImages.HOUSEHOLD_SUPPLIES
                ),
                Category(
                    null,
                    getString(RCore.string.Maintenance),
                    CategoryImages.MAINTENANCE_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Mortgage),
                    CategoryImages.MORTGAGE_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Pets),
                    CategoryImages.PETS_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Rent),
                    CategoryImages.HOME_RENT
                ),
                Category(
                    null,
                    getString(RCore.string.Childcare),
                    CategoryImages.CHILDCARE_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Clothing),
                    CategoryImages.CLOTHING_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Education),
                    CategoryImages.EDUCATION_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Gift),
                    CategoryImages.GIFT_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Insurance),
                    CategoryImages.INSURANCE_icon
                ),
                Category(
                    null,
                    getString(RCore.string.Medical_Expenses),
                    CategoryImages.MEDICAL_EXPENSES_ICON
                ),
                Category(
                    null,
                    getString(RCore.string.Taxes),
                    CategoryImages.TAXES_ICON
                )
            )
        }
    }

    private fun validation() =
        when {
            sEmailAddress.isEmpty() || sPassword.isEmpty() -> {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_LONG).show()
                false
            }

            !sEmailAddress.isValidGmail(sEmailAddress) -> {
                Toast.makeText(
                    this,
                    getString(R.string.please_enter_valid_email_id),
                    Toast.LENGTH_LONG
                ).show()
                false
            }

            sPassword.length < 6 -> {
                Toast.makeText(
                    this,
                    getString(R.string.please_enter_valid_password),
                    Toast.LENGTH_LONG
                ).show()
                false
            }

            else -> {
                true
            }
        }
}