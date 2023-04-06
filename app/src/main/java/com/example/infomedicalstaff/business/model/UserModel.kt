package com.example.infomedicalstaff.business.model

data class UserModel(
    val id : String = "",
    var userName : String = "",
    var fullName : String = "",
    var state : String = "",
    var photoUrl : String = "",
    var email : String = "",
    var post : String = ""
)
