package com.shashank.splitterexpensemanager.feature.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.shashank.splitterexpensemanager.R

class AccountFragment : Fragment() {
    lateinit var toolbar: Toolbar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_account, container, false)
        toolbar = v.findViewById(R.id.tb_account)

        toolbar.setTitle(getString(R.string.account))
        return v
    }
}