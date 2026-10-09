package com.example.roomdatabasetutorial

import android.view.View
import android.widget.TextView
import android.widget.Button

import androidx.recyclerview.widget.RecyclerView

class MyDataVH(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    val tvItemName: TextView =
        itemView.findViewById(R.id.tvItemName)

    val btnUpdateItem: Button =
        itemView.findViewById(R.id.btnUpdateItem)

    val btnDeleteItem: Button =
        itemView.findViewById(R.id.btnDeleteItem)
}