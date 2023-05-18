package com.example.infomedicalstaff.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.ui.fragments.pdf.OnPdfSelectListener
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.DocModel


class CategoriesAdapter(private val docList: ArrayList<DocModel>,
                        private val listener: OnPdfSelectListener
)
    : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doc, parent, false)

        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int = docList.size

    @SuppressLint("SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val doc: DocModel = docList[position]
        holder.title.text = doc.title
        holder.icon.setImageResource(R.drawable.guideline)
        holder.url.text = doc.file

        holder.item.setOnClickListener {
            listener.onPdfSelected(doc)
        }
    }

    class CategoriesViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.name_doc)
        val icon: ImageView = view.findViewById(R.id.iv_doc)
        val item: ConstraintLayout = view.findViewById(R.id.cl_item_doc)
        val url: TextView = view.findViewById(R.id.url) }
    }





