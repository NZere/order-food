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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()
//
//    class notOrdered{
//        var not_ordered:Boolean=false
//
//        fun setter(new:Boolean){
//            this.not_ordered=new
//        }
//        fun getter(): Boolean {
//            return this.not_ordered
//        }
//    }
//
//    var not_ordered=notOrdered()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val backBtn = findViewById<Button>(R.id.button_back)
        val registerText = findViewById<TextView>(R.id.register)
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val loginBtn = findViewById<Button>(R.id.button_login)
        loginBtn.setOnClickListener {
            performLogin()
        }

    }

    private fun performLogin() {
        // Getting input from the user
        val email = findViewById<EditText>(R.id.input_email)
        val password = findViewById<EditText>(R.id.input_password)

        // Null check on inputs
        if (email.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Enter all attributes!", Toast.LENGTH_SHORT).show()
            return
        }

        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()
        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to the Home Activity
                    //check orders

//                    checkOrders()
                    val users = database.getReference("User")
                    users.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
                        if (it.exists()) {
                            var not_ordered = it.child("has_unordered").value.toString().toBoolean()
                            if (not_ordered) {
                                val intent = Intent(this, CategoryMenuActivity::class.java)
                                startActivity(intent)

                                Toast.makeText(
                                    baseContext, "Authentication successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)

                                Toast.makeText(
                                    baseContext, "Authentication successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    baseContext, "error",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}


//    private fun checkOrders(){
//        val users = database.getReference("User")
//        var current_user = auth.currentUser?.uid
//        users.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
//            if (it.exists()) {
//
//                users.child(auth.currentUser?.uid!!).child("Order").addValueEventListener(object:
//                    ValueEventListener {
//
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        // This method is called once with the initial value and again
//                        // whenever data at this location is updated.
//                        for (ds in snapshot.children){
//                            Toast.makeText(baseContext, ds.value.toString(),
//                                Toast.LENGTH_SHORT).show()
//                            if (ds.child("is_ordered").value == false || ds.child("is_ordered").value == "false"|| ds.child("is_ordered").value.toString() == "false"){
//                                not_ordered.setter(true)
//                                return
//                            }
//                        }
//                    }
//                    override fun onCancelled(error: DatabaseError) {
//                    }
//                })
//            }
//        }.addOnFailureListener{
//            Toast.makeText(baseContext, "order number failed",
//                Toast.LENGTH_SHORT).show()
//        }
//
////        not_ordered.setter(true)
//        Toast.makeText(baseContext, not_ordered.getter().toString(),
//            Toast.LENGTH_SHORT).show()
//    }
//}