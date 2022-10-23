package project.app.team7cafe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = Firebase.auth


        val email = findViewById<TextView>(R.id.email_text)
        email.setText(auth.currentUser?.email.toString())

//        val tables = database.getReference("Table")
//
//        var hashMap: HashMap<String, Any?> = HashMap()
//        var table_id ="1"
//        hashMap["table_id"]= table_id
//        hashMap["seats_number"]=4
//        hashMap["is_reserved"]=false
//
//
//        tables.child(table_id!!).setValue(hashMap).addOnSuccessListener {
//            Toast.makeText(baseContext, "Saved successfully",
//                Toast.LENGTH_SHORT).show()
//
//        }.addOnFailureListener(){
//            Toast.makeText(baseContext, "Failed in saving",
//                Toast.LENGTH_SHORT).show()
//        }



        Toast.makeText(baseContext, auth.currentUser.toString(),
            Toast.LENGTH_SHORT).show()

        Toast.makeText(baseContext, auth.currentUser?.email.toString(),
            Toast.LENGTH_SHORT).show()

    }
}