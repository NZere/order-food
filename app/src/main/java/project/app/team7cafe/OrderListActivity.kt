package project.app.team7cafe

import android.app.DownloadManager.Request
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import project.app.team7cafe.Interface.ItemClickListener
import project.app.team7cafe.Model.Category
import project.app.team7cafe.Model.OrderRequest
import project.app.team7cafe.ViewHolder.MenuViewHolder
import project.app.team7cafe.ViewHolder.OrderViewHolder

class OrderListActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    val database = FirebaseDatabase.getInstance()
    private var auth: FirebaseAuth = Firebase.auth
    val users = database.getReference("User")
    val food = database.getReference("Food")
    val request: DatabaseReference = database.getReference("Request")
    val coupons = database.getReference("Coupons")

    lateinit var adapter: FirebaseRecyclerAdapter<OrderRequest, OrderViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)


        recyclerView = findViewById(R.id.listOrder)
        recyclerView.setHasFixedSize(true)
//        layoutManager = LinearLayoutManager(this)
        layoutManager = WrapContentLayoutManager(this@OrderListActivity, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = layoutManager

        loadOrders(auth.currentUser?.uid)

    }

    private fun loadOrders(uid: String?) {
        val options = FirebaseRecyclerOptions.Builder<OrderRequest>()
            .setQuery(request, OrderRequest::class.java)
            .setLifecycleOwner(this)
            .build()

        adapter =
            object : FirebaseRecyclerAdapter<OrderRequest, OrderViewHolder>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
                    return OrderViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.order_layout, parent, false)
                    )
                }

                override fun onBindViewHolder(
                    holder: OrderViewHolder,
                    position: Int,
                    model: OrderRequest
                ) {
                    holder.txtOrderId.text = adapter.getRef(position).key
                    holder.txtOrderStatus.text = convertCodeToStatus(model.status.toString())
                    holder.txtOrderTable.text = model.table_id.toString()
                    holder.txtOrderTotal.text = model.total.toString()

                    holder.setItemClickListener(object : ItemClickListener {
                        override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                            val intent: Intent = Intent(this@OrderListActivity, OrderDetailActivity::class.java)
                            intent.putExtra("OrderRequestId", adapter.getRef(position).key)
                            startActivity(intent)

//                            Toast.makeText(baseContext,""+adapter.getRef(position).key,Toast.LENGTH_SHORT).show()

                        }
                    })
                }


            }
        recyclerView.adapter=adapter

    }

    fun convertCodeToStatus(status: String): String {
        when (status) {
            "0" -> {
                return "in queue"
            }
            "1" -> {
                return "in process"
            }
            "2" -> {
                return "gave it to waiter"
            }
            "3" -> {
                return "done"
            }
        }
        return ""
    }

}