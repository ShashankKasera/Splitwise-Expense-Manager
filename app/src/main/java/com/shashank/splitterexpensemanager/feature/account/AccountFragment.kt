package com.shashank.splitterexpensemanager.feature.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.CommonImages
import com.shashank.splitterexpensemanager.core.FEMALE
import com.shashank.splitterexpensemanager.core.MALE
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
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
    private val viewModel: AccountViewModel by viewModels()
    lateinit var civPersonImage: CircleImageView

    @Inject
    lateinit var sharedPref: SharedPref
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
        return v
    }
}