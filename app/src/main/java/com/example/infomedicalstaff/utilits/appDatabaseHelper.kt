package com.example.infomedicalstaff.utilits

import com.example.infomedicalstaff.business.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var AUTH : FirebaseAuth
lateinit var REF_DATABASE_ROOT : DatabaseReference
lateinit var USER : User
lateinit var CURRENT_UID : String

const val NODE_USERS = "users"

const val CHILD_ID = "id"
const val CHILD_EMAIL = "email"
const val CHILD_USER_NAME = "userName"
const val CHILD_STATE = "state"
const val CHILD_PHOTO_URL = "photoURL"
const val CHILD_TEXT = "text"
const val CHILD_TYPE = "type"
const val CHILD_FROM_TEXT = "fromText"
const val CHILD_TIME_STAMP = "timeStamp"
const val CHILD_FULL_NAME = "fullName"

fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = User()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
}

