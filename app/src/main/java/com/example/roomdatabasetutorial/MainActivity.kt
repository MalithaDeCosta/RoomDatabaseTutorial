package com.example.roomdatabasetutorial

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityData

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Add safe padding for Android system bars
        val rootView: View = findViewById(R.id.main)

        val initialLeft = rootView.paddingLeft
        val initialTop = rootView.paddingTop
        val initialRight = rootView.paddingRight
        val initialBottom = rootView.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                initialLeft + systemBars.left,
                initialTop + systemBars.top,
                initialRight + systemBars.right,
                initialBottom + systemBars.bottom
            )

            insets
        }

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[
            MainActivityData::class.java
        ]

        // Find buttons
        val btnAdd: Button =
            findViewById(R.id.btnAdd)

        val btnView: Button =
            findViewById(R.id.btnView)

        // ADD RECORD BUTTON
        btnAdd.setOnClickListener {

            viewModel.clearSelectedRecord()

            showInsertFragment()
        }

        // VIEW RECORD BUTTON
        btnView.setOnClickListener {

            showViewFragment()
        }

        // Observe success and error messages
        viewModel.message.observe(this) { message ->

            if (!message.isNullOrEmpty()) {

                Toast.makeText(
                    this,
                    message,
                    Toast.LENGTH_SHORT
                ).show()

                viewModel.clearMessage()
            }
        }
    }

    // Load InsertRecordFragment
    fun showInsertFragment() {

        supportFragmentManager.beginTransaction()

            .replace(
                R.id.fragment_container,
                InsertRecordFragment()
            )

            .commit()
    }

    // Load ViewRecordsFragment
    fun showViewFragment() {

        supportFragmentManager.beginTransaction()

            .replace(
                R.id.fragment_container,
                ViewRecordsFragment()
            )

            .commit()
    }
}