package project.app.team7cafe

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import project.app.team7cafe.Database.Database
import project.app.team7cafe.Helper.RecyclerItemTouchHelper
import project.app.team7cafe.Interface.RecyclerItemTouchHelperListener
import project.app.team7cafe.Model.Order
import project.app.team7cafe.Model.OrderRequest
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
    private var auth: FirebaseAuth = Firebase.auth
    val users = database.getReference("User")
    val food = database.getReference("Food")
    val request: DatabaseReference = database.getReference("Request")
    val coupons = database.getReference("Coupons")

    lateinit var txtTotalPrice: TextView
    lateinit var btnPay: Button

    var carts: List<Order> = ArrayList()

    lateinit var cartAdapter: CartAdapter
    lateinit var order_request: OrderRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        rootLayout = findViewById(R.id.rootLayout)

        recyclerView = findViewById(R.id.listCart)
        recyclerView.setHasFixedSize(true)
//        layoutManager = LinearLayoutManager(this@CartActivity)
        layoutManager =
            WrapContentLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager


        var itemTouchHelper: ItemTouchHelper.SimpleCallback =
            RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)

        var t = ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView)

        txtTotalPrice = findViewById(R.id.total)
        btnPay = findViewById(R.id.btnPlaceOrder)



        btnPay.setOnClickListener {
            getCoupon()

        }

        loadListFood()


    }

    private fun getCoupon() {

        lateinit var table_id: String
        var alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@CartActivity, R.style.AlertDialogTheme)
        alertDialog.setTitle("Do you have any coupons?")
        alertDialog.setMessage("Enter your coupon code")

        val couponTxt: EditText = EditText(this@CartActivity)
        var lp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT

        )
        couponTxt.layoutParams = lp
        alertDialog.setView(couponTxt)
        alertDialog.setIcon(R.drawable.ic_baseline_card_giftcard_24)

        alertDialog.setPositiveButton(
            "Yes!"
        ) { dialog, which ->
            var user_id = auth.currentUser?.uid
            users.child(auth.currentUser?.uid!!).get().addOnSuccessListener { it ->
                if (it.exists()) {
                    table_id = it.child("table_id").value.toString()
                }
            }
            Toast.makeText(
                this@CartActivity,
                ""+couponTxt.text.toString(),
                Toast.LENGTH_SHORT
            ).show()
            coupons.child(couponTxt.text.toString()).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        var f_total = txtTotalPrice.text.toString()
                            .toDouble() * ((100 - it.child("discount").value.toString()
                            .toDouble()) / 100)
                        order_request =
                            OrderRequest(
                                user_id,
                                table_id,
                                f_total.toString(),
                                couponTxt.text.toString(),
                                "0",
                                carts
                            )
                        request.child(System.currentTimeMillis().toString()).setValue(order_request)
                        Database(baseContext).cleanCart()
                        Toast.makeText(
                            this@CartActivity,
                            "Thank you! Your order is being processed",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                    else{
                        Toast.makeText(
                            this@CartActivity,
                            "Incorrect coupon code"+couponTxt.text.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
                .addOnFailureListener {
                    Toast.makeText(
                        this@CartActivity,
                        "Incorrect coupon code"+couponTxt.text.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
        alertDialog.setNegativeButton(
            "No"
        ) { dialog, which ->
            var user_id = auth.currentUser?.uid
            users.child(auth.currentUser?.uid!!).get().addOnSuccessListener { it ->
                if (it.exists()) {
                    table_id = it.child("table_id").value.toString()
                    order_request =
                        OrderRequest(
                            user_id,
                            table_id,
                            txtTotalPrice.text.toString(),
                            couponTxt.text.toString(),
                            "0",
                            carts
                        )
                    request.child(System.currentTimeMillis().toString()).setValue(order_request)
                    Database(baseContext).cleanCart()
                    Toast.makeText(
                        this@CartActivity,
                        "Thank you! Your order is being processed",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }

        }

        alertDialog.setNeutralButton(
            "Cancel"
        ) { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()



}

private fun loadListFood() {
    var cart = Database(this@CartActivity).carts()
    cartAdapter = CartAdapter(cart, this)
    cartAdapter.notifyDataSetChanged()
    recyclerView.adapter = cartAdapter

    var total = 0

    for (order: Order in cart) {
        total += (order.price?.toInt()!!) * (order.quantity?.toInt()!!)
    }

    var locate = Locale("en", "US")
    var fmt = NumberFormat.getCurrencyInstance(locate)

    txtTotalPrice.text = fmt.format(total)

}

override fun onSwipped(
    viewHolder: RecyclerView.ViewHolder?,
    direction: Int,
    position: Int
) {
    if (viewHolder is CartViewHolder) {
        var name =
            (recyclerView.adapter as CartAdapter).getItem(viewHolder.absoluteAdapterPosition).productName
        var deleteItem =
            (recyclerView.adapter as CartAdapter).getItem(viewHolder.absoluteAdapterPosition)
        var deleteIndex = viewHolder.adapterPosition
        cartAdapter.removeItem(deleteIndex)

        var d0 = Database(baseContext).removeFromCart(deleteItem.productId!!)

        var cart = Database(baseContext).carts()

        var total = 0

        for (order_item: Order in cart) {
            total += (order_item.price?.toInt()!!) * (order_item.quantity?.toInt()!!)
        }

        var locate = Locale("en", "US")
        var fmt = NumberFormat.getCurrencyInstance(locate)

        txtTotalPrice.text = fmt.format(total)


        var snackbar: Snackbar =
            Snackbar.make(rootLayout, name + " removed", Snackbar.LENGTH_LONG)
        snackbar.setAction(
            "UNDO"
        ) {
            cartAdapter.restoreItem(deleteItem, deleteIndex)
            var d = Database(baseContext).addToCart(deleteItem)

            var cart = Database(baseContext).carts()

            var total = 0

            for (order_item: Order in cart) {
                total += (order_item.price?.toInt()!!) * (order_item.quantity?.toInt()!!)
            }

            var locate = Locale("en", "US")
            var fmt = NumberFormat.getCurrencyInstance(locate)

            txtTotalPrice.text = fmt.format(total)

        }
        snackbar.setActionTextColor(Color.YELLOW)
        snackbar.show()


    }
}
}

