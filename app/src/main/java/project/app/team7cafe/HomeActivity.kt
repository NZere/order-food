package project.app.team7cafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = Firebase.auth

    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val reserveBtn = findViewById<Button>(R.id.button_tut)
        val takeBtn = findViewById<Button>(R.id.button_tam)
        reserveBtn.setOnClickListener {
            val intent = Intent(this, Reserve1Activity::class.java)
            startActivity(intent)
        }
        takeBtn.setOnClickListener {
            val users = database.getReference("User")
            users.child(auth.currentUser?.uid!!).child("table_id").setValue(-100)
            val intent = Intent(this, CategoryMenuActivity::class.java)
            startActivity(intent)
        }
    }
}