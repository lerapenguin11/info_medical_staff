package com.example.infomedicalstaff.utilits

import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.business.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

lateinit var AUTH : FirebaseAuth
lateinit var REF_DATABASE_ROOT : DatabaseReference
lateinit var USER : UserModel
lateinit var CURRENT_UID : String

const val NODE_USERS = "users"
const val NOSE_MESSAGE = "message"

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

const val TYPE_TEXT = "text"


fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = UserModel()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
}

fun DataSnapshot.getCommonModel() : CommonModel =
    this.getValue(CommonModel :: class.java) ?: CommonModel()

fun DataSnapshot.getUserModel() : UserModel =
    this.getValue(UserModel :: class.java) ?: UserModel()

fun sendMessage(message: String, receivingUserId: String, typeText: String, function: () -> Unit) {
    val refDialogUser = "$NOSE_MESSAGE/$CURRENT_UID/$receivingUserId"
    val refDialogReceivingUser = "$NOSE_MESSAGE/$receivingUserId/$CURRENT_UID"
    val messageKey = REF_DATABASE_ROOT.child(refDialogUser).push().key

    val mapMessage = hashMapOf<String, Any>()
    mapMessage[CHILD_FROM_TEXT] = CURRENT_UID
    mapMessage[CHILD_TYPE] = typeText
    mapMessage[CHILD_TEXT] = message
    mapMessage[CHILD_TIME_STAMP] = ServerValue.TIMESTAMP

    val mapDialog = hashMapOf<String, Any>()
    mapDialog["$refDialogUser/$messageKey"] = mapMessage
    mapDialog["$refDialogReceivingUser/$messageKey"] = mapMessage

    REF_DATABASE_ROOT
        .updateChildren(mapDialog)
        .addOnSuccessListener { function }
}

