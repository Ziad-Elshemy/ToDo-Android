package com.route.todo_app.database

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
//import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.route.todo_app.R
import com.route.todo_app.database.model.Todo
import java.util.*

class AddTodoBottomSheet : BottomSheetDialogFragment() {
    lateinit var titleLayout: TextInputLayout
    lateinit var detailsLayout: TextInputLayout
    lateinit var chooseDate: TextView
    lateinit var addTodoButton: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        titleLayout = requireView().findViewById(R.id.edit_title_layout)
        detailsLayout = requireView().findViewById(R.id.edit_details_layout)
        chooseDate = requireView().findViewById(R.id.choose_date)
        addTodoButton = requireView().findViewById(R.id.add_todo_btn)
        chooseDate.setText(
            "" + calender.get(Calendar.DAY_OF_MONTH) + "/" +
                    (calender.get(Calendar.MONTH) + 1) + "/" +
                    calender.get(Calendar.YEAR)
        )

        chooseDate.setOnClickListener(View.OnClickListener {
            showDatePicker()
        })
        addTodoButton.setOnClickListener(View.OnClickListener {
            if (validateForm()) {
                // form is valid .. insert todo item
                val title = titleLayout.editText?.text.toString()
                val details = detailsLayout.editText?.text.toString()
                insertTodo(title, details)
            }
        })
    }

    private fun insertTodo(title: String, details: String) {
        clearTime()
        val todo = Todo(
//            id = 1,
            name = title,
            details = details,
            date = calender.time,
//            is_done = false
        )
        MyDatabase.getInstance(requireContext().applicationContext)
            .todoDao().addTodo(todo)
        Toast.makeText(requireContext(), "Todo Added Successfully", Toast.LENGTH_LONG).show()
        //call back to activity to notify insertion is done
        onTodoAdedListener?.onTodoAdded()
        dismiss()
    }

    var onTodoAdedListener: OnTodoAdedListener? = null

    interface OnTodoAdedListener {
        fun onTodoAdded();
    }

    private fun clearTime() {
        calender.clear(Calendar.HOUR)
        calender.clear(Calendar.MINUTE)
        calender.clear(Calendar.SECOND)
        calender.clear(Calendar.MILLISECOND)
    }

    val calender = Calendar.getInstance()

    fun showDatePicker() {
        val date_picker = DatePickerDialog(
            requireContext(),
            { p0, year, month, day ->
                calender.set(Calendar.YEAR, year)
                calender.set(Calendar.MONTH, month)
                calender.set(Calendar.DAY_OF_MONTH, day)
                chooseDate.setText("" + day + "/" + (month + 1) + "/" + (year))
            },
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)
        )
        date_picker.show()
    }

    fun validateForm(): Boolean {
        var is_valid = true
        if (titleLayout.editText?.text.toString().isBlank()) {
            titleLayout.error = "please enter todo title"
            is_valid = false
        } else titleLayout.error = null
        if (detailsLayout.editText?.text.toString().isBlank()) {
            detailsLayout.error = "please enter todo details"
            is_valid = false
        } else detailsLayout.error = null
        return is_valid
    }
}