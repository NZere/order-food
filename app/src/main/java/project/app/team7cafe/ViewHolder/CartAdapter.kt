package project.app.team7cafe.ViewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import project.app.team7cafe.CartActivity
import project.app.team7cafe.Database.Database
import project.app.team7cafe.Interface.ItemClickListener
import project.app.team7cafe.Model.Order
import project.app.team7cafe.R
import java.text.NumberFormat
import java.util.Locale


class CartAdapter(listData:List<Order>, cartAct: CartActivity): RecyclerView.Adapter<CartViewHolder>() {
    var  listData: List<Order> = ArrayList<Order>()
//    lateinit var context:Context
    lateinit var cartAct:CartActivity

    init{
        this.listData=listData
//        this.context = context
        this.cartAct = cartAct
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
//        var inflater:LayoutInflater= LayoutInflater.from(context)
        var inflater:LayoutInflater= LayoutInflater.from(cartAct)
        var itemView:View = inflater.inflate(R.layout.cart_layout,parent, false)
        var ob = CartViewHolder(itemView)
        return ob
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        //or here if it exists add
//        var drawable= TextDrawable.builder().buildRound(""+listData.get(position).quantity, Color.RED)
//
//        holder.img_cart_count?.setImageDrawable(drawable)
        holder.btn_quantity?.number = listData[position].quantity
        holder.btn_quantity!!.setOnValueChangeListener { view, oldValue, newValue ->
            var order= listData[position]
            order.quantity=newValue.toString()
//            var d = Database(context).updateCart(order)
            var d = Database(cartAct).updateCart(order)

            var total = 0
            var orders:List<Order> = Database(cartAct).carts()
            for(order_item:Order in orders){
                total+=(order_item.price?.toInt()!!)*(order_item.quantity?.toInt()!!)
            }


            cartAct.txtTotalPrice.text=total.toString()



        }

        var price= (listData[position].price!!.toInt())*(listData.get(position).quantity!!.toInt())
        holder.txt_price!!.text=price.toString()
        holder.txt_cart_name!!.text=listData.get(position).productName

//        notifyDataSetChanged()notifyDataSetChanged

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun getItem(position: Int):Order{
        return listData[position]
    }

    fun removeItem(position: Int){
        listData.drop(position)
        notifyItemRemoved(position)

    }
    fun restoreItem(item:Order, position: Int){
        listData.toMutableList().add(position,item)
        notifyItemInserted(position)

    }

}

