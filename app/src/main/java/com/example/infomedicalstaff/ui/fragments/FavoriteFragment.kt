package com.example.infomedicalstaff.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infomedicalstaff.business.model.DocModel
import com.example.infomedicalstaff.databinding.FragmentFavoriteBinding
import com.example.infomedicalstaff.ui.fragments.pdf.OnPdfSelectListener
import com.example.infomedicalstaff.utilits.*
import com.example.infomedicalstaff.view.adapter.FavoriteConfig
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FavoriteFragment : Fragment(), OnPdfSelectListener {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        readFavoriteList(object : DataStatusFavorite{
            override fun DataIsLoaded(favorite: ArrayList<DocModel>, keys: ArrayList<String>) {
                FavoriteConfig().setConfig(binding.rvFav, favorite, keys, this@FavoriteFragment,
                    activity!!
                )

            }

            override fun updateFavoriteList(favorite: ArrayList<DocModel>) {
                if (favorite != null){
                    if(favorite.size > 0){
                        binding.textFav.visibility = View.GONE
                    }
                    else binding.textFav.visibility = View.VISIBLE
                }
                else binding.textFav.visibility = View.VISIBLE
            }

        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        clickButton()
    }

    private fun clickButton() {
        binding.btArrowFav.setOnClickListener { replaceFragment(HomeFragment()) }
    }

    override fun onPdfSelected(file: DocModel) {

    }
}