package com.example.roomdatabasetutorial

import android.os.Bundle
import android.view.View

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.example.roomdatabasetutorial.database.entities.MyTable

class InsertRecordFragment :
    Fragment(R.layout.fragment_insert_record) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        // Find the views from XML
        val tvTitle: TextView =
            view.findViewById(R.id.tvTitle)

        val edtName: EditText =
            view.findViewById(R.id.edtName)

        val edtDOB: EditText =
            view.findViewById(R.id.edtDOB)

        val btnInsert: Button =
            view.findViewById(R.id.btnInsertRecord)

        val btnUpdate: Button =
            view.findViewById(R.id.btnUpdate)

        // Get the shared ViewModel
        val viewModel = ViewModelProvider(requireActivity())[
            MainActivityData::class.java
        ]

        // Find out whether a record was selected for updating
        val selectedRecord = viewModel.selectedRecord.value

        if (selectedRecord == null) {

            // INSERT MODE
            tvTitle.text = "Insert Record"

            btnInsert.isEnabled = true
            btnUpdate.isEnabled = false

        } else {

            // UPDATE MODE
            tvTitle.text = "Update Record"

            edtName.setText(selectedRecord.name)
            edtDOB.setText(selectedRecord.dateOfBirth)

            btnInsert.isEnabled = false
            btnUpdate.isEnabled = true
        }

        // INSERT BUTTON CLICK
        btnInsert.setOnClickListener {

            val name = edtName.text.toString().trim()
            val dob = edtDOB.text.toString().trim()

            // Validate user input
            if (name.isEmpty() || dob.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "Please enter name and date of birth",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                // Create the Entity object
                val newRecord = MyTable(
                    name = name,
                    dateOfBirth = dob
                )

                // Insert into Room Database
                viewModel.insertData(
                    requireContext(),
                    newRecord
                )
            }
        }

        // UPDATE BUTTON CLICK
        btnUpdate.setOnClickListener {

            val name = edtName.text.toString().trim()
            val dob = edtDOB.text.toString().trim()

            val currentRecord = viewModel.selectedRecord.value

            if (name.isEmpty() || dob.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "Please complete all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (currentRecord != null) {

                // Update existing record while preserving its ID
                val updatedRecord = currentRecord.copy(
                    name = name,
                    dateOfBirth = dob
                )

                viewModel.updateData(
                    requireContext(),
                    updatedRecord
                )

                // Return to the records list
                (requireActivity() as MainActivity)
                    .showViewFragment()
            }
        }

        // Observe successful insert
        viewModel.insertSuccess.observe(
            viewLifecycleOwner
        ) { success ->

            if (success) {

                // Clear input fields
                edtName.text.clear()
                edtDOB.text.clear()

                // Reset success state
                viewModel.clearInsertSuccess()
            }
        }
    }
}