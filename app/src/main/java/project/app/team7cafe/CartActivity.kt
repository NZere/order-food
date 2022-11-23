package project.app.team7cafe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import project.app.team7cafe.Database.Database
import project.app.team7cafe.Helper.RecyclerItemTouchHelper
import project.app.team7cafe.Interface.RecyclerItemTouchHelperListener
import project.app.team7cafe.Model.Order
import project.app.team7cafe.ViewHolder.CartAdapter
import project.app.team7cafe.ViewHolder.CartViewHolder
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartActivity : AppCompatActivity(), RecyclerItemTouchHelperListener {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var rootLayout: RelativeLayout

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

        rootLayout=findViewById(R.id.rootLayout)

        recyclerView=findViewById(R.id.listCart)
        recyclerView.setHasFixedSize(true)
//        layoutManager = LinearLayoutManager(this@CartActivity)
        layoutManager = WrapContentLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = layoutManager


        var itemTouchHelper :ItemTouchHelper.SimpleCallback = RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,  this)

        var t = ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView)

        txtTotalPrice=findViewById(R.id.total)
        btnPay=findViewById(R.id.btnPlaceOrder)

        btnPay.setOnClickListener {

        }
        loadListFood()


    }

    private fun loadListFood() {
        var cart = Database(this@CartActivity).carts()
        cartAdapter=CartAdapter(cart,this)
        cartAdapter.notifyDataSetChanged()
        recyclerView.adapter=cartAdapter

        var total = 0

        for(order:Order in cart){
            total+=(order.price?.toInt()!!)*(order.quantity?.toInt()!!)
        }

        var locate = Locale("en","US")
        var fmt = NumberFormat.getCurrencyInstance(locate)

        txtTotalPrice.text=fmt.format(total)

    }

    override fun onSwipped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        if(viewHolder is CartViewHolder){
            var name = (recyclerView.adapter as CartAdapter).getItem(viewHolder.absoluteAdapterPosition).productName
            var deleteItem = (recyclerView.adapter as CartAdapter).getItem(viewHolder.absoluteAdapterPosition)
            var deleteIndex = viewHolder.adapterPosition
            cartAdapter.removeItem(deleteIndex)

            var d0 = Database(baseContext).removeFromCart(deleteItem.productId!!)

            var cart = Database(baseContext).carts()

            var total = 0

            for(order_item:Order in cart){
                total+=(order_item.price?.toInt()!!)*(order_item.quantity?.toInt()!!)
            }

            var locate = Locale("en","US")
            var fmt = NumberFormat.getCurrencyInstance(locate)

            txtTotalPrice.text=fmt.format(total)


            var snackbar:Snackbar= Snackbar.make(rootLayout, name+" removed", Snackbar.LENGTH_LONG)
            snackbar.setAction("UNDO"
            ) {
                cartAdapter.restoreItem(deleteItem,deleteIndex)
                var d = Database(baseContext).addToCart(deleteItem)

                var cart = Database(baseContext).carts()

                var total = 0

                for(order_item:Order in cart){
                    total+=(order_item.price?.toInt()!!)*(order_item.quantity?.toInt()!!)
                }

                var locate = Locale("en","US")
                var fmt = NumberFormat.getCurrencyInstance(locate)

                txtTotalPrice.text=fmt.format(total)

            }
            snackbar.setActionTextColor(Color.YELLOW)
            snackbar.show()


        }
    }
}

