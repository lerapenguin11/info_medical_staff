package com.example.infomedicalstaff.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.DocModel

class CategoriesAdapter(private val docList: ArrayList<DocModel>) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doc, parent, false)

        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int = docList.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val doc : DocModel = docList[position]
        holder.title.text = doc.title
        holder.icon.setImageResource(R.drawable.guideline)
    }

    class CategoriesViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.name_doc)
        val icon : ImageView = view.findViewById(R.id.iv_doc)
    }
}