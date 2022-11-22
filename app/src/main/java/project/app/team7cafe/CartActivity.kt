package project.app.team7cafe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import project.app.team7cafe.Database.Database
import project.app.team7cafe.Model.Order
import project.app.team7cafe.ViewHolder.CartAdapter
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager

    val database = FirebaseDatabase.getInstance()
    val food = database.getReference("Food")
    val request:DatabaseReference=database.getReference("Request")

    lateinit var txtTotalPrice: TextView
    lateinit var btnPay: Button

    var  carts: List<Order> = ArrayList()

    lateinit var cartAdapter: CartAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView=findViewById(R.id.listCart)
        recyclerView.setHasFixedSize(true)
//        layoutManager = LinearLayoutManager(this@CartActivity)
        layoutManager = WrapContentLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = layoutManager

        txtTotalPrice=findViewById(R.id.total)
        btnPay=findViewById(R.id.btnPlaceOrder)
        loadListFood()

    }

    private fun loadListFood() {
        var cart = Database(this@CartActivity).carts()
        cartAdapter=CartAdapter(cart,this)
        recyclerView.adapter=cartAdapter

        var total = 0

        for(order:Order in cart){
            total+=(order.price?.toInt()!!)*(order.quantity?.toInt()!!)
        }

        var locate = Locale("en","US")
        var fmt = NumberFormat.getCurrencyInstance(locate)

        txtTotalPrice.text=fmt.format(total)

    }
}