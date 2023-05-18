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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.DocModel
import com.example.infomedicalstaff.databinding.FragmentSanitaryRulesBinding
import com.example.infomedicalstaff.ui.fragments.pdf.OnPdfSelectListener
import com.example.infomedicalstaff.ui.fragments.pdf.PDFFragment
import com.example.infomedicalstaff.utilits.*
import com.example.infomedicalstaff.view.adapter.CategoriesAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.*


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

        eventChangeListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
        clickButton()
    }

    private fun clickButton() {
        binding.btArrowSanRules.setOnClickListener { replaceFragment(HomeFragment()) }
    }

    private fun initRecyclerView() {
        binding.rvSanRules.apply {
            binding.rvSanRules.layoutManager = LinearLayoutManager(context)
            sanitaryRulesList = arrayListOf()
            sanRulesAdapter =
                CategoriesAdapter(sanitaryRulesList, this@SanitaryRulesFragment)
            binding.rvSanRules.adapter = sanRulesAdapter
        }
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

    @SuppressLint("UseRequireInsteadOfGet")
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
        val urlFile : TextView = dialog.findViewById(R.id.tv_url)

        title.text = file.title
        urlFile.text = file.file

        bt_open.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("title", file.title)
            bundle.putString("file", file.file)

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            val fragment = PDFFragment()
            fragment.arguments = bundle
            transaction?.replace(R.id.main_layout, fragment)
            transaction?.commit()
            if (file.file.isEmpty() && file.title!!.isEmpty()) {
                replaceFragment(PDFFragment())
            }

            dialog.cancel()
        }

        bt_add_fav.setOnClickListener {

            if (REF_DATABASE_ROOT.child(NODE_FAVORITES).child(CURRENT_UID) == null){
                val id : String = REF_DATABASE_ROOT.child(NODE_FAVORITES).push().key.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_TITLE_FILE] = file.title.toString()
                dateMap[CHILD_FILE] = file.file.toString()
                REF_DATABASE_ROOT.child(NODE_FAVORITES).child(CURRENT_UID).child(id).updateChildren(dateMap)
                    .addOnCompleteListener { it2 ->
                        if (it2.isSuccessful) {
                            dialog.cancel()
                            Toast.makeText(context, "Документ дообавлен в избранное", Toast.LENGTH_LONG).show()
                        } else Toast.makeText(context, "Документ не дообавлен в избранное", Toast.LENGTH_LONG).show()

                    }
            } else{
                val id : String = REF_DATABASE_ROOT.child(NODE_FAVORITES).push().key.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_TITLE_FILE] = file.title.toString()
                dateMap[CHILD_FILE] = file.file.toString()
                REF_DATABASE_ROOT.child(NODE_FAVORITES).child(CURRENT_UID).child(id).updateChildren(dateMap)
                    .addOnCompleteListener { it2 ->
                        if (it2.isSuccessful) {
                            dialog.cancel()
                            Toast.makeText(context, "Документ дообавлен в избранное", Toast.LENGTH_LONG).show()
                        } else Toast.makeText(context, "Документ не дообавлен в избранное", Toast.LENGTH_LONG).show()

                    }
            }
        }

        close.setOnClickListener { dialog.cancel() }

        dialog.show()
    }
}
