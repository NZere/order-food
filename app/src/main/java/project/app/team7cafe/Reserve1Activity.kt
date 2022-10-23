package project.app.team7cafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import project.app.team7cafe.Model.Table

class Reserve1Activity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve1)

        auth = Firebase.auth

        val nextBtn = findViewById<Button>(R.id.button_next)
        nextBtn.visibility = View.GONE

        var tables = database.getReference("Table")

        tables.child("1").get().addOnSuccessListener {
            if (it.exists()){
                var is_reserved = it.child("is_reserved").value.toString().toBoolean()
                var seats_number = it.child("seats_number").value.toString()
                var table_id= it.child("table_id").value.toString()

                val orange = findViewById<Button>(R.id.button_table_1_none)
                val green = findViewById<Button>(R.id.button_table_1_selected)
                val grey = findViewById<Button>(R.id.button_table_1_yes)

                if (is_reserved){
                    green.visibility = View.GONE
                    grey.visibility = View.VISIBLE
                    orange.visibility = View.GONE

                }
                else{
                    green.visibility = View.GONE
                    grey.visibility = View.GONE
                    orange.visibility = View.VISIBLE
                    orange.text = seats_number
                }
            }
        }
        tables.child("2").get().addOnSuccessListener {
            if (it.exists()){
                var is_reserved = it.child("is_reserved").value.toString().toBoolean()
                var seats_number = it.child("seats_number").value.toString()
                var table_id= it.child("table_id").value.toString()

                val orange = findViewById<Button>(R.id.button_table_2_none)
                val green = findViewById<Button>(R.id.button_table_2_selected)
                val grey = findViewById<Button>(R.id.button_table_2_yes)

                if (is_reserved){
                    green.visibility = View.GONE
                    grey.visibility = View.VISIBLE
                    orange.visibility = View.GONE

                }
                else{
                    green.visibility = View.GONE
                    grey.visibility = View.GONE
                    orange.visibility = View.VISIBLE
                    orange.text = seats_number
                }
            }
        }
        tables.child("3").get().addOnSuccessListener {
            if (it.exists()){
                var is_reserved = it.child("is_reserved").value.toString().toBoolean()
                var seats_number = it.child("seats_number").value.toString()
                var table_id= it.child("table_id").value.toString()

                val orange = findViewById<Button>(R.id.button_table_3_none)
                val green = findViewById<Button>(R.id.button_table_3_selected)
                val grey = findViewById<Button>(R.id.button_table_3_yes)

                if (is_reserved){
                    green.visibility = View.GONE
                    grey.visibility = View.VISIBLE
                    orange.visibility = View.GONE

                }
                else{
                    green.visibility = View.GONE
                    grey.visibility = View.GONE
                    orange.visibility = View.VISIBLE
                    orange.text = seats_number
                }
            }
        }
        tables.child("4").get().addOnSuccessListener {
            if (it.exists()){
                var is_reserved = it.child("is_reserved").value.toString().toBoolean()
                var seats_number = it.child("seats_number").value.toString()
                var table_id= it.child("table_id").value.toString()

                val orange = findViewById<Button>(R.id.button_table_4_none)
                val green = findViewById<Button>(R.id.button_table_4_selected)
                val grey = findViewById<Button>(R.id.button_table_4_yes)

                if (is_reserved){
                    green.visibility = View.GONE
                    grey.visibility = View.VISIBLE
                    orange.visibility = View.GONE

                }
                else{
                    green.visibility = View.GONE
                    grey.visibility = View.GONE
                    orange.visibility = View.VISIBLE
                    orange.text = seats_number
                }
            }
        }

        val backBtn = findViewById<Button>(R.id.button_back)

        backBtn.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }


    }
}