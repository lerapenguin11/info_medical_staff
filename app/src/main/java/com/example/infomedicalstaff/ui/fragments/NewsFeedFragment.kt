package com.example.infomedicalstaff.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.DocModel
import com.example.infomedicalstaff.business.model.NewsFeedModel
import com.example.infomedicalstaff.databinding.FragmentNewsFeedBinding
import com.example.infomedicalstaff.databinding.FragmentSanitaryRulesBinding
import com.example.infomedicalstaff.utilits.FIRE_STORE_DATABASE
import com.example.infomedicalstaff.view.adapter.CategoriesAdapter
import com.example.infomedicalstaff.view.adapter.NewsFeedAdapter
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class NewsFeedFragment : Fragment() {
    private var _binding: FragmentNewsFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsFeedList : ArrayList<NewsFeedModel>
    private lateinit var newsFeedAdapter : NewsFeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
        eventChangeListener()
    }

    private fun initRecyclerView() {
        binding.rvNewsFeed.apply {
            binding.rvNewsFeed.layoutManager = LinearLayoutManager(context)
            newsFeedList = arrayListOf()
            newsFeedAdapter =
                NewsFeedAdapter(newsFeedList)
            binding.rvNewsFeed.adapter = newsFeedAdapter
        }
    }

    private fun eventChangeListener() {
        FIRE_STORE_DATABASE.collection("news feed")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.d("Firestore Error", error.message.toString())
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            newsFeedList.add(dc.document.toObject(NewsFeedModel::class.java))
                        }
                    }
                    newsFeedAdapter.notifyDataSetChanged()
                }

            })
    }
}

