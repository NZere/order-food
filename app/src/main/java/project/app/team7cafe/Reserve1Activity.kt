package project.app.team7cafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import project.app.team7cafe.Model.Table
import project.app.team7cafe.CategoryMenuActivity

class Reserve1Activity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val database = FirebaseDatabase.getInstance()
    var final_table_id ="0"

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

                    orange.setOnClickListener{
                        val green1 = findViewById<Button>(R.id.button_table_1_selected)
                        val green2 = findViewById<Button>(R.id.button_table_2_selected)
                        val green3 = findViewById<Button>(R.id.button_table_3_selected)
                        val green4 = findViewById<Button>(R.id.button_table_4_selected)
                        green1.visibility = View.GONE
                        green2.visibility = View.GONE
                        green3.visibility = View.GONE
                        green4.visibility = View.GONE
                        green.visibility = View.VISIBLE
                        green.text = seats_number
                        final_table_id="1"
                        nextPage()
                    }
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

                    orange.setOnClickListener{
                        val green1 = findViewById<Button>(R.id.button_table_1_selected)
                        val green2 = findViewById<Button>(R.id.button_table_2_selected)
                        val green3 = findViewById<Button>(R.id.button_table_3_selected)
                        val green4 = findViewById<Button>(R.id.button_table_4_selected)
                        green1.visibility = View.GONE
                        green2.visibility = View.GONE
                        green3.visibility = View.GONE
                        green4.visibility = View.GONE
                        green.visibility = View.VISIBLE
                        green.text = seats_number
                        final_table_id="2"
                        nextPage()
                    }
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

                    orange.setOnClickListener{
                        val green1 = findViewById<Button>(R.id.button_table_1_selected)
                        val green2 = findViewById<Button>(R.id.button_table_2_selected)
                        val green3 = findViewById<Button>(R.id.button_table_3_selected)
                        val green4 = findViewById<Button>(R.id.button_table_4_selected)
                        green1.visibility = View.GONE
                        green2.visibility = View.GONE
                        green3.visibility = View.GONE
                        green4.visibility = View.GONE
                        green.visibility = View.VISIBLE
                        green.text = seats_number
                        final_table_id="3"
                        nextPage()
                    }
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

                    orange.setOnClickListener{
                        val green1 = findViewById<Button>(R.id.button_table_1_selected)
                        val green2 = findViewById<Button>(R.id.button_table_2_selected)
                        val green3 = findViewById<Button>(R.id.button_table_3_selected)
                        val green4 = findViewById<Button>(R.id.button_table_4_selected)
                        green1.visibility = View.GONE
                        green2.visibility = View.GONE
                        green3.visibility = View.GONE
                        green4.visibility = View.GONE
                        green.visibility = View.VISIBLE
                        green.text = seats_number
                        final_table_id="4"
                        nextPage()
                    }
                }
            }
        }
    }

    private fun nextPage() {
        val nextBtn = findViewById<Button>(R.id.button_next)
        nextBtn.visibility = View.VISIBLE

        val users = database.getReference("User")
        val timestamp = System.currentTimeMillis()

        var hashMap: HashMap<String, Any?> = HashMap()

        hashMap["user_id"]= auth.currentUser?.uid
        hashMap["date_start"]= timestamp
        hashMap["is_ordered"]= false
        hashMap["ordered_date"]=""
        hashMap["table_id"]=final_table_id

        var tables = database.getReference("Table")

        var order_num = 1005
        users.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                Toast.makeText(baseContext, "here",
                    Toast.LENGTH_SHORT).show()
                var order_number= it.child("order_number").value.toString().toInt()
                order_num = order_number
                Toast.makeText(baseContext, order_num.toString(),
                    Toast.LENGTH_SHORT).show()
                hashMap["order_id"]= (order_num+1).toString()
            }
        }.addOnFailureListener{
            Toast.makeText(baseContext, "order number failed",
                Toast.LENGTH_SHORT).show()
        }


        nextBtn.setOnClickListener{
            val intent = Intent(this, CategoryMenuActivity::class.java)
            startActivity(intent)

            users.child(auth.currentUser?.uid!!).child("Order").child(hashMap["order_id"].toString())
                .setValue(hashMap)
                .addOnSuccessListener {


                    Toast.makeText(baseContext, "table with id "+ final_table_id +" was saved successfully"+hashMap["order_id"].toString(),
                        Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(baseContext, "table is not selected",
                        Toast.LENGTH_SHORT).show()
                }

            users.child(auth.currentUser?.uid!!).child("order_number").setValue(hashMap["order_id"].toString())
            tables.child(final_table_id).child("is_reserved").setValue(true)
        }


    }

}