package com.shashank.splitterexpensemanager.feature.friendsdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.FRIEND_ID
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.feature.friends.FriendsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class FriendsDetailsActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPref: SharedPref
    @Inject
    lateinit var actionProcessor: ActionProcessor

    private val viewModel: FriendsDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_details)
        var friendId: Long = intent.extras?.getLong(FRIEND_ID) ?: -1
        val personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long

        viewModel.loadAllFriends(personId,friendId)
        lifecycleScope.launch {
            viewModel.allFriends.collect{
                Log.i("huhu", "onCreate: $it")
            }
        }

    }
}