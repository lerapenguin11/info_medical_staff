package com.example.infomedicalstaff.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.DocModel
import com.example.infomedicalstaff.ui.fragments.pdf.OnPdfSelectListener

class FavoriteConfig(){

    private lateinit var favAdapter : FavoriteAdapter

    fun setConfig(recyclerView: RecyclerView,
                  favDocList: ArrayList<DocModel>,
                  keys : ArrayList<String>, onPdfSelectListener: OnPdfSelectListener, context : Context){

        favAdapter = FavoriteAdapter(favDocList, onPdfSelectListener, keys)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = favAdapter

    }

    class FavoriteAdapter(private val favDocList: ArrayList<DocModel>,
                          private val listener: OnPdfSelectListener, private val keys : ArrayList<String>,
                          private val favoriteListCopy: ArrayList<DocModel> = favDocList
    ) : RecyclerView.Adapter<FavoritesViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doc, parent, false)

            return FavoritesViewHolder(view)
        }

        override fun getItemCount(): Int = favDocList.size

        override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {

            holder.bind(favDocList[position], keys[position])
            val doc: DocModel = favDocList[position]

            holder.item.setOnClickListener {
                listener.onPdfSelected(doc)
            }
            //updateListItem(favDocList)
            /*holder.title.text = doc.title
            holder.icon.setImageResource(R.drawable.guideline)
            holder.url.text = doc.file*/
        }

        //TODO исплавить баг с избранным
       /* fun updateListItem(item: DocModel){
            val tempArrayList: ArrayList<DocModel> = ArrayList()
            if(!favDocList.isEmpty()){
                tempArrayList.add(item)
                favDocList.add(item)
                notifyItemInserted(favDocList.size)
            } else {
                tempArrayList.addAll(favoriteListCopy)
            }
            favDocList.clear();
            favDocList.addAll(tempArrayList);
            notifyDataSetChanged();
            tempArrayList.clear();

        }*/


    }
    class FavoritesViewHolder(view : View)
        : RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.name_doc)
        val icon: ImageView = view.findViewById(R.id.iv_doc)
        val item: ConstraintLayout = view.findViewById(R.id.cl_item_doc)
        val url: TextView = view.findViewById(R.id.url)

        private lateinit var key : String

        fun bind(docModel: DocModel, key : String){
            title.setText(docModel.title)
            icon.setImageResource(R.drawable.guideline)
            url.setText(docModel.file)
            this.key = key
        }
    }
}

