package com.example.infomedicalstaff.view.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.DocModel

class CategoriesAdapter(private val docList: ArrayList<DocModel>, private val context : Context) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doc, parent, false)

        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int = docList.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val doc : DocModel = docList[position]
        holder.title.text = doc.title
        holder.icon.setImageResource(R.drawable.guideline)
        holder.item.setOnClickListener {
            val text : String = doc.title.toString()
            showCustomDialog(text)
        }
    }

    private fun showCustomDialog(text: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.item_dialog_doc)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val title : TextView = dialog.findViewById(R.id.dialog_title)
        val bt_open : TextView = dialog.findViewById(R.id.tv_bt_open)
        val bt_add_fav : TextView = dialog.findViewById(R.id.tv_bt_add_fav)
        val close : ImageView = dialog.findViewById(R.id.iv_cansel)

        title.text = text



        dialog.show()
    }

    class CategoriesViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.name_doc)
        val icon : ImageView = view.findViewById(R.id.iv_doc)
        val item : ConstraintLayout = view.findViewById(R.id.cl_item_doc)
    }
}