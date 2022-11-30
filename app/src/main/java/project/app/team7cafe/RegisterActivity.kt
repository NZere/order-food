package project.app.team7cafe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import project.app.team7cafe.Model.User


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val backBtn = findViewById<Button>(R.id.button_back)
        val loginText = findViewById<TextView>(R.id.register)
        backBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        loginText.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val registerBtn = findViewById<Button>(R.id.button_register)

        // Getting email & password from users
        registerBtn.setOnClickListener{
            performSignUp()
        }
    }

    private fun performSignUp() {
        val name = findViewById<EditText>(R.id.input_name)
        val phone = findViewById<EditText>(R.id.input_phone)
        val email = findViewById<EditText>(R.id.input_email)
        val password = findViewById<EditText>(R.id.input_password)

        if (email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Enter all attributes!", Toast.LENGTH_SHORT).show()
            return
        }

        val nameInput = name.text.toString()
        val phoneInput = phone.text.toString()
        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        val timestamp = System.currentTimeMillis()
        auth.createUserWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var hashMap: HashMap<String, Any?> = HashMap()
                    hashMap["uid"]= auth.currentUser?.uid
                    hashMap["name"]=nameInput
                    hashMap["phone"]=phoneInput
                    hashMap["email"]= emailInput
                    hashMap["password"]=passwordInput
                    hashMap["profileImage"]=""
                    hashMap["userType"]="user"
                    hashMap["timestamp"]=timestamp
                    hashMap["order_number"]=0
                    hashMap["has_unordered"]=false
                    hashMap["table_id"]=-1


                    val users = database.getReference("User")

                    users.child(auth.currentUser?.uid!!).setValue(hashMap).addOnSuccessListener {
                        Toast.makeText(baseContext, "Saved successfully",
                            Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener(){
                        Toast.makeText(baseContext, "Failed in saving",
                            Toast.LENGTH_SHORT).show()
                    }

                    Toast.makeText(baseContext, "Authentication successfully.",
                        Toast.LENGTH_SHORT).show()

                    users.child(auth.currentUser?.uid!!).child("MyCoupons").child(hashMap["order_id"].toString()).setValue(hashMap)

                    // Sign in success, go to HomeActivity
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error occurred ${it.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
            }



    }


}