package project.app.team7cafe.ViewHolder

import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import project.app.team7cafe.Interface.ItemClickListener
import project.app.team7cafe.R

open class CartViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

    private lateinit var itemClickListener: ItemClickListener
    var txt_cart_name: TextView? = null
    var txt_price: TextView? = null
    var btn_quantity: ElegantNumberButton? = null
    lateinit var view_back: RelativeLayout
    lateinit var view_front: LinearLayout


    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    init {
        this.txt_cart_name = itemView.findViewById(R.id.cart_item_name)
        this.txt_price = itemView.findViewById(R.id.cart_item_Price)
        this.btn_quantity=itemView.findViewById(R.id.quantity_button)
        this.view_back=itemView.findViewById(R.id.view_background)
        this.view_front=itemView.findViewById(R.id.view_front)
        itemView.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
    fun getItem(position:Int){}


}