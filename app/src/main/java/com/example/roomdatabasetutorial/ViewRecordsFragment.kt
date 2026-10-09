package com.example.roomdatabasetutorial

import android.os.Bundle
import android.view.View
import android.widget.TextView

import androidx.appcompat.app.AlertDialog

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewRecordsFragment :
    Fragment(R.layout.fragment_view_records) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        // Find RecyclerView
        val rvList: RecyclerView =
            view.findViewById(R.id.rvList)

        // Find empty message
        val tvEmpty: TextView =
            view.findViewById(R.id.tvEmpty)

        // Set RecyclerView layout manager
        rvList.layoutManager =
            LinearLayoutManager(requireContext())

        // Get the shared ViewModel
        val viewModel = ViewModelProvider(requireActivity())[
            MainActivityData::class.java
        ]

        // Observe the list of saved records
        viewModel.listMyTable.observe(
            viewLifecycleOwner
        ) { records ->

            // Show message if there are no records
            tvEmpty.visibility =
                if (records.isEmpty()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

            // Create RecyclerView adapter
            val adapter = MyDataAdapter(

                data = records,

                // UPDATE BUTTON ACTION
                onUpdate = { selectedRecord ->

                    // Save selected record in ViewModel
                    viewModel.selectRecord(selectedRecord)

                    // Open InsertRecordFragment in Update mode
                    (requireActivity() as MainActivity)
                        .showInsertFragment()
                },

                // DELETE BUTTON ACTION
                onDelete = { selectedRecord ->

                    // Show confirmation dialog
                    AlertDialog.Builder(requireContext())

                        .setTitle("Delete Record")

                        .setMessage(
                            "Are you sure you want to delete ${selectedRecord.name}?"
                        )

                        .setPositiveButton("Delete") { _, _ ->

                            // Delete from database
                            viewModel.deleteData(
                                requireContext(),
                                selectedRecord
                            )
                        }

                        .setNegativeButton("Cancel", null)

                        .show()
                }
            )

            // Attach adapter to RecyclerView
            rvList.adapter = adapter
        }

        // Load records from Room Database
        viewModel.loadData(requireContext())
    }
}