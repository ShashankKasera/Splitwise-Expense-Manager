package com.shashank.splitterexpensemanager.core.extension

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Calendar

private var isDatePickerShowing: Boolean = false

fun Fragment.showToast(text: String, durationLong: Boolean = false) {
    context?.showToast(text, durationLong)
}

fun Fragment.getBackgroundDrawable(@DrawableRes id: Int): Drawable? =
    ContextCompat.getDrawable(requireContext(), id)

fun Fragment.getResolvedColor(@ColorRes id: Int): Int = ContextCompat.getColor(requireContext(), id)
fun Fragment.showBirthDayPicker(
    selectedDate: Long?,
    positiveButtonClick: (Long) -> Unit

) {
    if (isDatePickerShowing) return
    val today = Calendar.getInstance()
    today.add(Calendar.YEAR, 18)
    val year18Ago = today.timeInMillis
    val validators: ArrayList<CalendarConstraints.DateValidator> = ArrayList()
    validators.add(DateValidatorPointBackward.before(year18Ago))

    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Titel")
        .setSelection(selectedDate)
        .setCalendarConstraints(
            CalendarConstraints.Builder().setEnd(MaterialDatePicker.todayInUtcMilliseconds())
                .setValidator(CompositeDateValidator.allOf(validators)).build()
        )
        .build()

    datePicker.addOnPositiveButtonClickListener {
        positiveButtonClick(it)
    }

    datePicker.addOnDismissListener {
        isDatePickerShowing = false
    }

    datePicker.show(this.childFragmentManager, "")
    isDatePickerShowing = true
}