package com.shashank.splitterexpensemanager.authentication.login

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.core.network.NetworkCallState
import com.shashank.splitterexpensemanager.authentication.R
import com.shashank.splitterexpensemanager.localdb.model.Category
import com.shashank.splitterexpensemanager.localdb.model.Person
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
            viewModel.login(sEmailAddress, sPassword)
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

                        viewModel.insertPerson(Person(null, null, sEmailAddress, null))

                        viewModel.insertAllCategory(
                            Category(null, "Game", R.drawable.game_icon_png),
                            Category(null, "Movie", R.drawable.movie_icon_png),
                            Category(null, "Music", R.drawable.music_icon_png),
                            Category(null, "Sports", R.drawable.sport_icon_png),
                            Category(null, "Dinig Out", R.drawable.dining_icon_png),
                            Category(null, "Groceries", R.drawable.groceries_icon_png),
                            Category(null, "Liquor", R.drawable.liquor_icon_png),
                            Category(null, "Electronics", R.drawable.electronics_icon_png),
                            Category(null, "furniture", R.drawable.furniture_icon_png),
                            Category(null, "Household supplies", R.drawable.household_supplies_png),
                            Category(null, "Maintenance", R.drawable.maintenance_icon_png),
                            Category(null, "Mortgage", R.drawable.mortgage_icon_png),
                            Category(null, "Pets", R.drawable.pets_icon_png),
                            Category(null, "Rent", R.drawable.home_rent_icon_png),
                            Category(null, "Childcare", R.drawable.childcare_icon_png),
                            Category(null, "Clothing", R.drawable.clothing_icon_png),
                            Category(null, "Education", R.drawable.education_icon_png),
                            Category(null, "Gift", R.drawable.gift_icon_png),
                            Category(null, "Insurance", R.drawable.insurence_icon_ing),
                            Category(
                                null,
                                "Medical expenses",
                                R.drawable.medical_expences_icon_png
                            ),
                            Category(null, "Taxes", R.drawable.taxes_icon_png)
                        )
                        actionProcessor.process(ActionRequestSchema(ActionType.DASH_BOARD.name))
                    }
                }
            }
        }
        singUpBtn.setOnClickListener {
            actionProcessor.process(ActionRequestSchema(ActionType.REGISTRATION.name))
        }
    }
}