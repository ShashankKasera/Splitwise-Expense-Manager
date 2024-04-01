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
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.CATEGORY
import com.shashank.splitterexpensemanager.core.EXPENSES_ID
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.GROUP_MEMBER
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.UPDATE_EXPENSES
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.extension.gone
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
    lateinit var tvCategoryName: TextView
    lateinit var ivCategoryImage: CircleImageView
    lateinit var etAmount: EditText
    lateinit var tvDescription: EditText
    lateinit var tvWhoPay: TextView
    lateinit var cvSave: CardView
    private var categoryId: Long = 0
    private var personId: Long = 0
    private val viewModel: AddExpensesViewModel by viewModels()

    val categoryResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val receivedCategory = data?.getParcelableExtra<Category>(CATEGORY)

            tvCategoryName.text = receivedCategory?.categoryName
            ivCategoryImage.setImageResource(receivedCategory?.categoryImage ?: 0)
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
            tvWhoPay.text = receivedPerson?.name
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
            val amount = etAmount.text.toString().trim().toDouble()
            val date = tvDate.text.toString().trim()
            val time = tvTime.text.toString().trim()
            val description = tvDescription.text.toString().trim()
            val name = tvWhoPay.text.toString().trim()
            if (updateFlag) {
                updateExpenses(expensesId, personId, groupId, amount, date, time, description, name)
            } else {
                addExpenses(personId, groupId, amount, date, time, description, name)
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
        viewModel.getGroup(groupId)
        lifecycleScope.launch {
            viewModel.group.collect {
                if (it != null) tvGroupName.text = it.groupName
            }
        }
    }

    private fun getInitialDataExpensesForInsert() {
        tvDate.text = getCurrentDate()
        tvTime.text = getCurrentTime()
        lifecycleScope.launch {
            viewModel.whoPayPerson.collect {
                if (it != null) tvWhoPay.text = it.name
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
            var ampm = ""
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
                ivCategoryImage.setImageResource(it?.category?.categoryImage ?: 0)
                tvDate.text = (it?.expense?.date)
                tvTime.text = it?.expense?.time
                tvDescription.setText(it?.expense?.description)
                tvWhoPay.text = it?.person?.name
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
}