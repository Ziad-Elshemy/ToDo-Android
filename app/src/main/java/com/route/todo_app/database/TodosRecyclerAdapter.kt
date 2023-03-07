package com.route.todo_app.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.todo_app.R
import com.route.todo_app.database.model.Todo

class TodosRecyclerAdapter(var items: MutableList<Todo>?) :
    RecyclerView.Adapter<TodosRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!!.get(position)
        holder.title.setText(item.name)
        holder.description.setText(item.details)

    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    public fun changeData(newItems: MutableList<Todo>) {
        items = newItems
        notifyDataSetChanged() // notifies the adapter that the data is changed
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.item_title)
        val description: TextView = itemView.findViewById(R.id.item_description)
        val img_check: ImageView = itemView.findViewById(R.id.item_check_btn)

    }
}