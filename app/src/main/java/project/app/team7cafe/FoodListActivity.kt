package project.app.team7cafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import project.app.team7cafe.Interface.ItemClickListener
import project.app.team7cafe.Model.Category
import project.app.team7cafe.Model.Food
import project.app.team7cafe.ViewHolder.FoodViewHolder
import project.app.team7cafe.ViewHolder.MenuViewHolder
import project.app.team7cafe.databinding.ActivityCategoryMenuBinding

class FoodListActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    val auth: FirebaseAuth = Firebase.auth
    val database = FirebaseDatabase.getInstance()
    val food = database.getReference("Food")

    lateinit var recyler_food: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    var category_id: String = ""

    lateinit var adapter: FirebaseRecyclerAdapter<Food, FoodViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        try {
        }catch(_:Exception){        }

        recyler_food = findViewById(R.id.recycler_food)
        recyler_food.setHasFixedSize(true)
//        layoutManager = LinearLayoutManager(this)
        layoutManager = WrapContentLayoutManager(this@FoodListActivity, LinearLayoutManager.VERTICAL,false)

        recyler_food.layoutManager = layoutManager

        if (intent != null) {
            category_id = intent.getStringExtra("CategoryId")!!
        }
        if (category_id.isNotEmpty()) {
            loadListFood(category_id)

//            recyler_food.recycledViewPool.clear()
//            adapter.notifyDataSetChanged()
        }

    }

    private fun loadListFood(categoryId: String) {
        val options = FirebaseRecyclerOptions.Builder<Food>()
            .setQuery(food.orderByChild("category_id").equalTo(categoryId), Food::class.java)
            .setLifecycleOwner(this)
            .build()

        adapter =
            object : FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
                    return FoodViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.food_item, parent, false)
                    )
                }

                override fun onBindViewHolder(holder: FoodViewHolder, position: Int, model: Food) {
                    holder.food_name?.text = model.name
                    if(model.image?.isEmpty() == true){
                        model.image="https://lasd.lv/public/assets/no-image.png"
                    }
                    Picasso.with(baseContext).load(model.image).into(holder.food_image)
                    var clickItem: Food = model
                    holder.setItemClickListener(object : ItemClickListener {
                        override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                            val intent:Intent = Intent(this@FoodListActivity, FoodDetailActivity::class.java)
                            intent.putExtra("FoodId", adapter.getRef(position).key)
                            startActivity(intent)


                        }
                    }
                    )
                }

            }
        Log.d("Tag", ""+adapter.itemCount)
        recyler_food.adapter= adapter

    }
}
