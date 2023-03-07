package com.route.todo_app.database.model

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time;
    }

    @TypeConverter
    fun toDate(time: Long): Date {
        return Date(time);
    }
}