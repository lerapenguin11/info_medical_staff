package com.example.infomedicalstaff.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.infomedicalstaff.databinding.FragmentChangeNameBinding
import com.example.infomedicalstaff.utilits.*

class ChangeNameFragment : Fragment() {

    private var _binding : FragmentChangeNameBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChangeNameBinding.inflate(inflater, container, false)

        binding.btChengeSave.setOnClickListener{
            changeName()
            replaceFragment(ProfileFragment())
        }

        val fullNameList = USER.userName.split(" ")
        binding.etChengeUserName.setText(fullNameList[0])
        //binding.etChengeUserSurname.setText(fullNameList[1])

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        initFirebase()
        //
        //changeName()
        clickArrowProfile()
    }

    private fun clickArrowProfile() {
        binding.ivArrowProfile.setOnClickListener {
            replaceFragment(ProfileFragment())
        }
    }

    private fun changeName(){
        val name = binding.etChengeUserName.text.toString()
        val surname = binding.etChengeUserSurname.text.toString()

        if(name.isEmpty()){
            Toast.makeText(context, "Поле имя не может быть пустым", Toast.LENGTH_LONG).show()
        } else {
            val fullName = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_USER_NAME)
                .setValue(fullName).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(context, "Данные обновлены", Toast.LENGTH_LONG).show()
                        USER.userName = fullName
                    }
                }
        }
    }
}