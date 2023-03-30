package com.example.infomedicalstaff.business.model

data class User(
    val id : String = "",
    var userName : String = "",
    var fullName : String = "",
    var status : String = "",
    var photoUrl : String = "",
    var email : String = "",
    var post : String = ""
)
