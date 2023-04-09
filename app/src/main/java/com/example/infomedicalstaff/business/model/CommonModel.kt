package com.example.infomedicalstaff.business.model

data class CommonModel(
    val id : String = "",
    val userName : String = "",
    var state : String = "",
    var photoURL : String = "empty",
    var fullName : String = "",
    //данные для чата
    var text : String = "",
    var type : String = "",
    var fromText : String = "",
    var timeStamp : Any = "",

    var lastMessage : String = "",
    var choice : Boolean = false

)
