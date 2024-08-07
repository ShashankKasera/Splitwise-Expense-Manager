package com.shashank.splitterexpensemanager.feature.friendsdetails

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.CommonImages
import com.shashank.splitterexpensemanager.core.FEMALE
import com.shashank.splitterexpensemanager.core.FRIEND_ID
import com.shashank.splitterexpensemanager.core.MALE
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.EMPTY
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.FriendOweOrOwed
import com.shashank.splitterexpensemanager.model.Group
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FriendsDetailsActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var toolbar: TextView
    lateinit var ivBack: ImageView
    lateinit var rvGroupOweOwed: RecyclerView
    lateinit var tvOverallOweOrOwed: TextView
    lateinit var cvSettledUp: CardView
    lateinit var tvPlus: TextView
    lateinit var ivSetting: ImageView
    lateinit var civFriend: CircleImageView
    lateinit var rvFriendOweOwed: RecyclerView
    lateinit var groupOweOwedAdapter: GroupOweOwedAdapter
    lateinit var friendOweOwedAdapter: FriendOweOwedAdapter
    private var groupOweOwedList = mutableListOf<Pair<Group, Double>>()
    private var friendOweOwedList = mutableListOf<FriendOweOrOwed>()

    private val viewModel: FriendsDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_details)
        var friendId: Long = intent.extras?.getLong(FRIEND_ID) ?: -1
        val personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long

        init(friendId, personId)
        navigationForFriendSettings(friendId)
        cvSettledUp.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.GROUP_SETTLED_UP.name,
                    hashMapOf(
                        PERSON_ID to (personId),
                        FRIEND_ID to (friendId)
                    )
                )
            )
        }
    }

    override fun onRestart() {
        super.onRestart()
        var friendId: Long = intent.extras?.getLong(FRIEND_ID) ?: -1
        val personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long

        viewModel.loadAllFriends(personId, friendId)
    }

    private fun init(friendId: Long, personId: Long) {
        rvGroupOweOwed = findViewById(R.id.rv_group_owe_owed_friend_details)
        civFriend = findViewById(R.id.civ_friends_image)
        toolbar = findViewById(R.id.tv_name_friend_details)
        ivBack = findViewById(R.id.iv_back_friend_details)

        toolbar.text = getString(R.string.friend_details)
        ivBack.setOnClickListener {
            finish()
        }
        ivSetting = findViewById(R.id.iv_setting_friendDetails)
        tvOverallOweOrOwed = findViewById(R.id.tv_overall_owe_friend_details)
        rvFriendOweOwed = findViewById(R.id.rv_owe_owed_friend_details)
        tvPlus = findViewById(R.id.tv_other_member_friend_details)
        cvSettledUp = findViewById(R.id.cv_settle_up_friend_details)
        recyclerViewSetUp()

        viewModel.loadAllFriends(personId, friendId)
        Glide.with(this).load(CommonImages.SETTING_ICON).into(ivSetting)

        getData()
    }

    private fun recyclerViewSetUp() {
        groupOweOwedAdapter = GroupOweOwedAdapter(actionProcessor, groupOweOwedList)
        rvGroupOweOwed.layoutManager = LinearLayoutManager(this)
        rvGroupOweOwed.adapter = groupOweOwedAdapter

        friendOweOwedAdapter = FriendOweOwedAdapter(friendOweOwedList)
        rvFriendOweOwed.layoutManager = LinearLayoutManager(this)
        rvFriendOweOwed.adapter = friendOweOwedAdapter
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.allFriends.collect { friendDetails ->
                toolbar.text = friendDetails.friend.name

                val gender = friendDetails.friend.gender ?: String.EMPTY

                if (gender == MALE) {
                    Glide.with(this@FriendsDetailsActivity).load(CommonImages.USER_ICON)
                        .into(civFriend)
                } else if (gender == FEMALE) {
                    Glide.with(this@FriendsDetailsActivity).load(CommonImages.GIRL).into(civFriend)
                }
                groupOweOwedList.apply {
                    clear()
                    addAll(friendDetails.friendsHashMap.toList())
                }
                groupOweOwedAdapter.notifyDataSetChanged()

                friendOweOwedList.apply {
                    clear()
                    addAll(friendDetails.friendOweOwedList)
                }
                friendOweOwedAdapter.notifyDataSetChanged()

                overall(
                    friendDetails.friendsHashMap.size,
                    friendDetails.friendOweOwedList.size,
                    friendDetails.overallOweOrOwed
                )

                if (friendOweOwedList.size > 3) {
                    tvPlus.visible()
                    tvPlus.text =
                        getString(R.string.plus_other_balance, (friendOweOwedList.size - 2))
                } else {
                    tvPlus.gone()
                }
            }
        }
    }

    private fun overall(friendsHashMapSize: Int, friendOweOwedList: Int, overallOweOrOwed: Double) {
        when {
            friendsHashMapSize == 0 -> {
                tvOverallOweOrOwed.visible()
                tvOverallOweOrOwed.text = getString(R.string.no_expenses_friend)
            }

            friendOweOwedList == 0 -> {
                tvOverallOweOrOwed.visible()
                tvOverallOweOrOwed.text = getString(R.string.you_are_all_settled_up)
                tvOverallOweOrOwed.setTextColor(tvOverallOweOrOwed.context.resources.getColor(R.color.black))
            }

            friendOweOwedList > 1 -> {
                tvOverallOweOrOwed.visible()
                val overall = overallOweOrOwed

                val colorResId = when {
                    overall == 0.0 -> R.color.black
                    overall < 0 -> R.color.primary_dark
                    else -> R.color.green
                }

                val absOverall = Math.abs(overall)
                val oweOrOwedText = when {
                    overall == 0.0 -> getString(R.string.you_are_settled_up_overall)
                    overall < 0 -> getString(
                        R.string.you_owe_rs_overall,
                        absOverall.formatNumber(2)
                    )

                    else -> getString(R.string.you_are_owed_rs_overall, absOverall.formatNumber(2))
                }

                tvOverallOweOrOwed.text = oweOrOwedText
                tvOverallOweOrOwed.setTextColor(
                    tvOverallOweOrOwed.context.resources.getColor(
                        colorResId
                    )
                )
            }

            else -> tvOverallOweOrOwed.gone()
        }
    }

    private fun navigationForFriendSettings(friendId: Long) {
        ivSetting.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.FRIEND_SETTING.name,
                    hashMapOf(
                        FRIEND_ID to (friendId)
                    )
                )
            )
        }
    }
}