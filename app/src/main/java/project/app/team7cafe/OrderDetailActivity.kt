package project.app.team7cafe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import project.app.team7cafe.Model.OrderRequest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

class OrderDetailActivity : AppCompatActivity(), Runnable {

    lateinit var orderRequestId: String
    lateinit var order_request_item: OrderRequest


    val auth: FirebaseAuth = Firebase.auth
    val database = FirebaseDatabase.getInstance()
    val request = database.getReference("Request")

    lateinit var animationPrepare: LottieAnimationView
    lateinit var animationReady: LottieAnimationView
    lateinit var animationDone: LottieAnimationView
    lateinit var txtStatus: TextView
    lateinit var txtTime: TextView
    lateinit var txtTimer: TextView
    lateinit var btnFinishedEating: Button

    lateinit var handler: Handler
    lateinit var runnable: Runnable
    lateinit var resultDate:Date


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        animationPrepare = findViewById(R.id.animationPrepare)
        animationReady = findViewById(R.id.animationPrepared)
        animationDone = findViewById(R.id.animationDone)
        btnFinishedEating = findViewById(R.id.btn_finished_eating)
        txtTime = findViewById(R.id.order_time)
        txtTimer = findViewById(R.id.order_timer)
        txtStatus = findViewById(R.id.order_status)
        animationPrepare.setFailureListener {
            animationPrepare.setAnimation("")
            Toast.makeText(
                this@OrderDetailActivity,
                "ERROR",
                Toast.LENGTH_SHORT
            ).show()
        }
//        animationPrepare.setAnimation("prepare-food.json")
        animationReady.setFailureListener {
            Toast.makeText(
                this@OrderDetailActivity,
                "ERROR",
                Toast.LENGTH_SHORT
            ).show()
        }
//        animationReady.setAnimation("raw/food_is_ready.json")
        animationDone.setFailureListener {
            Toast.makeText(
                this@OrderDetailActivity,
                "ERROR",
                Toast.LENGTH_SHORT
            ).show()
        }
//        animationDone.setAnimation("raw/done.json")


        if (intent != null) {
            orderRequestId = intent.getStringExtra("OrderRequestId")!!
        }
        if (orderRequestId.isNotEmpty()) {
            getOrderDetail(orderRequestId)
        }



        btnFinishedEating.setOnClickListener() {
            order_request_item.status = "3"
            request.child(orderRequestId).child("status")
                .setValue("3")
                .addOnSuccessListener {
                    getOrderDetail(orderRequestId)
                }

        }

    }

    private fun getOrderDetail(orderRequestId: String) {
        request.child(orderRequestId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                order_request_item = snapshot.getValue(OrderRequest::class.java)!!
                txtTime.text = order_request_item.time
                txtTimer.text = "00:00:00"

                when (order_request_item.status) {
                    //1- in process 2- gave it to waiter 3- done

                    "1" -> {
                        animationDone.visibility = View.GONE
                        animationPrepare.visibility = View.VISIBLE
                        animationReady.visibility = View.GONE
                        btnFinishedEating.visibility = View.GONE

                        var sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss")
                        resultDate= Date(
                            (orderRequestId.toLong() + order_request_item.time!!.toInt() * 60000 + 6 * 60 * 60000)
                        )
                        txtTime.text = sdf.format(resultDate)
                        txtStatus.text = "In process"
                        getTargetTime(resultDate)

                    }
                    "2" -> {

                        animationDone.visibility = View.GONE
                        animationPrepare.visibility = View.GONE
                        animationReady.visibility = View.VISIBLE
                        btnFinishedEating.visibility = View.VISIBLE
                        txtTimer.text = "00:00:00"
                        var currentTime = System.currentTimeMillis()
                        var sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss")
                        var resultDate: Date = Date(currentTime + 6 * 60 * 60000) //+utc6
                        txtTime.text = sdf.format(resultDate)
                        txtStatus.text = "gave it to waiter"

                    }
                    "3" -> {
                        animationDone.visibility = View.VISIBLE
                        animationPrepare.visibility = View.GONE
                        animationReady.visibility = View.GONE
                        btnFinishedEating.visibility = View.GONE
                        txtTimer.text = "00:00:00"
                        var sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss")
                        var resultDate: Date = Date(
                            (orderRequestId.toLong() + order_request_item.time!!.toInt() * 60000 + 6 * 60 * 60000)
                        )
                        txtTime.text = sdf.format(resultDate)
                        txtStatus.text = "done"
                    }
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    fun getTargetTime(resultDate: Date) {
        handler = Handler()
        runnable = Runnable {
            handler.postDelayed(this, 10)
            try {
                val futureDate = resultDate
                var currentDate = Date(System.currentTimeMillis() + 6 * 60 * 60000)

                if (!currentDate.after(futureDate)) {
                    var diff: Long = futureDate.time - currentDate.time
                    var days = diff / (24 * 60 * 60 * 1000)
                    diff -= days * (24 * 60 * 60 * 1000)
                    var hours = diff / (60 * 60 * 1000)
                    diff -= hours * (60 * 60 * 1000)
                    var minutes = diff / (60 * 1000)
                    diff -= minutes * (60 * 1000)
                    var seconds = diff / 1000
                    diff -= seconds*1000
                    var millisec = diff

                    txtTimer.text = "$hours h $minutes min $seconds s"
                }

            } catch (e: Exception) {
                txtTimer.text = "00:00:01"
            }
        }
        handler.postDelayed(runnable,1*10)

        if (txtStatus.text !="In process"){
            txtTimer.text = "00:00:01"
            handler.removeCallbacks(runnable)
        }

    }

    override fun run() {

        handler = Handler()
        runnable = Runnable {
            handler.postDelayed(this, 10)
            try {
                val futureDate = resultDate
                var currentDate = Date(System.currentTimeMillis() + 6 * 60 * 60000)

                if (!currentDate.after(futureDate)) {
                    var diff: Long = futureDate.time - currentDate.time
                    var days = diff / (24 * 60 * 60 * 1000)
                    diff -= days * (24 * 60 * 60 * 1000)
                    var hours = diff / (60 * 60 * 1000)
                    diff -= hours * (60 * 60 * 1000)
                    var minutes = diff / (60 * 1000)
                    diff -= minutes * (60 * 1000)
                    var seconds = diff / 1000
                    diff -= seconds*1000
                    var millisec = diff

                    txtTimer.text = "$hours h $minutes min $seconds s"
                }

            } catch (e: Exception) {
                txtTimer.text = "00:00:01"
            }
        }
        handler.postDelayed(runnable,1*10)

        if (txtStatus.text !="In process"){
            txtTimer.text = "00:00:01"
            handler.removeCallbacks(runnable)
        }
    }


}