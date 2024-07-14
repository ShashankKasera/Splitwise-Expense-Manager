package com.shashank.splitterexpensemanager.feature.addexpense

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.AddExpensesImages
import com.shashank.splitterexpensemanager.core.CATEGORY
import com.shashank.splitterexpensemanager.core.EXPENSES_ID
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.GROUP_MEMBER
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.UPDATE_EXPENSES
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.extension.EMPTY
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.shortenName
import com.shashank.splitterexpensemanager.core.extension.showToast
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.feature.category.CategoryActivity
import com.shashank.splitterexpensemanager.feature.groupmember.GroupMemberActivity
import com.shashank.splitterexpensemanager.model.Category
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AddExpensesActivity : AppCompatActivity() {

    @Inject
    lateinit var actionProcessor: ActionProcessor

    @Inject
    lateinit var sharedPref: SharedPref
    lateinit var toolbar: TextView
    lateinit var ivBack: ImageView
    lateinit var clCategory: ConstraintLayout
    lateinit var llDatePicker: LinearLayout
    lateinit var tvGroupName: TextView
    lateinit var tvDate: TextView
    lateinit var clDate: ConstraintLayout
    lateinit var datePicker: DatePicker
    lateinit var llTimePicker: LinearLayout
    lateinit var tvTime: TextView
    lateinit var clTime: ConstraintLayout
    lateinit var timePicker: TimePicker
    lateinit var civGroup: CircleImageView
    lateinit var tvCategoryName: TextView
    lateinit var ivCategoryImage: CircleImageView
    lateinit var civRsImage: CircleImageView
    lateinit var civDateImage: CircleImageView
    lateinit var civTimeImage: CircleImageView
    lateinit var civDescriptionImage: CircleImageView
    lateinit var etAmount: EditText
    lateinit var tvDescription: EditText
    lateinit var tvWhoPay: TextView
    lateinit var cvSave: CardView
    private var categoryId: Long = -1
    private var personId: Long = 0
    private val viewModel: AddExpensesViewModel by viewModels()

    val categoryResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val receivedCategory = data?.getParcelableExtra<Category>(CATEGORY)

            tvCategoryName.text = receivedCategory?.categoryName
            Glide.with(this).load(receivedCategory?.categoryImage ?: String.EMPTY).into(ivCategoryImage)
            categoryId = receivedCategory?.id ?: 0
        }
    }
    val groupMemberResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val receivedPerson = data?.getParcelableExtra<Person>(GROUP_MEMBER)
            personId = receivedPerson?.id ?: 0
            tvWhoPay.text = receivedPerson?.name?.shortenName(receivedPerson.name ?: String.EMPTY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        val expensesId: Long = intent.extras?.getLong(EXPENSES_ID) ?: 0
        val updateFlag: Boolean = intent.extras?.getBoolean(UPDATE_EXPENSES) ?: false
        personId = sharedPref.getValue(PERSON_ID, 0L) as Long

        init(groupId)

        if (updateFlag) {
            viewModel.allOweOrOwed(expensesId)
            getInitialDataExpensesForUpdate(expensesId)
        } else {
            viewModel.getPerson(personId)
            viewModel.allGroupMember(groupId)
            viewModel.allGroupMember(groupId)
            getInitialDataExpensesForInsert()
        }
        clDate.setOnClickListener {
            getDatePicker()
        }

        clTime.setOnClickListener {
            getTimePicker()
        }

        clCategory.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            categoryResultLauncher.launch(intent)
        }

        tvWhoPay.setOnClickListener {
            val intent = Intent(this, GroupMemberActivity::class.java)
            intent.putExtra(GROUP_ID, groupId)
            groupMemberResultLauncher.launch(intent)
        }

        cvSave.setOnClickListener {
            val amount =
                when {
                    etAmount.text.toString().trim().isEmpty() -> -1.0
                    else -> etAmount.text.toString()
                        .trim().toDouble()
                }
            val date = if (tvDate.text.toString().trim().equals(String.EMPTY)) {
                tvDate.hint.toString().trim()
            } else {
                tvDate.text.toString().trim()
            }
            val time = if (tvTime.text.toString().trim().equals(String.EMPTY)) {
                tvTime.hint.toString()
                    .trim()
            } else {
                tvTime.text.toString().trim()
            }
            val description = tvDescription.text.toString().trim()
            val name = tvWhoPay.text.toString().trim()
            when {
                updateFlag -> {
                    when {
                        validation(amount, categoryId) -> updateExpenses(
                            expensesId,
                            personId,
                            groupId,
                            amount,
                            date,
                            time,
                            description,
                            name
                        )
                    }
                }

                else -> {
                    when {
                        validation(amount, categoryId) -> addExpenses(
                            personId,
                            groupId,
                            amount,
                            date,
                            time,
                            description,
                            name
                        )
                    }
                }
            }
        }
    }


    private fun init(groupId: Long) {
        tvGroupName = findViewById(R.id.tv_group_name_expenses)
        clCategory = findViewById(R.id.cl_category_expenses)
        clDate = findViewById(R.id.cl_date)
        tvDate = findViewById(R.id.tv_date_expenses)
        llDatePicker = findViewById(R.id.ll_dp_expenses)
        datePicker = findViewById(R.id.dp_expenses)
        clTime = findViewById(R.id.cl_time)
        tvTime = findViewById(R.id.tv_time_expenses)
        llTimePicker = findViewById(R.id.ll_TimePicker_expenses)
        timePicker = findViewById(R.id.timePicker_expenses)
        tvCategoryName = findViewById(R.id.tv_category_name_expenses)
        ivCategoryImage = findViewById(R.id.civ_category_image_expenses)
        civGroup = findViewById(R.id.civ_group_image_add_expenses)
        civRsImage = findViewById(R.id.civ_rs)
        civDateImage = findViewById(R.id.civ_date)
        civTimeImage = findViewById(R.id.civ_time)
        civDescriptionImage = findViewById(R.id.civ_description)
        etAmount = findViewById(R.id.et_amount_expenses)
        tvDescription = findViewById(R.id.et_description_expenses)
        tvWhoPay = findViewById(R.id.tv_who_pay_expenses)
        cvSave = findViewById(R.id.cv_save_expenses)
        toolbar = findViewById(R.id.tv_tb_add_expenses)
        ivBack = findViewById(R.id.iv_tb_add_expenses)

        toolbar.text = getString(R.string.add_expenses)
        ivBack.setOnClickListener {
            finish()
        }

        Glide.with(this).load(AddExpensesImages.RS_ICON).into(civRsImage)
        Glide.with(this).load(AddExpensesImages.CATEGORY_ICON).into(ivCategoryImage)
        Glide.with(this).load(AddExpensesImages.CALENDAR_ICON).into(civDateImage)
        Glide.with(this).load(AddExpensesImages.CLOCK_ICON).into(civTimeImage)
        Glide.with(this).load(AddExpensesImages.DESCRIPTION_ICON).into(civDescriptionImage)
        viewModel.getGroup(groupId)
        lifecycleScope.launch {
            viewModel.group.collect {
                if (it != null) tvGroupName.text = it.groupName
                Glide.with(this@AddExpensesActivity).load(it?.groupImage ?: String.EMPTY).into(civGroup)
            }
        }
    }

    private fun getInitialDataExpensesForInsert() {
        tvDate.hint = getCurrentDate()
        tvTime.hint = getCurrentTime()
        lifecycleScope.launch {
            viewModel.whoPayPerson.collect {
                if (it != null) tvWhoPay.text = it.name?.shortenName(it.name ?: String.EMPTY)
            }
        }
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun getDatePicker() {
        llDatePicker.visible()
        val today = Calendar.getInstance()
        datePicker.init(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val formattedDate = String.format("%02d/%02d/%d", day, month + 1, year)
            tvDate.text = formattedDate
            llDatePicker.gone()
        }
    }

    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun getTimePicker() {
        llTimePicker.visible()
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var hour = hour
            var ampm = String.EMPTY
            when {
                hour == 0 -> {
                    hour += 12
                    ampm = "AM"
                }

                hour == 12 -> ampm = "PM"
                hour > 12 -> {
                    hour -= 12
                    ampm = "PM"
                }

                else -> ampm = "AM"
            }
            val msg =
                "${if (hour < 10) "0" + hour else hour} : ${if (minute < 10) "0" + minute else minute} $ampm"
            tvTime.text = msg
            llTimePicker.gone()
        }
    }

    private fun addExpenses(
        personId: Long,
        groupId: Long,
        amount: Double,
        date: String,
        time: String,
        description: String,
        name: String
    ) {
        lifecycleScope.launch {
            viewModel.groupMemberFetchedForInsertOweOwed.collect {
                if (it) {
                    if (viewModel.allGroupMemberList.size > 0) {
                        val numberOfMember = viewModel.allGroupMemberList.size
                        val splitAmount = (amount / numberOfMember)
                        viewModel.insertExpenses(
                            personId,
                            groupId,
                            categoryId,
                            amount,
                            splitAmount,
                            name,
                            date,
                            time,
                            description
                        )
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.expensesInsertSuccessfully.collect {
                if (it) {
                    finish()
                }
            }
        }
    }

    private fun getInitialDataExpensesForUpdate(expensesId: Long) {
        lifecycleScope.launch {
            viewModel.loadExpensesDetails(expensesId)
            viewModel.expensesDetails.collect {
                etAmount.setText(it?.expense?.amount.toString())

                tvCategoryName.text = it?.category?.categoryName
                Glide.with(this@AddExpensesActivity).load(it?.category?.categoryImage ?: String.EMPTY)
                    .into(ivCategoryImage)
                tvDate.text = (it?.expense?.date)
                tvTime.text = it?.expense?.time
                tvDescription.setText(it?.expense?.description)
                tvWhoPay.text = it?.person?.name?.shortenName(it.person.name ?: String.EMPTY)
                categoryId = it?.category?.id ?: -1
                personId = it?.expense?.personId ?: -1
            }
        }
    }

    private fun updateExpenses(
        expensesId: Long,
        personId: Long,
        groupId: Long,
        amount: Double,
        date: String,
        time: String,
        description: String,
        name: String
    ) {
        lifecycleScope.launch {
            viewModel.oweOrOwedFetchedForUpdate.collect {
                if (it) {
                    viewModel.updateExpenses(
                        expensesId,
                        personId,
                        groupId,
                        categoryId,
                        amount,
                        name,
                        date,
                        time,
                        description
                    )
                }
            }
        }

        lifecycleScope.launch {
            viewModel.expensesUpdateSuccessfully.collect {
                if (it) finish()
            }
        }
    }

    private fun validation(amount: Double, categoryId: Long) =

        when {
            amount == -1.0 -> {
                showToast(getString(R.string.please_enter_an_amount))
                false
            }

            amount <= 0.0 -> {
                showToast(getString(R.string.you_must_enter_an_amount))
                false
            }

            categoryId == -1L -> {
                showToast(getString(R.string.please_select_category))
                false
            }

            else -> true
        }
}