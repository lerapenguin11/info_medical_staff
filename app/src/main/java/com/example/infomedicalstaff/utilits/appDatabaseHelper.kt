package com.example.infomedicalstaff.utilits

import com.example.infomedicalstaff.business.model.CommonModel
import com.example.infomedicalstaff.business.model.DocModel
import com.example.infomedicalstaff.business.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore

lateinit var AUTH : FirebaseAuth
lateinit var FIRE_STORE_DATABASE : FirebaseFirestore
lateinit var REF_DATABASE_ROOT : DatabaseReference
lateinit var USER : UserModel
lateinit var CURRENT_UID : String
lateinit var DOC : DocModel

const val NODE_USERS = "users"
const val NODE_MESSAGE = "message"
const val NODE_CHAT_LIST = "chat_list"
const val NODE_GROUPS = "groups"
const val NODE_MEMBERS = "members"
const val NODE_PHONES = "phones"
const val NODE_FAVORITES = "favorite"
const val NODE_FAVORITE_ID = "favorite_id"
const val NODE_CONTACTS = "contacts"

const val USER_CREATOR = "creator"
const val USER_ADMIN = "admin"
const val USER_MEMBER = "member"

const val CHILD_ID = "id"
const val CHILD_EMAIL = "email"
const val CHILD_PHONE = "phone"
const val CHILD_USER_NAME = "userName"
const val CHILD_STATE = "state"
const val CHILD_PHOTO_URL = "photoURL"
const val CHILD_TEXT = "text"
const val CHILD_TYPE = "type"
const val CHILD_FROM_TEXT = "fromText"
const val CHILD_TIME_STAMP = "timeStamp"
const val CHILD_FULL_NAME = "fullName"

const val CHILD_TITLE_FILE = "title"
const val CHILD_FILE = "file"

const val TYPE_TEXT = "text"

private var mListItems = arrayListOf<DocModel>()

interface DataStatusFavorite{
    fun DataIsLoaded(favorite : ArrayList<DocModel>, keys : ArrayList<String>)
    fun updateFavoriteList(favorite : ArrayList<DocModel>)
}


fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = UserModel()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
    FIRE_STORE_DATABASE = FirebaseFirestore.getInstance()

    DOC = DocModel()
}

fun DataSnapshot.getCommonModel() : CommonModel =
    this.getValue(CommonModel :: class.java) ?: CommonModel()

fun DataSnapshot.getDocModel() : DocModel =
    this.getValue(DocModel :: class.java) ?: DocModel()

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

fun readFavoriteList(dataStatus : DataStatusFavorite) {
    REF_DATABASE_ROOT.child(NODE_FAVORITES).child(CURRENT_UID)
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val keys = mutableListOf<String>()
                for (keyNode: DataSnapshot in snapshot.children) {
                    keys.add(keyNode.key!!)
                    val docModel: DocModel? = keyNode.getValue(DocModel::class.java)
                    mListItems.add(docModel!!)
                }
                dataStatus.DataIsLoaded(mListItems, keys as ArrayList<String>)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
}

