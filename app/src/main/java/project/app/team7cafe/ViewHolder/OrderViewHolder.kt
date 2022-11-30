package project.app.team7cafe.ViewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import project.app.team7cafe.Interface.ItemClickListener
import project.app.team7cafe.R

class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var txtOrderId: TextView
    var txtOrderStatus: TextView
    var txtOrderTable: TextView
    var txtOrderTotal: TextView
    var itemClickListener: ItemClickListener? = null

    init {
        txtOrderId = itemView.findViewById(R.id.order_item_id)
        txtOrderStatus = itemView.findViewById(R.id.order_item_status)
        txtOrderTable = itemView.findViewById(R.id.order_item_table)
        txtOrderTotal = itemView.findViewById(R.id.order_item_total)
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        itemClickListener!!.onClick(v, adapterPosition, false)
    }
}