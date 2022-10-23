package project.app.team7cafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import project.app.team7cafe.R.*

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_home)

        auth = Firebase.auth


        val email = findViewById<TextView>(id.email_text)
        email.setText(auth.currentUser?.email.toString())



        Toast.makeText(baseContext, auth.currentUser.toString(),
            Toast.LENGTH_SHORT).show()

        Toast.makeText(baseContext, auth.currentUser?.email.toString(),
            Toast.LENGTH_SHORT).show()

        val backBtn = findViewById<Button>(R.id.button_back)

        backBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val outButton = findViewById<Button>(R.id.button_out)
        val inButton = findViewById<Button>(R.id.button_in)

        outButton.setOnClickListener{
            val intent = Intent(this, Reserve2Activity::class.java)
            startActivity(intent)
        }

        inButton.setOnClickListener{
            val intent = Intent(this, Reserve1Activity::class.java)
            startActivity(intent)
        }

    }
}