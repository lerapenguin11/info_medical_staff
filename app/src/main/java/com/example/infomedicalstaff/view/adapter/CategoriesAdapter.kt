package com.example.infomedicalstaff.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.DocModel

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>(){
    val listItems = mutableListOf<DocModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doc, parent, false)

        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.title.text = "Профилактика внутрибольничных гнойноных пациентов"
        holder.icon.setImageResource(R.drawable.guideline)

    }

    class CategoriesViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.name_doc)
        val icon : ImageView = view.findViewById(R.id.iv_doc)
    }
}