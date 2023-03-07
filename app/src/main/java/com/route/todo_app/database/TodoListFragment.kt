package com.route.todo_app.database

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.route.todo_app.R
import java.util.*

class TodoListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    lateinit var recyclerView: RecyclerView
    lateinit var calenderView: MaterialCalendarView
    val adapter = TodosRecyclerAdapter(null)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    override fun onResume() {
        super.onResume()
        getTodosListFromDB()
    }

    val calender = Calendar.getInstance()
    fun clearCalenderTime() {
        calender.clear(Calendar.HOUR)
        calender.clear(Calendar.MINUTE)
        calender.clear(Calendar.SECOND)
        calender.clear(Calendar.MILLISECOND)
    }

    public fun getTodosListFromDB() {
        clearCalenderTime()
        val todosList = MyDatabase.getInstance(requireContext())
            .todoDao().getTodosByDate(calender.time) // calender.time return time in milliseconds
        adapter.changeData(todosList.toMutableList())
    }

    private fun initViews() {
        recyclerView = requireView().findViewById(R.id.todos_recycler)
        calenderView = requireView().findViewById(R.id.calendarView)
        calenderView.selectedDate = CalendarDay.today()
        recyclerView.adapter = adapter
        calenderView.setOnDateChangedListener { widget, calenderDay, selected ->
            calender.set(Calendar.DAY_OF_MONTH, calenderDay.day)
            calender.set(Calendar.MONTH, calenderDay.month - 1)
            calender.set(Calendar.YEAR, calenderDay.year)
            getTodosListFromDB()
        }
    }
}