package com.example.infomedicalstaff.utilits

import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.business.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.FirebaseFirestore

lateinit var AUTH : FirebaseAuth
lateinit var FIRE_STORE_DATABASE : FirebaseFirestore
lateinit var REF_DATABASE_ROOT : DatabaseReference
lateinit var USER : UserModel
lateinit var CURRENT_UID : String

const val NODE_USERS = "users"
const val NODE_MESSAGE = "message"
const val NODE_CHAT_LIST = "chat_list"
const val NODE_GROUPS = "groups"
const val NODE_MEMBERS = "members"
const val NODE_PHONES = "phones"

const val USER_CREATOR = "creator"
const val USER_ADMIN = "admin"
const val USER_MEMBER = "member"

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
    FIRE_STORE_DATABASE = FirebaseFirestore.getInstance()
}

fun DataSnapshot.getCommonModel() : CommonModel =
    this.getValue(CommonModel :: class.java) ?: CommonModel()

fun DataSnapshot.getUserModel() : UserModel =
    this.getValue(UserModel :: class.java) ?: UserModel()

fun sendMessage(message: String, receivingUserId: String, typeText: String, function: () -> Unit) {
    val refDialogUser = "$NODE_MESSAGE/$CURRENT_UID/$receivingUserId"
    val refDialogReceivingUser = "$NODE_MESSAGE/$receivingUserId/$CURRENT_UID"
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
        .addOnSuccessListener { function() }
}

fun sendMessageToGroup(message: String, groupID: String, typeText: String, function: () -> Unit) {

    var refMessages = "$NODE_GROUPS/$groupID/$NODE_MESSAGE"
    val messageKey = REF_DATABASE_ROOT.child(refMessages).push().key

    val mapMessage = hashMapOf<String, Any>()
    mapMessage[CHILD_FROM_TEXT] =
        CURRENT_UID
    mapMessage[CHILD_TYPE] = typeText
    mapMessage[CHILD_TEXT] = message
    mapMessage[CHILD_ID] = messageKey.toString()
    mapMessage[CHILD_TIME_STAMP] =
        ServerValue.TIMESTAMP

    REF_DATABASE_ROOT.child(refMessages).child(messageKey.toString())
        .updateChildren(mapMessage)
        .addOnSuccessListener { function() }
}

fun saveToMainList(id: String, type: String) {
    val refUser = "$NODE_CHAT_LIST/$CURRENT_UID/$id"
    val refReceived = "$NODE_CHAT_LIST/$id/$CURRENT_UID"

    val mapUser = hashMapOf<String, Any>()
    val mapReceived = hashMapOf<String, Any>()

    mapUser[CHILD_ID] = id
    mapUser[CHILD_TYPE] = type
    mapUser[CHILD_TIME_STAMP] = ServerValue.TIMESTAMP

    mapReceived[CHILD_ID] = CURRENT_UID
    mapReceived[CHILD_TYPE] = type
    mapReceived[CHILD_TIME_STAMP] = ServerValue.TIMESTAMP

    val commonMap = hashMapOf<String, Any>()
    commonMap[refUser] = mapUser
    commonMap[refReceived] = mapReceived

    REF_DATABASE_ROOT.updateChildren(commonMap)
}

fun deleteChat(id: String, function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_CHAT_LIST).child(CURRENT_UID).child(id).removeValue()
        .addOnSuccessListener { function() }
}

