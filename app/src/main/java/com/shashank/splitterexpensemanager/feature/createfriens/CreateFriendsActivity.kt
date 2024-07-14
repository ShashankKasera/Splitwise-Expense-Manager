package com.shashank.splitterexpensemanager.feature.createfriens

import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.CommonImages
import com.shashank.splitterexpensemanager.core.extension.EMPTY
import com.shashank.splitterexpensemanager.core.extension.capitalizeFirstLetter
import com.shashank.splitterexpensemanager.core.extension.isValidMobileNumber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateFriendsActivity : AppCompatActivity() {
    private val viewModel: CreateFriendsViewModel by viewModels()

    private lateinit var etName: EditText
    private lateinit var etNumber: EditText
    private lateinit var tvDone: TextView
    private lateinit var toolbar: TextView
    private lateinit var ivBack: ImageView
    private lateinit var llMale: ConstraintLayout
    private lateinit var llFemale: ConstraintLayout
    private lateinit var tvMale: TextView
    private lateinit var tvFemale: TextView
    private lateinit var ivMale: ImageView
    private lateinit var ivFemale: ImageView
    private var gender = String.EMPTY
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_friends)
        inti()
        tvDone.setOnClickListener {
            val sName = etName.text.toString().trim()
            val sNumber = etNumber.text.toString().trim()
            if (validation(sName, sNumber)) {
                viewModel.insertPerson(sName.capitalizeFirstLetter(sName), sNumber, gender)
                finish()
            }
        }

        llMale.setOnClickListener {
            gender = getString(R.string.male)
            tvFemale.setTextColor(tvFemale.context.getResources().getColor(R.color.black))
            tvMale.setTextColor(tvMale.context.getResources().getColor(R.color.white))
            llMale.setBackgroundResource(R.drawable.main_gradient)
            llFemale.setBackgroundResource(R.drawable.red_border)
            ivFemale.setColorFilter(getColor(R.color.primary_mid), PorterDuff.Mode.SRC_IN)
            ivMale.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN)
        }
        llFemale.setOnClickListener {
            gender = getString(R.string.female)
            tvFemale.setTextColor(tvFemale.context.getResources().getColor(R.color.white))
            tvMale.setTextColor(tvMale.context.getResources().getColor(R.color.black))
            llMale.setBackgroundResource(R.drawable.red_border)
            ivFemale.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN)
            ivMale.setColorFilter(getColor(R.color.primary_mid), PorterDuff.Mode.SRC_IN)
            llFemale.setBackgroundResource(R.drawable.main_gradient)
        }
    }

    private fun inti() {
        etName = findViewById(R.id.et_name)
        etNumber = findViewById(R.id.et_number)
        tvDone = findViewById(R.id.iv_done)
        llMale = findViewById(R.id.ll_male)
        llFemale = findViewById(R.id.ll_female)
        tvMale = findViewById(R.id.tv_male)
        tvFemale = findViewById(R.id.tv_female)
        ivMale = findViewById(R.id.iv_male)
        ivFemale = findViewById(R.id.iv_female)
        toolbar = findViewById(R.id.tv_tb_create_friend)
        ivBack = findViewById(R.id.iv_tb_create_friend)

        toolbar.text = getString(R.string.create_friend)
        ivBack.setOnClickListener {
            finish()
        }

        Glide.with(this@CreateFriendsActivity).load(CommonImages.MALE)
            .into(ivMale)
        Glide.with(this@CreateFriendsActivity).load(CommonImages.FEMALE)
            .into(ivFemale)
    }

    private fun validation(sName: String, sNumber: String) =
        when {
            sName.isEmpty() -> {
                Toast.makeText(
                    this@CreateFriendsActivity,
                    getString(R.string.enter_a_name),
                    Toast.LENGTH_LONG
                ).show()
                false
            }

            sNumber.isEmpty() -> {
                Toast.makeText(
                    this@CreateFriendsActivity,
                    getString(R.string.enter_a_number),
                    Toast.LENGTH_LONG
                ).show()
                false
            }

            !sNumber.isValidMobileNumber(sNumber) -> {
                Toast.makeText(
                    this@CreateFriendsActivity,
                    getString(R.string.enter_a_valid_mobile_number),
                    Toast.LENGTH_LONG
                ).show()
                false
            }

            gender.isEmpty() -> {
                Toast.makeText(
                    this@CreateFriendsActivity,
                    getString(R.string.select_gender),
                    Toast.LENGTH_LONG
                ).show()
                false
            }

            else -> {
                true
            }
        }
}