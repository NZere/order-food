package project.app.team7cafe.Model

import com.google.firebase.database.Exclude

class Table {

    var is_reserved: Boolean= false
    var seats_number: Int=0
    var table_id:Int=0

    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf("is_reserved" to is_reserved,
            "seats_number" to seats_number,
            "table_id" to table_id
        )
    }

}