package com.example.infomedicalstaff.ui.fragments.groups

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.Key.VISIBILITY
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.databinding.FragmentAddContactsBinding
import com.example.infomedicalstaff.ui.fragments.chatList.ChatsListFragment
import com.example.infomedicalstaff.utilits.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView

class AddContactsFragment : Fragment() {
    private var _binding : FragmentAddContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter : FirebaseRecyclerAdapter<CommonModel, AddContactsFragment.AddContactHolder>
    private lateinit var mRefContacts : DatabaseReference
    private lateinit var mRefUser : DatabaseReference
    private lateinit var mReceivingUserListener : AppValueEventListener
    private var mapListener = hashMapOf<DatabaseReference, AppValueEventListener>()
    val listItems = mutableListOf<CommonModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddContactsBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initFun()

        binding.button2Add.setOnClickListener{
            listContacts.forEach {
                println(it.id)
            }
        }
    }

    private fun initFun(){
        initRecyclerView()
        initButtonClickArron()
    }

    private fun initRecyclerView() {
        mRecyclerView = binding.rvAddContacts
        mRefContacts = REF_DATABASE_ROOT.child(NODE_USERS)

        val options = FirebaseRecyclerOptions.Builder<CommonModel>()
            .setQuery(mRefContacts, CommonModel::class.java)
            .build()

        mAdapter = object : FirebaseRecyclerAdapter<CommonModel, AddContactsFragment.AddContactHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddContactsFragment.AddContactHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_user, parent, false)

                return AddContactsFragment.AddContactHolder(view)
            }

            @SuppressLint("SuspiciousIndentation")
            override fun onBindViewHolder(
                holder: AddContactsFragment.AddContactHolder,
                position: Int,
                model: CommonModel
            ) {
                mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(model.id)
                listItems.add(model)

                mReceivingUserListener = AppValueEventListener {
                    val contact = it.getCommonModel()
                    holder.addNameGroup.text = contact.userName
                    holder.addStateGroup.text = contact.state
                    holder.itemView.setOnClickListener {
                        //replaceFragment(SingleChatFragment(listItems[holder.adapterPosition]))
                        if(listItems[holder.absoluteAdapterPosition].choice){
                            holder.addChoice.visibility = View.INVISIBLE
                            listItems[holder.absoluteAdapterPosition].choice = false
                            listContacts.remove(listItems[holder.absoluteAdapterPosition])

                        } else {
                            holder.addChoice.visibility = View.VISIBLE
                            listItems[holder.absoluteAdapterPosition].choice = true
                            listContacts.add(listItems[holder.absoluteAdapterPosition])

                        }
                    }
                }
                mRefUser.addValueEventListener(mReceivingUserListener)
                mapListener[mRefUser] = mReceivingUserListener



            }

        }
        mRecyclerView.setLayoutManager(LinearLayoutManager(context))
        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()
    }

    private fun initButtonClickArron(){
        binding.btArrowAddContacts.setOnClickListener{
            val chatListFragment = ChatsListFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.main_layout, chatListFragment)
            transaction.commit()
        }
    }

    class AddContactHolder(view: View) : RecyclerView.ViewHolder(view) {
        val addNameGroup : TextView = view.findViewById(R.id.tv_add_name_user)
        val addStateGroup : TextView = view.findViewById(R.id.tv_add_user_appearance)
        //val iconGroup : CircleImageView = view.findViewById(R.id.iv_add_user)
        val addChoice : ConstraintLayout = view.findViewById(R.id.cl_add_choice)
    }

    override fun onPause() {
        super.onPause()
        mAdapter.stopListening()
        mapListener.forEach{
            it.key.removeEventListener(it.value)
        }
    }

    companion object{
        val listContacts = mutableListOf<CommonModel>()
    }
}



