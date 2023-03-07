package com.route.todo_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.route.todo_app.database.dao.TodoDao
import com.route.todo_app.database.model.DateConverter
import com.route.todo_app.database.model.Todo

@Database(entities = [Todo::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        private val DATABASE_NAME: String = "todo-Database";
        private var myDatabase: MyDatabase? = null;
        fun getInstance(context: Context): MyDatabase {
            // single object from the database(singleton pattern)
            if (myDatabase == null) {
                myDatabase = Room.databaseBuilder(
                    context,
                    MyDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // ANR -> wait or kill ?
                    .build() //return object from the data base
            }
            return myDatabase!!;
        }
    }
}