package com.example.todoapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todoapplication.dao.NotesDao;
import com.example.todoapplication.dao.UserDao;
import com.example.todoapplication.model.Note;
import com.example.todoapplication.model.User;

@Database( entities = {Note.class, User.class}, version = 1)
public abstract class AllDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();
    public abstract UserDao userDao();
    public static AllDatabase INSTANCE;

    public static AllDatabase getDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AllDatabase.class, "todo").allowMainThreadQueries().build();
        }

        return INSTANCE;
    }
}
