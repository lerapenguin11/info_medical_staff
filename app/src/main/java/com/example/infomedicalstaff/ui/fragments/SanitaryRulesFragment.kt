package com.example.infomedicalstaff.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infomedicalstaff.ui.fragments.pdf.OnPdfSelectListener
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.DocModel
import com.example.infomedicalstaff.databinding.FragmentSanitaryRulesBinding
import com.example.infomedicalstaff.ui.fragments.pdf.PDFFragment
import com.example.infomedicalstaff.utilits.FIRE_STORE_DATABASE
import com.example.infomedicalstaff.utilits.replaceFragment
import com.example.infomedicalstaff.view.adapter.CategoriesAdapter
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot


class SanitaryRulesFragment : Fragment(), OnPdfSelectListener {
    private var _binding: FragmentSanitaryRulesBinding? = null
    private val binding get() = _binding!!
    private lateinit var sanitaryRulesList: ArrayList<DocModel>
    private lateinit var sanRulesAdapter: CategoriesAdapter

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSanitaryRulesBinding.inflate(inflater, container, false)

        binding.rvSanRules.apply {
            binding.rvSanRules.layoutManager = LinearLayoutManager(context)
            sanitaryRulesList = arrayListOf()
            sanRulesAdapter =
                CategoriesAdapter(sanitaryRulesList, this@SanitaryRulesFragment)
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
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.d("Firestore Error", error.message.toString())
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            sanitaryRulesList.add(dc.document.toObject(DocModel::class.java))
                        }
                    }


                    sanRulesAdapter.notifyDataSetChanged()
                }

            })
    }

    override fun onPdfSelected(file: DocModel) {
        val dialog = context?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.item_dialog_doc)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val title: TextView = dialog!!.findViewById(R.id.dialog_title)
        val bt_open: TextView = dialog.findViewById(R.id.tv_bt_open)
        val bt_add_fav: TextView = dialog.findViewById(R.id.tv_bt_add_fav)
        val close: ImageView = dialog.findViewById(R.id.iv_cansel)

        title.text = file.title

        bt_open.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("title", file.title)
            bundle.putString("file", file.file)

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            val fragment = PDFFragment()
            fragment.arguments = bundle
            transaction?.replace(R.id.main_layout, fragment)
            transaction?.commit()
            if(file.file.isEmpty() && file.title!!.isEmpty()){
                replaceFragment(PDFFragment())
            }

            dialog.cancel()
        }

        close.setOnClickListener { dialog.cancel() }

        dialog.show()
    }
}
