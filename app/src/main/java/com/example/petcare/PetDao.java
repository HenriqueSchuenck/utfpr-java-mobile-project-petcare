package com.example.petcare;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface PetDao {
    @Insert
    void insert(Pet pet);

    @Update
    void update(Pet pet);

    @Delete
    void delete(Pet pet);

    @Query("SELECT * FROM pets ORDER BY nome ASC")
    List<Pet> getAllPets();
}