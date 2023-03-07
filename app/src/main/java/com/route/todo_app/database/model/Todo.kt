package com.route.todo_app.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "ID")
    @ColumnInfo // make this attribute to be a column in the database
    val id: Int? = null,
//    @ColumnInfo(name = "title") // column name in the database is now equal "title"
    @ColumnInfo
    val name: String? = null,
    @ColumnInfo
    val details: String? = null,
    @ColumnInfo
    val date: Date? = null,
//    @Ignore // this attribute is now in the data class but not in the database
    @ColumnInfo
    val is_done: Boolean? = false

)