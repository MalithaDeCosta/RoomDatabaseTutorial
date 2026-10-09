package com.example.roomdatabasetutorial

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabasetutorial.database.MyDatabase
import com.example.roomdatabasetutorial.database.entities.MyTable
import com.example.roomdatabasetutorial.database.repository.MyTableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityData : ViewModel() {

    // Stores all database records
    private val _listMyTable =
        MutableLiveData<List<MyTable>>(emptyList())

    val listMyTable: LiveData<List<MyTable>> =
        _listMyTable

    // Stores the record selected for updating
    private val _selectedRecord =
        MutableLiveData<MyTable?>(null)

    val selectedRecord: LiveData<MyTable?> =
        _selectedRecord

    // Tracks successful insert operations
    private val _insertSuccess =
        MutableLiveData(false)

    val insertSuccess: LiveData<Boolean> =
        _insertSuccess

    // Displays success and error messages
    private val _message =
        MutableLiveData<String?>(null)

    val message: LiveData<String?> =
        _message

    // Get database repository
    private fun getRepository(
        context: Context
    ): MyTableRepository {

        val database = MyDatabase.getInstance(
            context.applicationContext
        )

        return MyTableRepository(database)
    }

    // LOAD ALL RECORDS
    fun loadData(context: Context) {

        val appContext = context.applicationContext

        viewModelScope.launch {

            try {

                val records = withContext(Dispatchers.IO) {

                    getRepository(appContext).getAll()
                }

                _listMyTable.value = records

            } catch (e: Exception) {

                _message.value =
                    "Failed to load records: ${e.message}"
            }
        }
    }

    // INSERT A RECORD
    fun insertData(
        context: Context,
        myTable: MyTable
    ) {

        val appContext = context.applicationContext

        viewModelScope.launch {

            try {

                val records = withContext(Dispatchers.IO) {

                    val repository = getRepository(appContext)

                    repository.insert(myTable)

                    repository.getAll()
                }

                _listMyTable.value = records

                _insertSuccess.value = true

                _message.value =
                    "Record inserted successfully"

            } catch (e: Exception) {

                _message.value =
                    "Insert failed: ${e.message}"
            }
        }
    }

    // UPDATE A RECORD
    fun updateData(
        context: Context,
        myTable: MyTable
    ) {

        val appContext = context.applicationContext

        viewModelScope.launch {

            try {

                val records = withContext(Dispatchers.IO) {

                    val repository = getRepository(appContext)

                    repository.update(myTable)

                    repository.getAll()
                }

                _listMyTable.value = records

                _selectedRecord.value = null

                _message.value =
                    "Record updated successfully"

            } catch (e: Exception) {

                _message.value =
                    "Update failed: ${e.message}"
            }
        }
    }

    // DELETE A RECORD
    fun deleteData(
        context: Context,
        myTable: MyTable
    ) {

        val appContext = context.applicationContext

        viewModelScope.launch {

            try {

                val records = withContext(Dispatchers.IO) {

                    val repository = getRepository(appContext)

                    repository.delete(myTable)

                    repository.getAll()
                }

                _listMyTable.value = records

                _message.value =
                    "Record deleted successfully"

            } catch (e: Exception) {

                _message.value =
                    "Delete failed: ${e.message}"
            }
        }
    }

    // Save selected record for updating
    fun selectRecord(myTable: MyTable) {

        _selectedRecord.value = myTable
    }

    // Clear selected record
    fun clearSelectedRecord() {

        _selectedRecord.value = null
    }

    // Reset insert success
    fun clearInsertSuccess() {

        _insertSuccess.value = false
    }

    // Clear message after displaying it
    fun clearMessage() {

        _message.value = null
    }
}