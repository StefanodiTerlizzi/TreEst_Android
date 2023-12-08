package com.example.treest.model.DB;

import androidx.room.*;

import java.util.List;
@Dao
public interface UserDAO {
    @Insert
    void InsertAll(User... users);

    @Query("SELECT * FROM User")
    List<User> getAll();

    @Delete
    void delete(User user);

    @Update
    void updateAll(User... users);
}
