package com.example.todoapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todoapplication.model.User;
import com.example.todoapplication.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    UserRepository repository;
    public LiveData<List<User>> getAllUser;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        getAllUser = repository.getAllUser;
    }

    public void insertUser_vm(User user) {
        repository.insertUsers(user);
    }

    public void updateUser_vm(User note) {
        repository.updateUsers(note);
    }

    public void deleteUser_vm(int id) {
        repository.deleteUsers(id);
    }
}
