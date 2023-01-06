package com.surajdevgan.speer_technologies_android_assessment

import java.io.Serializable

data class UserModel(
    var userid: String?,
    var username: String?,
    var description: String?,
    var followers: String?,
    var following: String?,
    var avatar: String?
    ) :
    Serializable {

}



