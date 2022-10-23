package project.app.team7cafe.Model

import java.sql.Timestamp

public class User{
    var uid:Any?=""
    var name: String? = ""
    var phone: String? = ""
    var email: String? = ""
    var password: String? = ""
    var profileImage: String? = ""
    var userType: String? = "user"
    var timestramp:Any? = System.currentTimeMillis()


    constructor(){}

    constructor(uid:Any,name:String="", phone:String="", email: String, password: String, profileImage:String="", userType:String="user", timestamp: Any){
        this.uid = uid
        this.name = name
        this.phone=phone
        this.email=email
        this.password=password
        this.profileImage= profileImage
        this.userType= userType
        this.timestramp= timestamp
    }


}