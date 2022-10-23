package project.app.team7cafe.Model

import java.sql.Timestamp

public class User{
    var name: String? = null
    var phone: String? = null
    var email: String? = null
    var password: String? = null
    var profileImage: String? = null
    var userType: String? = "user"
    var timestramp:String? = null


    constructor(){}

    constructor(name:String="", phone:String="", email: String, password: String, profileImage:String="", userType:String="user", timestamp: String){
        this.name = name
        this.phone=phone
        this.email=email
        this.password=password
        this.profileImage= profileImage
    }


}