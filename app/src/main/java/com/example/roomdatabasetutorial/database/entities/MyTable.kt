package com.example.roomdatabasetutorial.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MyTable")
data class MyTable(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    val dateOfBirth: String

)