package project.app.team7cafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import project.app.team7cafe.Database.Database
import project.app.team7cafe.Interface.ItemClickListener
import project.app.team7cafe.Model.Food
import project.app.team7cafe.Model.Order
import project.app.team7cafe.ViewHolder.FoodViewHolder

class FoodDetailActivity : AppCompatActivity() {

    lateinit var food_name: TextView
    lateinit var food_price: TextView
    lateinit var food_description: TextView
    lateinit var food_image: ImageView

    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    lateinit var btn_cart: FloatingActionButton
    lateinit var elegantNumberButton: ElegantNumberButton

    var food_id: String = ""

    lateinit var recyclerView: RecyclerView

    val auth: FirebaseAuth = Firebase.auth
    val database = FirebaseDatabase.getInstance()
    val food = database.getReference("Food")

    lateinit var recyler_food: RecyclerView
    lateinit var layoutManager: LinearLayoutManager

    lateinit var adapter: FirebaseRecyclerAdapter<Food, FoodViewHolder>
    lateinit var food_item:Food

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        elegantNumberButton = findViewById(R.id.number_button)
        btn_cart = findViewById(R.id.btnCart) as FloatingActionButton

        btn_cart.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {

                var ord:Order= Order(food_id,
                    food_item.name,
                    elegantNumberButton.number,
                    food_item.price,
                    food_item.discount    )
                var d= Database(this@FoodDetailActivity)
                d.addToCart(ord)
                Toast.makeText(this@FoodDetailActivity, "added to cart ", Toast.LENGTH_SHORT).show()

            }

        }
        )

        food_description = findViewById(R.id.food_description)
        food_name = findViewById(R.id.food_name)
        food_price = findViewById(R.id.food_price)
        food_image = findViewById(R.id.img_food)

        collapsingToolbarLayout = findViewById(R.id.collapsing)
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpendedAppbat)
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbat)



        if (intent != null) {
            food_id = intent.getStringExtra("FoodId")!!
        }
        if (food_id.isNotEmpty()) {
            getDetailFood(food_id)
        }

    }

    private fun getDetailFood(foodId: String) {
        food.child(foodId).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                food_item= snapshot.getValue(Food::class.java)!!
                if (food_item != null) {
                    if(food_item.image?.isEmpty() == true){
                        food_item.image="https://lasd.lv/public/assets/no-image.png"
                    }
                }
                Picasso.with(baseContext).load(food_item!!.image).into(food_image)
                collapsingToolbarLayout.title= food_name.toString()
                food_price.text=food_item.price
                food_name.text=food_item.name
                food_description.text=food_item.description
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}