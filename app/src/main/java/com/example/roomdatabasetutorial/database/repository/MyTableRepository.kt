package com.example.roomdatabasetutorial.database.repository

import com.example.roomdatabasetutorial.database.MyDatabase
import com.example.roomdatabasetutorial.database.entities.MyTable

class MyTableRepository(
    private val db: MyDatabase
) {

    suspend fun insert(myTable: MyTable) {
        db.getMyTableDao().insert(myTable)
    }

    suspend fun update(myTable: MyTable) {
        db.getMyTableDao().update(myTable)
    }

    suspend fun delete(myTable: MyTable) {
        db.getMyTableDao().delete(myTable)
    }

    suspend fun getAll(): List<MyTable> {
        return db.getMyTableDao().getAll()
    }

    suspend fun getOne(name: String): MyTable? {
        return db.getMyTableDao().getOne(name)
    }
}