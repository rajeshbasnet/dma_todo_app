package com.example.todoapplication.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todoapplication.dao.UserDao;
import com.example.todoapplication.database.AllDatabase;
import com.example.todoapplication.model.User;

import java.util.List;

public class UserRepository {

    public UserDao userDao;
    public LiveData<List<User>> getAllUser;

    public UserRepository(Application application) {
        AllDatabase database = AllDatabase.getDatabaseInstance(application);
        userDao = database.userDao();
        getAllUser = userDao.getAll();
    }

    public void insertUsers(User User) {
        userDao.insertUser(User);
    }

    public void deleteUsers(int id) {
        userDao.deleteUser(id);
    }

    public void updateUsers(User User) {
        userDao.updateUser(User);
    }
}
