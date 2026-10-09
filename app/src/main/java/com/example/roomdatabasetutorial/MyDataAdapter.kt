package com.example.roomdatabasetutorial

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import com.example.roomdatabasetutorial.database.entities.MyTable

class MyDataAdapter(

    private val data: List<MyTable>,

    private val onUpdate: (MyTable) -> Unit,

    private val onDelete: (MyTable) -> Unit

) : RecyclerView.Adapter<MyDataVH>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyDataVH {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.list_item_layout,
                parent,
                false
            )

        return MyDataVH(view)
    }

    override fun getItemCount(): Int {

        return data.size
    }

    override fun onBindViewHolder(
        holder: MyDataVH,
        position: Int
    ) {

        val singleData = data[position]

        holder.tvItemName.text =
            "${singleData.name}\nDOB: ${singleData.dateOfBirth}"

        // UPDATE BUTTON
        holder.btnUpdateItem.setOnClickListener {

            onUpdate(singleData)
        }

        // DELETE BUTTON
        holder.btnDeleteItem.setOnClickListener {

            onDelete(singleData)
        }
    }
}