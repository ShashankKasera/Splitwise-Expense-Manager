package com.shashank.splitterexpensemanager.feature.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.login.LoginActivity
import com.shashank.splitterexpensemanager.core.CommonImages
import com.shashank.splitterexpensemanager.core.FEMALE
import com.shashank.splitterexpensemanager.core.MALE
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : Fragment() {

    lateinit var toolbar: Toolbar
    lateinit var tvPersonName: TextView
    lateinit var tvPersonEmail: TextView
    lateinit var tvPersonGender: TextView
    lateinit var cvLogout: CardView
    lateinit var cvLogoutYes: CardView
    lateinit var cvLogoutNo: CardView
    private val viewModel: AccountViewModel by viewModels()
    lateinit var civPersonImage: CircleImageView
    lateinit var dialog: BottomSheetDialog

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var actionProcessor: ActionProcessor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_account, container, false)
        toolbar = v.findViewById(R.id.tb_account)
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        tvPersonName = v.findViewById(R.id.tv_name_account)
        tvPersonEmail = v.findViewById(R.id.tv_emil_account)
        tvPersonGender = v.findViewById(R.id.tv_gender_account)
        civPersonImage = v.findViewById(R.id.civ_account)
        cvLogout = v.findViewById(R.id.cv_log_out)
        toolbar.setTitle(getString(R.string.account))

        viewModel.loadPerson(personId)
        lifecycleScope.launch {
            viewModel.person.collect {
                tvPersonName.text = it.name
                tvPersonEmail.text = it.emailId
                tvPersonGender.text = it.gender
                if (it.gender == MALE) {
                    Glide.with(this@AccountFragment).load(CommonImages.USER_ICON)
                        .into(civPersonImage)
                } else if (it.gender == FEMALE) {
                    Glide.with(this@AccountFragment).load(CommonImages.GIRL).into(civPersonImage)
                }
            }
        }

        logOut()
        return v
    }

    private fun logOut() {
        cvLogout.setOnClickListener {
            context?.let {
                dialog = BottomSheetDialog(it, R.style.BottomSheetDialog)
            }
            val view = layoutInflater.inflate(R.layout.logout_confirmation, null)
            cvLogoutYes = view.findViewById(R.id.cv_log_out_yes)
            cvLogoutNo = view.findViewById(R.id.cv_log_out_no)
            dialog.setContentView(view)
            dialog.show()

            cvLogoutNo.setOnClickListener {
                dialog.dismiss()
            }
            cvLogoutYes.setOnClickListener {
                val intent = Intent(
                    requireActivity(),
                    LoginActivity::class.java
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}