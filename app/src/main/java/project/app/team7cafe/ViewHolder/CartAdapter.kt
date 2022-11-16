package project.app.team7cafe.ViewHolder

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import project.app.team7cafe.Interface.ItemClickListener
import project.app.team7cafe.Model.Order
import project.app.team7cafe.R
import java.text.NumberFormat
import java.util.Locale

open class CartViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

    private lateinit var itemClickListener: ItemClickListener
    var txt_cart_name: TextView? = null
    var txt_price: TextView? = null
    var img_cart_count: ImageView? = null
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    init {
        this.txt_cart_name = itemView.findViewById(R.id.cart_item_name)
        this.txt_price = itemView.findViewById(R.id.cart_item_Price)
        this.img_cart_count=itemView.findViewById(R.id.cart_item_count)
        itemView.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}

class CartAdapter(listData:List<Order>, context: Context): RecyclerView.Adapter<CartViewHolder>() {
    var  listData: List<Order> = ArrayList()
    lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        var inflater:LayoutInflater= LayoutInflater.from(context)
        var itemView:View = inflater.inflate(R.layout.cart_layout,parent, false)
        var ob = CartViewHolder(itemView)
        return ob
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        var drawable= TextDrawable.builder().buildRound(""+listData.get(position).quantity, Color.RED)

        holder.img_cart_count?.setImageDrawable(drawable)

        var locate = Locale("en","US")
        var fmt = NumberFormat.getCurrencyInstance(locate)
        var price= (listData[position].price as Int)*(listData.get(position).quantity as Int)
        holder.txt_price!!.text=fmt.format(price)
        holder.txt_cart_name!!.text=listData.get(position).productName


    }

    override fun getItemCount(): Int {
        return listData.size
    }

}