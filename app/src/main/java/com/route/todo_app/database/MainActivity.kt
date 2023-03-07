package com.route.todo_app.database

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import com.route.todo_app.R

class MainActivity : AppCompatActivity() {

    lateinit var bottom_navigation: BottomNavigationView
    lateinit var addButton: FloatingActionButton
    val todoListFragment = TodoListFragment()
    val settingsFragment = SettingsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        MyDatabase.getInstance(this).todoDao()
        bottom_navigation = findViewById(R.id.navigation_view)
        addButton = findViewById(R.id.add_btn)
        addButton.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this,UpdateTodoActivity::class.java)
//            startActivity(intent)
            showAddBottomSheet()
        })
        bottom_navigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            if (item.itemId == R.id.navigation_list) {
                pushFragment(todoListFragment)
            } else if (item.itemId == R.id.navigation_settings) {
                pushFragment(settingsFragment)
            }
            return@OnItemSelectedListener true;
        })
        bottom_navigation.selectedItemId = R.id.navigation_list
    }

    private fun showAddBottomSheet() {
        val addBottomSheet = AddTodoBottomSheet();
        addBottomSheet.show(supportFragmentManager, "");
        addBottomSheet.onTodoAdedListener = object : AddTodoBottomSheet.OnTodoAdedListener {
            override fun onTodoAdded() {
                // refresh todos list from database inside list fragments
                todoListFragment.getTodosListFromDB();
            }
        }

    }

    fun pushFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}