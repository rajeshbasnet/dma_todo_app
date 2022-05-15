package com.example.todoapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String username;
    @ColumnInfo
    public String email;
    @ColumnInfo
    public String password;
}
