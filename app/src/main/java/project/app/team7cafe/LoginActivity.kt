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
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val backBtn = findViewById<Button>(R.id.button_back)
        val registerText = findViewById<TextView>(R.id.register)
        backBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        registerText.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        
        val loginBtn = findViewById<Button>(R.id.button_login)
        loginBtn.setOnClickListener{
            performLogin()
        }

    }

    private fun performLogin() {
        // Getting input from the user
        val email = findViewById<EditText>(R.id.input_email)
        val password = findViewById<EditText>(R.id.input_password)

        // Null check on inputs
        if(email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Enter all attributes!", Toast.LENGTH_SHORT).show()
            return
        }

        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to the Home Activity
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(baseContext, "Authentication successfully.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Welcome. ${it.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
            }
    }
}