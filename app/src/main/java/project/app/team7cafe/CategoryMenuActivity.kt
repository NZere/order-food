package project.app.team7cafe

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.internal.service.Common
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import project.app.team7cafe.databinding.ActivityCategoryMenuBinding

class CategoryMenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCategoryMenuBinding
    private lateinit var auth: FirebaseAuth

    val database = FirebaseDatabase.getInstance()
    lateinit var txtFullName:TextView
    lateinit var recyler_menu: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarCategoryMenu.toolbar)


        //init firebase
        var categories = database.getReference("Category")


        binding.appBarCategoryMenu.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView



        //set name of user
        var headerView: View =navView.getHeaderView(0)
        txtFullName=findViewById(R.id.txtFullName)
        val users = database.getReference("User")
        users.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                var name = it.child("name").value.toString()
                txtFullName.setText(name)
            }
        }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.category_menu, menu)
        return true
    }

}


