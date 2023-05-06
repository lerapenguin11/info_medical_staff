package com.example.infomedicalstaff.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.DocModel
import com.example.infomedicalstaff.databinding.FragmentSanitaryRulesBinding
import com.example.infomedicalstaff.utilits.FIRE_STORE_DATABASE
import com.example.infomedicalstaff.view.adapter.CategoriesAdapter
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class SanitaryRulesFragment : Fragment() {
    private var _binding : FragmentSanitaryRulesBinding? = null
    private val binding get() = _binding!!
    private lateinit var sanitaryRulesList : ArrayList<DocModel>
    private lateinit var sanRulesAdapter : CategoriesAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSanitaryRulesBinding.inflate(inflater, container, false)

        binding.rvSanRules.apply {
            binding.rvSanRules.layoutManager = LinearLayoutManager(context)
            sanitaryRulesList = arrayListOf()
            sanRulesAdapter = CategoriesAdapter(sanitaryRulesList, context)
            binding.rvSanRules.adapter = sanRulesAdapter
        }

        eventChangeListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        //initRecyclerView()

    }

    private fun eventChangeListener() {
        FIRE_STORE_DATABASE.collection("sanitary rules")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if(error !=null){
                        Log.d("Firestore Error", error.message.toString())
                    }

                    for (dc : DocumentChange in value?.documentChanges!!){
                        if(dc.type == DocumentChange.Type.ADDED){
                            sanitaryRulesList.add(dc.document.toObject(DocModel ::class.java))
                        }
                    }

                    sanRulesAdapter.notifyDataSetChanged()
                }

            })
    }

    private fun initRecyclerView() {
        binding.rvSanRules.apply {
            adapter = CategoriesAdapter(sanitaryRulesList, context)

            binding.rvSanRules.adapter

        }
    }
}