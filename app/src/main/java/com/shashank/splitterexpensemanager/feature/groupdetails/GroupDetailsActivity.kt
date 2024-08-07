package com.shashank.splitterexpensemanager.feature.groupdetails

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.CommonImages
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.GroupDetails
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GroupDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var rvOweOwed: RecyclerView
    lateinit var tvGroupName: TextView
    lateinit var cvBalances: CardView
    lateinit var cvTotal: CardView
    lateinit var cvSettleUp: CardView
    lateinit var tvOverallOweOrOwed: TextView
    lateinit var tvNoExpenses: TextView
    lateinit var tvYouAreTheOnlyOneHere: TextView
    lateinit var clOweOwed: ConstraintLayout
    lateinit var oweOwedAdapter: OweOwedAdapter
    lateinit var tvOweOrOwedOther: TextView
    lateinit var ivSetting: ImageView
    lateinit var llAddGroupMember: LinearLayout
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    lateinit var ivBack: ImageView
    lateinit var civGroup: CircleImageView
    private var oweOwedList = mutableListOf<Pair<Person, Double>>()
    private val viewModel: GroupDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)
        var groupId: Long = intent.extras?.getLong(GROUP_ID) ?: -1
        groupId = if (groupId != -1L) intent.getLongExtra(GROUP_ID, -1) else -1
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        viewModel.groupId = groupId
        viewModel.personId = personId
        init()
        recyclerViewSetUp()
        navigationForTotal(groupId)
        navigationForBalances(groupId)
        navigationForSettleUp(groupId)
        navigationForGroupSettings(groupId)
        navigationForAddFriends(groupId)
        navigationForGroupMember(groupId)
        getData()
    }

    private fun navigationForSettleUp(groupId: Long) {
        cvSettleUp.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.SETTLE_UP.name,
                    hashMapOf(
                        GROUP_ID to (groupId)
                    )
                )
            )
        }
    }

    private fun navigationForTotal(groupId: Long) {
        cvTotal.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.TOTAL.name,
                    hashMapOf(
                        GROUP_ID to (groupId)
                    )
                )
            )
        }
    }

    private fun init() {
        rvOweOwed = findViewById(R.id.rv_owe_owed)
        tvNoExpenses = findViewById(R.id.tv_no_expenses_here_yet)
        tvGroupName = findViewById(R.id.tv_group_Name_in_detail)
        cvBalances = findViewById(R.id.cv_balance)
        cvTotal = findViewById(R.id.cv_totals)
        cvSettleUp = findViewById(R.id.cv_settle_up)
        tvOverallOweOrOwed = findViewById(R.id.tv_overall_owe)
        llAddGroupMember = findViewById(R.id.ll_group_member)
        tvOweOrOwedOther = findViewById(R.id.tv_other_member)
        clOweOwed = findViewById(R.id.cl_amount)
        tvYouAreTheOnlyOneHere = findViewById(R.id.tv_you_re_the_only_one_here)
        ivSetting = findViewById(R.id.iv_setting_groupDetails)
        civGroup = findViewById(R.id.civ_group)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tablayout)
        ivBack = findViewById(R.id.iv_back_group_details)

        ivBack.setOnClickListener {
            finish()
        }
        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        viewModel.groupDetails(groupId, personId)
        Glide.with(this).load(CommonImages.SETTING_ICON).into(ivSetting)
    }

    override fun onRestart() {
        super.onRestart()
        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        viewModel.groupDetails(groupId, personId)
        getData()
    }

    private fun navigationForBalances(groupId: Long) {
        cvBalances.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.BALANCES.name,
                    hashMapOf(
                        GROUP_ID to (groupId)
                    )
                )
            )
        }
    }

    private fun navigationForGroupSettings(groupId: Long) {
        ivSetting.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.GROUP_SETTING.name,
                    hashMapOf(
                        GROUP_ID to (groupId)
                    )
                )
            )
        }
    }

    private fun navigationForAddFriends(groupId: Long) {
        llAddGroupMember.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_FRIENDS.name,
                    hashMapOf(
                        GROUP_ID to (groupId)
                    )
                )
            )
        }
    }

    private fun navigationForGroupMember(groupId: Long) {
        tvGroupName.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.GROUP_MEMBER.name,
                    hashMapOf(
                        GROUP_ID to (groupId)
                    )
                )
            )
        }
    }


    private fun getData() {
        lifecycleScope.launch {
            viewModel.groupDetails.collect { groupDetails ->
                val groupMemberCount = groupDetails.groupMember.size
                val expensesNotEmpty = groupDetails.expenses.isNotEmpty()
                val repayNotEmpty = groupDetails.repay.isNotEmpty()
                val oweOwedNotEmpty = groupDetails.hashMap.isNotEmpty()

                if (groupMemberCount > 1) {
                    tvYouAreTheOnlyOneHere.gone()
                } else {
                    tvYouAreTheOnlyOneHere.visible()
                }
                Glide.with(this@GroupDetailsActivity).load(groupDetails.group.groupImage).into(civGroup)

                llAddGroupMember.visibility =
                    if (expensesNotEmpty || repayNotEmpty) View.GONE else View.VISIBLE

                if (oweOwedNotEmpty) {
                    tvNoExpenses.gone()
                    clOweOwed.visible()
                    oweOwedList.clear()
                    oweOwedList.addAll(groupDetails.hashMap.toList().filter { it.second != 0.0 })
                    oweOwedAdapter.notifyDataSetChanged()
                } else {
                    oweOwedList.clear()
                }

                if (expensesNotEmpty || oweOwedList.isNotEmpty()) {
                    overall(groupDetails, oweOwedList.size)

                    if (oweOwedList.isNotEmpty()) {
                        rvOweOwed.visible()
                        tvOweOrOwedOther.visibility =
                            if (oweOwedList.size > 2) View.VISIBLE else View.GONE
                        tvOweOrOwedOther.text =
                            getString(R.string.plus_other_balance, (oweOwedList.size - 2))
                    } else {
                        rvOweOwed.gone()
                        tvOweOrOwedOther.gone()
                    }
                } else {
                    tvNoExpenses.visible()
                    clOweOwed.gone()
                }

                groupDetails.group?.let { tvGroupName.text = it.groupName }
            }
        }
    }


    private fun overall(it: GroupDetails, size: Int) {
        if (size == 0) {
            tvOverallOweOrOwed.visible()
            tvOverallOweOrOwed.text = getString(R.string.you_are_all_settled_up_in_this_group)
            tvOverallOweOrOwed.setTextColor(tvOverallOweOrOwed.context.resources.getColor(R.color.black))
        } else if (size > 1) {
            tvOverallOweOrOwed.visible()
            var overall = 0.0
            var oweAmount = 0.0
            var owedAmount = 0.0

            it.hashMap.forEach { (_, value) ->
                if (value > 0) owedAmount += value
                if (value < 0) oweAmount += value
            }

            overall = oweAmount + owedAmount

            val colorResId =
                if (overall == 0.0) R.color.black else if (overall < 0) R.color.primary_dark else R.color.green
            val absOverall = Math.abs(overall)

            tvOverallOweOrOwed.text = if (overall == 0.0) {
                getString(R.string.you_are_settled_up_overall)
            } else if (overall < 0) {
                getString(R.string.you_owe_rs_overall, absOverall.formatNumber(2))
            } else {
                getString(R.string.you_are_owed_rs_overall, absOverall.formatNumber(2))
            }

            tvOverallOweOrOwed.setTextColor(tvOverallOweOrOwed.context.resources.getColor(colorResId))
        } else {
            tvOverallOweOrOwed.gone()
        }
    }

    private fun recyclerViewSetUp() {
        oweOwedAdapter = OweOwedAdapter(oweOwedList)
        rvOweOwed.layoutManager = LinearLayoutManager(this)
        rvOweOwed.adapter = oweOwedAdapter

        val adapter = ViewPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}