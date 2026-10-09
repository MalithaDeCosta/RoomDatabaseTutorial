package com.example.roomdatabasetutorial.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query

import com.example.roomdatabasetutorial.database.entities.MyTable

@Dao
interface MyTableDao {

    @Insert
    suspend fun insert(myTable: MyTable)

    @Update
    suspend fun update(myTable: MyTable)

    @Delete
    suspend fun delete(myTable: MyTable)

    @Query("SELECT * FROM MyTable ORDER BY id DESC")
    suspend fun getAll(): List<MyTable>

    @Query("SELECT * FROM MyTable WHERE name = :name LIMIT 1")
    suspend fun getOne(name: String): MyTable?

}